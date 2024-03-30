package com.example.datastructureproject_groupb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaPrincipal;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

import java.util.ArrayList;
import java.util.Date;

public class PaginaPrincipal extends AppCompatActivity {

    Button botonDescubrir, botonCuenta, botonEventos;
    String correoElectronico,tipoUsuario;
    RecyclerView listaEventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        botonDescubrir=findViewById(R.id.botonDescubrirPaginaPrincipal);
        botonCuenta=findViewById(R.id.botonCuentaPaginaPrincipal);
        botonEventos=findViewById(R.id.botonEventosPaginaPrincipal);


        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());
        botonCuenta.setOnClickListener(view -> cambiarACuenta());
        botonEventos.setOnClickListener(view -> cambiarAEventos());

        listaEventos = findViewById(R.id.recyclerViewEventosPaginaPrincipal);
        correoElectronico = getIntent().getStringExtra("correoElectronico");
        tipoUsuario = getIntent().getStringExtra("tipoUsuario");


        listaEventos.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<EventosEntidad> arregloDePrueba = new ArrayList<>();
        arregloDePrueba.add(new EventosEntidad(0, "Evento1", new Date(1, 2, 3), "Unal", 100, 1000, 1, "eventazo"));

        AdaptadorPaginaPrincipal adapter=new AdaptadorPaginaPrincipal(arregloDePrueba, correoElectronico);
        listaEventos.setAdapter(adapter);

    }

    public void cambiarADescubrir() {
        Intent miIntent = new Intent(this, Descubrir.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarACuenta() {
        Intent miIntent = new Intent(this, Cuenta.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarAEventos() {
        Intent miIntent = new Intent(this, Eventos.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }


}