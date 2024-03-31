package com.example.datastructureproject_groupb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StaticUnsortedList;
import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaEventos;
import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaPrincipal;
import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

import java.util.Date;

public class Eventos extends AppCompatActivity {
    Button botonPaginaPrincipal, botonCuenta, botonDescubrir, botonEditarEvento, botonEliimnarEvento;
    ImageButton botonCrearEvento;
    String correoElectronico;
    RecyclerView listaEventos;
    String CorreoElectronicoExpositor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        CorreoElectronicoExpositor = getIntent().getStringExtra(correoElectronico);

        botonPaginaPrincipal=findViewById(R.id.botonPaginaPrincipalEventos);
        botonCuenta=findViewById(R.id.botonCuentaEventos);
        botonDescubrir=findViewById(R.id.botonDescubrirEventos);
        botonCrearEvento=findViewById(R.id.imageButtonCrearEvento);

        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonCuenta.setOnClickListener(view -> cambiarACuenta());
        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());
        botonCrearEvento.setOnClickListener(view -> cambiarACrearEventos());


        listaEventos = findViewById(R.id.RecyclerViewEventosPaginaEventos);
        correoElectronico = getIntent().getStringExtra("correoElectronico");

        listaEventos.setLayoutManager(new LinearLayoutManager(this));
        DbEventos dbEventos = new DbEventos(this);
        StaticUnsortedList<EventosEntidad> eventos = dbEventos.obtenerEventos();

        AdaptadorPaginaEventos adapter=new AdaptadorPaginaEventos(eventos, correoElectronico);
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
    public void cambiarACrearEventos() {
        Intent miIntent = new Intent(this, CrearEventos.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }

}







