package com.example.datastructureproject_groupb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicArray;
import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaEventos;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

import java.util.Date;

public class Eventos extends AppCompatActivity {
    Button botonPaginaPrincipal, botonCuenta, botonDescubrir;
    String correoElectronico;
    RecyclerView listaEventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        botonPaginaPrincipal=findViewById(R.id.botonPaginaPrincipalEventos);
        botonCuenta=findViewById(R.id.botonCuentaEventos);
        botonDescubrir=findViewById(R.id.botonDescubrirEventos);

        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonCuenta.setOnClickListener(view -> cambiarACuenta());
        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());


        listaEventos = findViewById(R.id.RecyclerViewEventosPaginaEventos);
        correoElectronico = getIntent().getStringExtra("email");

        listaEventos.setLayoutManager(new LinearLayoutManager(this));

        DynamicArray<EventosEntidad> arregloDePrueba = new DynamicArray<>(new EventosEntidad[10]);
        arregloDePrueba.pushBack(new EventosEntidad(0, "EventoExpositor", new Date(2024, 4, 15), "PUJ", 112, 5000, "8:00am - 12:00pm", 1284, "Reunión pueblos afrocolombianos. Danza y cultura."));

        AdaptadorPaginaEventos adapter=new AdaptadorPaginaEventos(arregloDePrueba, correoElectronico);
        listaEventos.setAdapter(adapter);


    }

    public void cambiarAPaginaPrincipal() {
        Intent miIntent = new Intent(this, PaginaPrincipal.class);
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
    public void cambiarADescubrir() {
        Intent miIntent = new Intent(this, Descubrir.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }

}







