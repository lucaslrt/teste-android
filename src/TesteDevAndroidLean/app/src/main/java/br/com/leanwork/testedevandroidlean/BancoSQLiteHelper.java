package br.com.leanwork.testedevandroidlean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.leanwork.testedevandroidlean.model.Endereco;
import br.com.leanwork.testedevandroidlean.model.Pessoa;

public class BancoSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BancoComSqlite.db";

    // Nomes das tabelas
    public static final String PESSOA_TABLE = "pessoa";
    public static final String ENDERECO_TABLE = "endereco";

    // Atributos de Pessoa
    public static final String PESSOA_ID_COLUMN = "pessoaID";
    public static final String PESSOA_NOME_COLUMN = "pessoaNome";
    public static final String PESSOA_GENERO_COLUMN = "pessoaGenero";
    public static final String PESSOA_NASCIMENTO_COLUMN = "pessoaNascimento";
    public static final String PESSOA_TELEFONE_COLUMN = "pessoaTelefone";

    // Atributos de Endereço
    public static final String ENDERECO_ID_COLUMN = "enderecoID";
    public static final String ENDERECO_CEP_COLUMN = "enderecoCep";
    public static final String ENDERECO_ENDERECO_COLUMN = "enderecoEndereco";
    public static final String ENDERECO_BAIRRO_COLUMN = "enderecoBairro";
    public static final String ENDERECO_CIDADE_COLUMN = "enderecoCidade";
    public static final String ENDERECO_ESTADO_COLUMN = "enderecoEstado";

    // Criando tabela para Pessoa
    private static final String SQL_CREATE_PERSON_TABLE = "CREATE TABLE "
            + PESSOA_TABLE + "(" + PESSOA_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PESSOA_NOME_COLUMN + " TEXT, " + PESSOA_GENERO_COLUMN + " TEXT, "
            + PESSOA_NASCIMENTO_COLUMN + " DATE, " + PESSOA_TELEFONE_COLUMN + " TEXT" + ");";

    // Criando tabela para Endereço
    private static final String SQL_CREATE_ADDRESSES_TABLE = "CREATE TABLE "
            + ENDERECO_TABLE + "(" + ENDERECO_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ENDERECO_CEP_COLUMN + " INTEGER, " + ENDERECO_ENDERECO_COLUMN + " TEXT, "
            + ENDERECO_BAIRRO_COLUMN + " TEXT, " + ENDERECO_CIDADE_COLUMN + " TEXT, "
            + ENDERECO_ESTADO_COLUMN + " TEXT" + ");";

    private static final String SQL_DELETE_PERSON_TABLE =
            "DROP TABLE IF EXISTS " + Pessoa.PessoaDBEntry.TABLE_NAME;

    private static final String SQL_DELETE_ADRESSES_TABLE =
            "DROP TABLE IF EXISTS " + Endereco.EnderecoDBEntry.TABLE_NAME;

    private static BancoSQLiteHelper instance;

    public static synchronized BancoSQLiteHelper getHelper(Context context) {
        if (instance == null)
            instance = new BancoSQLiteHelper(context);
        return instance;
    }

    public BancoSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PERSON_TABLE);
        db.execSQL(SQL_CREATE_ADDRESSES_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PERSON_TABLE);
        db.execSQL(SQL_DELETE_ADRESSES_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
