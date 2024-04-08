package com.example.datastructureproject_groupb.fragmentos;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StackLinkedList;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaPrincipal;
import com.example.datastructureproject_groupb.entidades.Evento;

public class PaginaPrincipalFragment extends Fragment {

    public static StackLinkedList<Evento> historialEventos;
    private ImageButton botonHistorial;
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

        botonHistorial.setOnClickListener(i -> {
            if(!historialEventos.isEmpty())
                mostrarDialogHistorial(historialEventos.pop());
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

        tituloEvento.setText(evento.getNombreEvento());
        fechaEvento.setText("Fecha: " + evento.getFechaEventoString());
        horarioEvento.setText("Horario: " + evento.getHorarioEvento());
        lugarEvento.setText("Lugar: " + evento.getUbicacionEvento());
        costoEvento.setText("Costo: " + evento.getCostoEventoConFormato());
        tipoEvento.setText("Tipo: " + Bocu.INTERESES[evento.getCategoriaEvento()]);
        descripcionEvento.setText("Descripción: " + evento.getDescripcionEvento());

        builder.setView(view);

        builder.show();

    }

}