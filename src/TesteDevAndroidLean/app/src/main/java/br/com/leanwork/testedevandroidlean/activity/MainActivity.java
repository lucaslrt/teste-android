package br.com.leanwork.testedevandroidlean.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.com.leanwork.testedevandroidlean.R;
import br.com.leanwork.testedevandroidlean.fragment.FormEnderecoFragment;
import br.com.leanwork.testedevandroidlean.fragment.FormPessoaFragment;
import br.com.leanwork.testedevandroidlean.fragment.ListaEnderecosFragment;
import br.com.leanwork.testedevandroidlean.fragment.ListaPessoasFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
            switch (item.getItemId()) {
                case R.id.nav_lista_enderecos:
                    fragmentTransaction
                            .replace(R.id.framePrincipal, new ListaEnderecosFragment())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.nav_lista_pessoas:
                    fragmentTransaction
                            .replace(R.id.framePrincipal, new ListaPessoasFragment())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.nav_nova_pessoa:
                    fragmentTransaction
                            .replace(R.id.framePrincipal, new FormPessoaFragment())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.nav_novo_endereco:
                    fragmentTransaction
                            .replace(R.id.framePrincipal, new FormEnderecoFragment())
                            .addToBackStack(null)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // abrindo a lista de endereços primeiramente
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        fragmentTransaction
                .replace(R.id.framePrincipal, new ListaEnderecosFragment())
                .addToBackStack(null)
                .commit();
    }

}
