package com.example.datastructureproject_groupb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaEventos;

public class Eventos extends AppCompatActivity {
    Button botonPaginaPrincipal, botonCuenta, botonDescubrir;
    ImageButton botonCrearEvento;
    RecyclerView listaEventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        botonPaginaPrincipal=findViewById(R.id.botonPaginaPrincipalEventos);
        botonCuenta=findViewById(R.id.botonCuentaEventos);
        botonDescubrir=findViewById(R.id.botonDescubrirEventos);
        botonCrearEvento=findViewById(R.id.imageButtonCrearEvento);

        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonCuenta.setOnClickListener(view -> cambiarACuenta());
        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());
        botonCrearEvento.setOnClickListener(view -> cambiarACrearEventos());


        listaEventos = findViewById(R.id.RecyclerViewEventosPaginaEventos);

        listaEventos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        AdaptadorPaginaEventos adapter=new AdaptadorPaginaEventos(Bocu.eventosExpositor);
        listaEventos.setAdapter(adapter);
        listaEventos.scrollToPosition(Bocu.eventosExpositor.size() - 1);
    }

    public void cambiarAPaginaPrincipal() {
        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarACuenta() {
        Intent miIntent = new Intent(this, Cuenta.class);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarADescubrir() {
        Intent miIntent = new Intent(this, Descubrir.class);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarACrearEventos() {
        Intent miIntent = new Intent(this, CrearEventos.class);
        startActivity(miIntent);
        finishAffinity();
    }

}







