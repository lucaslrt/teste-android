package br.com.leanwork.testedevandroidlean.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import br.com.leanwork.testedevandroidlean.BancoSQLiteHelper;
import br.com.leanwork.testedevandroidlean.model.Pessoa;

public class PessoaDAO extends BancoGenericoDAO {
    private static final String TAG = PessoaDAO.class.getSimpleName();
    public PessoaDAO(Context context) {
        super(context);
    }

    public ArrayList<Pessoa> getPessoas(){
        ArrayList<Pessoa> listaPessoas = new ArrayList<>();

        @SuppressLint("Recycle") Cursor cursor = database.query(BancoSQLiteHelper.PESSOA_TABLE,
                new String[] {BancoSQLiteHelper.PESSOA_ID_COLUMN,
                        BancoSQLiteHelper.PESSOA_NOME_COLUMN,
                        BancoSQLiteHelper.PESSOA_GENERO_COLUMN,
                        BancoSQLiteHelper.PESSOA_NASCIMENTO_COLUMN,
                        BancoSQLiteHelper.PESSOA_TELEFONE_COLUMN
                },null,null,null,null,null);

        while(cursor.moveToNext()){
            Pessoa pessoa = new Pessoa(cursor.getLong(0)
                    ,cursor.getString(1)
                    ,cursor.getString(2)
                    ,cursor.getString(3)
                    ,cursor.getString(4));

            listaPessoas.add(pessoa);
        }

        return listaPessoas;
    }

    public long setPessoa(Pessoa pessoa) {
        ContentValues values = new ContentValues();
        values.put(BancoSQLiteHelper.PESSOA_NOME_COLUMN, pessoa.getNome());
        values.put(BancoSQLiteHelper.PESSOA_GENERO_COLUMN, pessoa.getGenero());
        values.put(BancoSQLiteHelper.PESSOA_NASCIMENTO_COLUMN, pessoa.getDataNascimento());
        values.put(BancoSQLiteHelper.PESSOA_TELEFONE_COLUMN, pessoa.getTelefone());

        Log.d(TAG, "setPessoa -> Pessoa salva no banco de dados!");

        return database.insert(BancoSQLiteHelper.PESSOA_TABLE, null, values);
    }

}
