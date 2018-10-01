package br.com.leanwork.testedevandroidlean.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.julienarzul.simpledialogfragment.SimpleDialogContent;
import com.julienarzul.simpledialogfragment.SimpleDialogFragment;

import br.com.leanwork.testedevandroidlean.R;
import br.com.leanwork.testedevandroidlean.dao.EnderecoDAO;
import br.com.leanwork.testedevandroidlean.dao.PessoaDAO;
import br.com.leanwork.testedevandroidlean.model.Endereco;
import br.com.leanwork.testedevandroidlean.model.Pessoa;

public class DetalheCadastroActivity extends AppCompatActivity {

    public static String ExtraTipoCadastro = "TipoCadastro";
    public static String ExtraIdCadastro = "IDCadastro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cadastro);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String tipoCadastro = extras.getString(ExtraTipoCadastro);
            final long idCadastro = extras.getLong(ExtraIdCadastro);

            TextView tvMensagem = ((TextView) findViewById(R.id.tvMensagem));
            tvMensagem.setText("Novo Cadastro de " + tipoCadastro + "!\nBora Exibir os detalhes?");

            tvMensagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (tipoCadastro){
                        case "Pessoa":
                            mostrarDadosPessoa(idCadastro);
                            break;
                        case "Endereco":
                            mostrarDadosEndereco(idCadastro);
                            break;
                    }
                }
            });
        }
    }

    public void mostrarDadosPessoa(long idCadastro){
        PessoaDAO pessoaDAO = new PessoaDAO(getApplicationContext());
        Pessoa pessoa = pessoaDAO.getPessoa(idCadastro);

        SimpleDialogFragment.newInstance(
                SimpleDialogContent.builder()
                        .setTitle("Pessoa")
                        .setMessage("Nome:\t" + pessoa.getNome() + "\n"
                                + "Gênero:\t" + pessoa.getGenero() + "\n"
                                + "Data de Nascimento:\t" + pessoa.getDataNascimento() + "\n"
                                + "Telefone:\t" + pessoa.getTelefone() + "\n")
                        .setPositiveButtonText("Ok")
                        .build())
                .show(getSupportFragmentManager(), SimpleDialogFragment.TAG);
    }

    public void mostrarDadosEndereco(long idCadastro){
        EnderecoDAO enderecoDAO = new EnderecoDAO(getApplicationContext());
        Endereco endereco = enderecoDAO.getEndereco(idCadastro);

        SimpleDialogFragment.newInstance(
                SimpleDialogContent.builder()
                        .setTitle("Endereço")
                        .setMessage("CEP:\t" + endereco.getCep() + "\n"
                                + "Endereço:\t" + endereco.getEndereco() + "\n"
                                + "Bairro:\t" + endereco.getBairro() + "\n"
                                + "Cidade:\t" + endereco.getCidade() + "\n"
                                + "Estado:\t" + endereco.getEstado() + "\n")
                        .setPositiveButtonText("Ok")
                        .build())
                .show(getSupportFragmentManager(), SimpleDialogFragment.TAG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
