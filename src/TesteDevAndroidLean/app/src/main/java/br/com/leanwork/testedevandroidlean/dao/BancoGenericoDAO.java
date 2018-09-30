package br.com.leanwork.testedevandroidlean.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.leanwork.testedevandroidlean.BancoSQLiteHelper;

public class BancoGenericoDAO {
    SQLiteDatabase database;
    private BancoSQLiteHelper dbHelper;
    private Context mContext;

    public BancoGenericoDAO(Context context) {
        this.mContext = context;
        dbHelper = BancoSQLiteHelper.getHelper(mContext);
        open();
    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = BancoSQLiteHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    /*public void close() {
        dbHelper.close();
        database = null;
    }*/

}

