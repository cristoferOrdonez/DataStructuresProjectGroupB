package com.example.datastructureproject_groupb;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.datastructureproject_groupb.fragmentos.CuentaFragment;
import com.example.datastructureproject_groupb.fragmentos.DescubrirFragment;
import com.example.datastructureproject_groupb.fragmentos.EventosFragment;
import com.example.datastructureproject_groupb.fragmentos.PaginaPrincipalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PaginaInicio extends AppCompatActivity {

    public static final int PAGINA_PRINCIPAL = 0;
    public static final int DESCUBRIR = 1;
    public static final int EVENTOS = 2;
    public static final int CUENTA = 3;
    public static int intensionInicializacion = PAGINA_PRINCIPAL;
    private BottomNavigationView menu;
    private Fragment fragmento;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicio);

        initView();
        initValues();
        initListener();

        switch (intensionInicializacion) {
            case PAGINA_PRINCIPAL:
                menu.setSelectedItemId(R.id.menuPaginaPrincipal);
                fragmento = PaginaPrincipalFragment.newInstance();
                openFragment(fragmento);
                 break;
            case DESCUBRIR:
                menu.setSelectedItemId(R.id.menuDescubrir);
                fragmento = DescubrirFragment.newInstance();
                openFragment(fragmento);
                break;
            case EVENTOS:
                menu.setSelectedItemId(R.id.menuEventos);
                fragmento = EventosFragment.newInstance();
                openFragment(fragmento);
                break;
            case CUENTA:
                menu.setSelectedItemId(R.id.menuCuenta);
                fragmento = CuentaFragment.newInstance();
                openFragment(fragmento);
                break;
        }

    }

    private void initView(){
        menu = findViewById(R.id.menu);
    }

    private void initValues(){
        manager = getSupportFragmentManager();
    }

    private void initListener(){
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int idMenu = menuItem.getItemId();

                switch (idMenu) {
                    case R.id.menuPaginaPrincipal:
                        fragmento = PaginaPrincipalFragment.newInstance();
                        openFragment(fragmento);
                        return true;
                    case R.id.menuDescubrir:
                        fragmento = DescubrirFragment.newInstance();
                        openFragment(fragmento);
                        return true;
                    case R.id.menuEventos:
                        fragmento = EventosFragment.newInstance();
                        openFragment(fragmento);
                        return true;
                    case R.id.menuCuenta:
                        fragmento = CuentaFragment.newInstance();
                        openFragment(fragmento);
                        return true;
                }

                return false;
            }
        });
    }

    private void openFragment(Fragment fragmento){
        manager.beginTransaction()
                .replace(R.id.frameContenedor, fragmento)
                .commit();
    }

    public BottomNavigationView getMenu(){
        return menu;
    }

}