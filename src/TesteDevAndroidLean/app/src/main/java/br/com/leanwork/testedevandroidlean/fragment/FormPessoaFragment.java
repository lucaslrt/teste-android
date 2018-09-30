package br.com.leanwork.testedevandroidlean.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

import br.com.leanwork.testedevandroidlean.BancoSQLiteHelper;
import br.com.leanwork.testedevandroidlean.dao.PessoaDAO;
import br.com.leanwork.testedevandroidlean.model.Pessoa;
import br.com.leanwork.testedevandroidlean.util.FormUtil;
import br.com.leanwork.testedevandroidlean.PushCadastro;
import br.com.leanwork.testedevandroidlean.R;
import br.com.leanwork.testedevandroidlean.util.ServiceUtil;

import static android.R.layout.simple_spinner_dropdown_item;

public class FormPessoaFragment extends Fragment {
    private final String TAG = FormPessoaFragment.class.getSimpleName();

    private TextInputEditText etNomeCompleto;
    private TextInputEditText etTelefone;
    private static TextInputEditText etDataNascimento;
    private Spinner spnGenero;
    private String nome;
    private String genero;
    private String telefone;
    private static String dataNascimento;

    public static FormPessoaFragment newInstance() {
        Bundle args = new Bundle();
        FormPessoaFragment fragment = new FormPessoaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form_pessoa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG,"onViewCreated -> On method.");

        etNomeCompleto = view.findViewById(R.id.etNomeCompleto);
        etTelefone = view.findViewById(R.id.etTelefoneCelular);
        etDataNascimento = view.findViewById(R.id.etDataNascimento);
        spnGenero = view.findViewById(R.id.spnGenero);
        MaterialButton mbtFinalizarCadastro = view.findViewById(R.id.btFinalizarCadastroPessoa);

        // adicionando itens para o spinner
        adicionarGeneros();

        // quando o campo de data for selecionado
        etDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerFragment().show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


        // quando o botão de finalizar cadastro for clicado
        mbtFinalizarCadastro.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                nome = Objects.requireNonNull(etNomeCompleto.getText()).toString();
                genero = spnGenero.getSelectedItem().toString();
                dataNascimento = Objects.requireNonNull(etDataNascimento.getText()).toString();
                telefone = Objects.requireNonNull(etTelefone.getText()).toString();
                if(formValido()){
                    // adicionando os dados no banco
                    PessoaDAO pessoaDAO = new PessoaDAO(getContext());
                    Pessoa novaPessoa = new Pessoa(nome,genero,dataNascimento,telefone);

                    cadastroRealizadoComSucesso(pessoaDAO.setPessoa(novaPessoa));
                }
                else {
                    Toast.makeText(getActivity(), "Por favor, preencha os campos de nome e telefone.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void adicionarGeneros(){
        // adicionando itens para o spinner
        String[] listaGeneros = new String[]{
                "Masculino", "Feminino", "Outro"
        };
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getActivity(), simple_spinner_dropdown_item, listaGeneros);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGenero.setAdapter(spinnerAdapter);
    }

    private void cadastroRealizadoComSucesso(Long idNovaPessoa) {
        limparDados();
        PushCadastro.disparar(getContext(), "Pessoa", idNovaPessoa);
        ServiceUtil.esconderTeclado(getContext(), etNomeCompleto);
        Toast.makeText(getActivity(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void limparDados() {
        nome = null;
        genero = null;
        telefone = null;
        dataNascimento = null;
        etNomeCompleto.setText(null);
        etTelefone.setText(null);
        etDataNascimento.setText(null);
        spnGenero.setSelection(0);
    }

    private boolean formValido() {
        nome = etNomeCompleto.getText() != null ? etNomeCompleto.getText().toString() : null;
        telefone = etTelefone.getText() != null ? etTelefone.getText().toString() : null;
        return FormUtil.formularioPreenchido(nome, genero, dataNascimento, telefone);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            //noinspection ConstantConditions
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @SuppressLint("SetTextI18n")
        public void onDateSet(DatePicker view, int year, int month, int day) {
            etDataNascimento.setText(day + "/" + month + "/" + year);
        }
    }

}
