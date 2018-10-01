package br.com.leanwork.testedevandroidlean.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import br.com.leanwork.testedevandroidlean.BancoSQLiteHelper;
import br.com.leanwork.testedevandroidlean.model.Endereco;
import br.com.leanwork.testedevandroidlean.model.Pessoa;

public class EnderecoDAO extends BancoGenericoDAO {

    private static final String TAG = EnderecoDAO.class.getSimpleName();
    public EnderecoDAO(Context context) {
        super(context);
    }

    public ArrayList<Endereco> getEnderecos(){
        ArrayList<Endereco> listaEnderecos = new ArrayList<>();

        @SuppressLint("Recycle") Cursor cursor = database.query(BancoSQLiteHelper.ENDERECO_TABLE,
                new String[] {BancoSQLiteHelper.ENDERECO_ID_COLUMN,
                        BancoSQLiteHelper.ENDERECO_CEP_COLUMN,
                        BancoSQLiteHelper.ENDERECO_ENDERECO_COLUMN,
                        BancoSQLiteHelper.ENDERECO_BAIRRO_COLUMN,
                        BancoSQLiteHelper.ENDERECO_CIDADE_COLUMN,
                        BancoSQLiteHelper.ENDERECO_ESTADO_COLUMN,
                },null,null,null,null,null);

        while(cursor.moveToNext()){
            Endereco endereco = new Endereco(cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));

            listaEnderecos.add(endereco);
        }

        return listaEnderecos;
    }

    public Endereco getEndereco(long id){
        Endereco endereco = null;

        String sql = "SELECT * FROM " + BancoSQLiteHelper.ENDERECO_TABLE
                + " WHERE " + BancoSQLiteHelper.ENDERECO_ID_COLUMN + " = ?";

        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            endereco = new Endereco(cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
        }
        return endereco;
    }

    public long setEndereco(Endereco endereco) {
        ContentValues values = new ContentValues();
        values.put(BancoSQLiteHelper.ENDERECO_CEP_COLUMN, endereco.getCep());
        values.put(BancoSQLiteHelper.ENDERECO_ENDERECO_COLUMN, endereco.getEndereco());
        values.put(BancoSQLiteHelper.ENDERECO_BAIRRO_COLUMN, endereco.getBairro());
        values.put(BancoSQLiteHelper.ENDERECO_CIDADE_COLUMN, endereco.getCidade());
        values.put(BancoSQLiteHelper.ENDERECO_ESTADO_COLUMN, endereco.getEstado());

        Log.d(TAG, "setEndereco -> Endereço salvo no banco de dados!");

        return database.insert(BancoSQLiteHelper.ENDERECO_TABLE, null, values);
    }
}
