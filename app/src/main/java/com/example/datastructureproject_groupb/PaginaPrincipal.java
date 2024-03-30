package com.example.datastructureproject_groupb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StackLinkedList;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StaticUnsortedList;
import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaPrincipal;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

import java.util.Date;

public class PaginaPrincipal extends AppCompatActivity {

    public static StackLinkedList<EventosEntidad> historialEventos;
    private Button botonDescubrir, botonCuenta, botonEventos;
    private ImageButton botonHistorial;
    private String correoElectronico;
    private RecyclerView listaEventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        historialEventos = new StackLinkedList<>();

        botonDescubrir=findViewById(R.id.botonDescubrirPaginaPrincipal);
        botonCuenta=findViewById(R.id.botonCuentaPaginaPrincipal);
        botonEventos=findViewById(R.id.botonEventosPaginaPrincipal);

        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());
        botonCuenta.setOnClickListener(view -> cambiarACuenta());
        botonEventos.setOnClickListener(view -> cambiarAEventos());

        listaEventos = findViewById(R.id.recyclerViewEventosPaginaPrincipal);
        correoElectronico = getIntent().getStringExtra("correoElectronico");


        listaEventos.setLayoutManager(new LinearLayoutManager(this));

        //StaticUnsortedList<EventosEntidad> arregloDePrueba = new DbEventos(this).obtenerEventos();
        StaticUnsortedList<EventosEntidad> arregloDePrueba = new StaticUnsortedList<>(10);
        arregloDePrueba.insert(new EventosEntidad(0, "Evento1", new Date(2024, 2, 3), "Unal", 100, 1000, "12:00pm - 4:00pm", 1984, "eventazo"));
        arregloDePrueba.insert(new EventosEntidad(0, "Evento2", new Date(2024, 2, 3), "Unal", 100, 1000, "12:00pm - 4:00pm", 1984, "eventazo"));
        arregloDePrueba.insert(new EventosEntidad(0, "Evento3", new Date(2024, 2, 3), "Unal", 100, 1000, "12:00pm - 4:00pm", 1984, "eventazo"));
        arregloDePrueba.insert(new EventosEntidad(0, "Evento4", new Date(2024, 2, 3), "Unal", 100, 1000, "12:00pm - 4:00pm", 1984, "eventazo"));

        AdaptadorPaginaPrincipal adapter=new AdaptadorPaginaPrincipal(arregloDePrueba, correoElectronico);
        listaEventos.setAdapter(adapter);

        botonHistorial = findViewById(R.id.imageButtonHistorial);

        botonHistorial.setOnClickListener(i -> {
            if(!historialEventos.isEmpty())
                mostrarDialogHistorial(historialEventos.pop());
        });

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

    public void mostrarDialogHistorial(EventosEntidad evento){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.view_evento_presionado_pagina_principal, null);
        TextView tituloEvento = view.findViewById(R.id.textViewTituloEventoPaginaPrincipal),
                fechaEvento = view.findViewById(R.id.textViewFechaEventoPaginaPrincipal),
                horarioEvento = view.findViewById(R.id.textViewHorarioEventoPaginaPrincipal),
                lugarEvento = view.findViewById(R.id.textViewLugarEventoPaginaPrincipal),
                costoEvento = view.findViewById(R.id.textViewCostoEventoPaginaPrincipal),
                tipoEvento = view.findViewById(R.id.textViewTipoEventoPaginaPrincipal),
                descripcionEvento = view.findViewById(R.id.textViewDescripcionEventoPaginaPrincipal);

        tituloEvento.setText(evento.getNombreEvento());
        fechaEvento.setText(evento.getFechaEventoString());
        horarioEvento.setText(evento.getHorarioEvento());
        lugarEvento.setText(evento.getUbicacionEvento());
        costoEvento.setText(evento.getCostoEventoConFormato());
        tipoEvento.setText("Tipo de evento: " + evento.getCategoriaEvento());
        descripcionEvento.setText(evento.getDescripcionEvento());

        builder.setView(view);

        builder.show();

    }

}