package com.example.datastructureproject_groupb.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StackLinkedList;
import com.example.datastructureproject_groupb.entidades.evento.MostrarUbicacionEvento;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaPrincipal;
import com.example.datastructureproject_groupb.entidades.evento.Evento;

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
        View view = inflater.inflate(R.layout.card_layout_presionado, null);
        TextView tituloEvento = view.findViewById(R.id.textViewTituloEventoPaginaPrincipal),
                fechaEvento = view.findViewById(R.id.textViewFechaEventoPaginaPrincipal),
                horarioEvento = view.findViewById(R.id.textViewHorarioEventoPaginaPrincipal),
                lugarEvento = view.findViewById(R.id.textViewLugarEventoPaginaPrincipal),
                costoEvento = view.findViewById(R.id.textViewCostoEventoPaginaPrincipal),
                tipoEvento = view.findViewById(R.id.textViewTipoEventoPaginaPrincipal),
                descripcionEvento = view.findViewById(R.id.textViewDescripcionEventoPaginaPrincipal);

        Button boton = view.findViewById(R.id.botonMostrarUbicacion);

        if (evento.getUbicacionEvento() != 21) {
            boton.setOnClickListener(i -> {
                Intent miIntent = new Intent(getContext(), MostrarUbicacionEvento.class);
                miIntent.putExtra("UBICACION_EVENTO", evento.getDirePlatEvento());
                miIntent.putExtra("NOMBRE_EVENTO", evento.getNombreEvento());
                startActivity(miIntent);
            });
            tituloEvento.setText(evento.getNombreEvento() + " - Presencial");
            lugarEvento.setText("Lugar: " + evento.getDireccionEvento());
        } else {
            boton.setVisibility(View.GONE);
            tituloEvento.setText(evento.getNombreEvento() + " - Virtual");
            lugarEvento.setText("Plataforma: " + evento.getDirePlatEvento());
        }
        fechaEvento.setText("Fecha: " + evento.getFechaEventoString());
        horarioEvento.setText("Horario: " + evento.getHorarioEvento());
        costoEvento.setText("Costo: " + evento.getCostoEventoConFormato());
        tipoEvento.setText("Tipo: " + Bocu.INTERESES[evento.getCategoriaEvento()]);
        descripcionEvento.setText("Descripción: " + evento.getDescripcionEvento());

        builder.setView(view);

        AlertDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        dialog.getWindow().setAttributes(layoutParams);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.getWindow().setDimAmount(0.9f);

        dialog.show();

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