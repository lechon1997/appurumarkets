package com.example.myapplication98;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication98.Controladores.ControladorUsuario;
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.Fragmentos.fragmentBuscador;
import com.example.myapplication98.Fragmentos.fragmentCarrito;
import com.example.myapplication98.Fragmentos.fragmentCuenta;
import com.example.myapplication98.Fragmentos.fragmentInicio;
import com.example.myapplication98.Modelo.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private ControladorVista CV = ControladorVista.getInstance();
    private ControladorUsuario CU = ControladorUsuario.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);
        CV.setNavigation(navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        loadFragment(CV.getFGInicio());

        FragmentManager FM = getSupportFragmentManager();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        boolean recordado = sharedPref.getBoolean("recordado",false);

        if(recordado){
            String usuKey = sharedPref.getString("usuarioRecordado","asd");
            String datos = sharedPref.getString(usuKey,"asd");
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(datos, Usuario.class);
            CU.setUsuario(usuario);
        }

        /*
        int index = FM.getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = FM.getBackStackEntryAt(index);
        String tag = backEntry.getName();
        Fragment f = FM.findFragmentByTag(tag);
        */
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.primerFragmento:

                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    boolean recordado = sharedPref.getBoolean("recordado",false);

                    if(recordado || CU.getSession()){
                        loadFragment(CV.getFGlogeado());
                    } else{
                        loadFragment(CV.getFGLogin());
                    }
                    break;
                case R.id.SegundoFragmento:
                    loadFragment(CV.getFGCarrito());
                    break;
                case R.id.TercerFragmento:
                    loadFragment(CV.getFGInicio());
                    break;
                case R.id.CuartoFragmento:
                    loadFragment(CV.getFGBuscador());
                    break;
            }
            return true;
        }
    };

    private void loadFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentConteiner,fragment);
        transaction.commit();

    }
}