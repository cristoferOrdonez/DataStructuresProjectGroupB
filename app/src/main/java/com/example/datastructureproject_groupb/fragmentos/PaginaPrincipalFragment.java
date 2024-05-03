package com.example.datastructureproject_groupb.fragmentos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StackLinkedList;
import com.example.datastructureproject_groupb.MostrarUbicacionEvento;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaPrincipal;
import com.example.datastructureproject_groupb.entidades.Evento;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class PaginaPrincipalFragment extends Fragment {

    public static StackLinkedList<Evento> historialEventos;
    public static ImageButton botonHistorial;
    private RecyclerView listaEventos;

    public PaginaPrincipalFragment() {
    }

    public static PaginaPrincipalFragment newInstance() {
        PaginaPrincipalFragment fragment = new PaginaPrincipalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pagina_principal, container, false);

        historialEventos = new StackLinkedList<>();

        listaEventos = root.findViewById(R.id.recyclerViewEventosPaginaPrincipal);

        listaEventos.setLayoutManager(new LinearLayoutManager(getContext()));

        AdaptadorPaginaPrincipal adapter = new AdaptadorPaginaPrincipal(Bocu.eventos);
        listaEventos.setAdapter(adapter);

        botonHistorial = root.findViewById(R.id.imageButtonHistorial);
        botonHistorial.setVisibility(View.INVISIBLE);

        botonHistorial.setOnClickListener(i -> {
            if(!historialEventos.isEmpty())
                mostrarDialogHistorial(historialEventos.pop());
            if(historialEventos.isEmpty())
                showFadeOutAnimation(botonHistorial, 500);
        });

        return root;
    }

    public void mostrarDialogHistorial(Evento evento){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.view_evento_presionado_pagina_principal, null);
        TextView tituloEvento = view.findViewById(R.id.textViewTituloEventoPaginaPrincipal),
                fechaEvento = view.findViewById(R.id.textViewFechaEventoPaginaPrincipal),
                horarioEvento = view.findViewById(R.id.textViewHorarioEventoPaginaPrincipal),
                lugarEvento = view.findViewById(R.id.textViewLugarEventoPaginaPrincipal),
                costoEvento = view.findViewById(R.id.textViewCostoEventoPaginaPrincipal),
                tipoEvento = view.findViewById(R.id.textViewTipoEventoPaginaPrincipal),
                descripcionEvento = view.findViewById(R.id.textViewDescripcionEventoPaginaPrincipal);

        Button boton = view.findViewById(R.id.botonMostrarUbicacion);

        boton.setOnClickListener(i -> {
            Intent miIntent = new Intent(getContext(), MostrarUbicacionEvento.class);
            miIntent.putExtra("UBICACION_EVENTO", evento.getDirePlatEvento());
            miIntent.putExtra("NOMBRE_EVENTO", evento.getNombreEvento());
            startActivity(miIntent);
        });

        if (evento.getUbicacionEvento() != 21) {
            tituloEvento.setText(evento.getNombreEvento() + " - Presencial");
            lugarEvento.setText("Lugar: " + evento.getDireccionEvento());
        } else {
            tituloEvento.setText(evento.getNombreEvento() + " - Virtual");
            lugarEvento.setText("Plataforma: " + evento.getDirePlatEvento());
        }
        fechaEvento.setText("Fecha: " + evento.getFechaEventoString());
        horarioEvento.setText("Horario: " + evento.getHorarioEvento());
        costoEvento.setText("Costo: " + evento.getCostoEventoConFormato());
        tipoEvento.setText("Tipo: " + Bocu.INTERESES[evento.getCategoriaEvento()]);
        descripcionEvento.setText("Descripción: " + evento.getDescripcionEvento());

        builder.setView(view);

        builder.show();

    }

    private void showFadeOutAnimation(View view, long duration){

        AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setStartOffset(0);
        fadeOut.setDuration(duration);

        view.clearAnimation();
        view.startAnimation(fadeOut);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.INVISIBLE);
            }
        }, duration);

    }

}