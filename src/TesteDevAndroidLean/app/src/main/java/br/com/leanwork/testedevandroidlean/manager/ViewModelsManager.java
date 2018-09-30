package br.com.leanwork.testedevandroidlean.manager;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import br.com.leanwork.testedevandroidlean.BancoSQLiteHelper;

public class ViewModelsManager extends ViewModelProvider.NewInstanceFactory {

    private final BancoSQLiteHelper dbHelper;

    ViewModelsManager(Context context) {
        this.dbHelper = new BancoSQLiteHelper(context);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {


        return super.create(modelClass);
    }

}
