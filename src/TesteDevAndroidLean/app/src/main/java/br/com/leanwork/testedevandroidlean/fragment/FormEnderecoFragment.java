package br.com.leanwork.testedevandroidlean.fragment;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import br.com.leanwork.testedevandroidlean.CepResponse;
import br.com.leanwork.testedevandroidlean.activity.DetalheCadastroActivity;
import br.com.leanwork.testedevandroidlean.dao.EnderecoDAO;
import br.com.leanwork.testedevandroidlean.manager.ApiManager;
import br.com.leanwork.testedevandroidlean.model.Endereco;
import br.com.leanwork.testedevandroidlean.util.FormUtil;
import br.com.leanwork.testedevandroidlean.PushCadastro;
import br.com.leanwork.testedevandroidlean.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormEnderecoFragment extends Fragment {
    private final String TAG = FormEnderecoFragment.class.getSimpleName();

    private TextInputEditText etCep, etEndereco, etCidade, etBairro, etEstado;
    private String cep, endereco, bairro, cidade, estado;

    public static FormEnderecoFragment newInstance() {
        Bundle args = new Bundle();
        FormEnderecoFragment fragment = new FormEnderecoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form_endereco, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG,"onViewCreated -> On method.");

        etCep = view.findViewById(R.id.etCep);
        etEndereco = view.findViewById(R.id.etLogradouro);
        etBairro = view.findViewById(R.id.etBairro);
        etCidade = view.findViewById(R.id.etCidade);
        etEstado = view.findViewById(R.id.etEstado);
        MaterialButton mbtFinalizarCadastro = view.findViewById(R.id.btFinalizarCadastroEndereco);
        MaterialButton mbtLimparDados = view.findViewById(R.id.btLimparCadastroEndereco);

        // quando o cep foi preenchido
        etCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    procurarEndereco(Objects.requireNonNull(etCep.getText()).toString());
                }
            }
        });

        // quando o botão de cadastro for pressionado
        mbtFinalizarCadastro.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                cep = Objects.requireNonNull(etCep.getText()).toString();
                endereco = Objects.requireNonNull(etEndereco.getText()).toString();
                bairro = Objects.requireNonNull(etBairro.getText()).toString();
                cidade = Objects.requireNonNull(etCidade.getText()).toString();
                estado = Objects.requireNonNull(etEstado.getText()).toString();

                if(formValido()){
                    EnderecoDAO enderecoDAO = new EnderecoDAO(getContext());
                    Endereco end = new Endereco(cep,endereco,bairro,cidade,estado);

                    cadastroRealizadoComSucesso(enderecoDAO.setEndereco(end));
                }
                else {
                    Toast.makeText(getActivity(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // quando o botão de limpar cadastro for clicado
        mbtLimparDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparDados();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void procurarEndereco(String cep){
        ApiManager apiManager = ApiManager.getInstance();
        Call<CepResponse> call;
        call = apiManager.getApiCep().pesquisarEndereco(cep);
        call.enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(@NonNull Call<CepResponse> call, @NonNull Response<CepResponse> response) {

                CepResponse cepResponse = response.body();
                if (cepResponse != null) {
                    preencherCep(cepResponse);
                }
                else {
                    cepNaoLocalizado();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CepResponse> call, @NonNull Throwable t) {
                cepNaoLocalizado();
            }
        });
    }

    private void cepNaoLocalizado(){
        Toast.makeText(getActivity(), "Não foi possível localizar o cep.", Toast.LENGTH_SHORT).show();
    }

    private void preencherCep(CepResponse cepResponse) {
        etCep.setText(cepResponse.getCep());
        etEndereco.setText(cepResponse.getEndereco());
        etBairro.setText(cepResponse.getBairro());
        etCidade.setText(cepResponse.getCidade());
        etEstado.setText(cepResponse.getEstado());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void cadastroRealizadoComSucesso(Long idEndereco) {

        Intent notificationIntent = new Intent(getActivity(), DetalheCadastroActivity.class);
        notificationIntent.putExtra(DetalheCadastroActivity.ExtraTipoCadastro, "Endereco");
        notificationIntent.putExtra(DetalheCadastroActivity.ExtraIdCadastro, idEndereco);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                .Builder(Objects.requireNonNull(getContext()), "0")
                .setSmallIcon(R.drawable.ic_cadastro_notification)
                .setContentTitle("Novo Cadastro de Endereço!")
                .setContentText("Um novo endereço em " + cidade + ", " + estado + " foi adicionado!")
                .setContentIntent(contentIntent);

        PushCadastro.disparar(getContext(), notificationBuilder);
        Toast.makeText(getActivity(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
        limparDados();
    }

    private void limparDados() {
        cep = null;
        endereco = null;
        bairro = null;
        cidade = null;
        estado = null;
        etCep.setText(null);
        etEndereco.setText(null);
        etCidade.setText(null);
        etBairro.setText(null);
        etEstado.setText(null);
    }

    private boolean formValido() {
        cep = etCep.getText() != null ? etCep.getText().toString() : null;
        endereco = etEndereco.getText() != null ? etEndereco.getText().toString() : null;
        bairro = etBairro.getText() != null ? etBairro.getText().toString() : null;
        cidade = etCidade.getText() != null ? etCidade.getText().toString() : null;
        estado = etEstado.getText() != null ? etEstado.getText().toString() : null;
        return FormUtil.formularioPreenchido(cep, endereco, bairro, cidade, estado);
    }

}
