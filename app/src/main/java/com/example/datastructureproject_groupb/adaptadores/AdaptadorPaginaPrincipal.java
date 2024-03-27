package com.example.datastructureproject_groupb.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

import java.util.ArrayList;

public class AdaptadorPaginaPrincipal extends RecyclerView.Adapter<AdaptadorPaginaPrincipal.EventoViewHolder> {

    ArrayList<EventosEntidad> listaEventos;
    String correoElectronico;

    public AdaptadorPaginaPrincipal(ArrayList<EventosEntidad> listaEventos, String correoElectronico) {
        this.listaEventos = listaEventos;
        this.correoElectronico = correoElectronico;
    }
    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_eventos_pagina_principal, null, false);

        return new EventoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {

        EventosEntidad evento = listaEventos.get(position);

        holder.textViewTituloEvento.setText(evento.getNombreEvento());
        holder.textViewFechaEvento.setText("Fecha: " + evento.getFechaEvento().getDay() + "/" + (evento.getFechaEvento().getMonth() + 1) + "/" + evento.getFechaEvento().getYear());
        //holder.textViewHorarioEvento
        holder.textViewLugarEvento.setText("Lugar: " + evento.getUbicacionEvento());
        holder.textViewCostoEvento.setText("Costo: " + evento.getCostoEvento());
        holder.textViewTipoEvento.setText("Tipo: " + evento.getCategoriaEvento());


    }

    @Override
    public int getItemCount() {
        return listaEventos.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTituloEvento, textViewFechaEvento,textViewHorarioEvento, textViewLugarEvento, textViewCostoEvento, textViewTipoEvento;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTituloEvento = itemView.findViewById(R.id.textViewTituloEventoPaginaPrincipal);
            textViewFechaEvento = itemView.findViewById(R.id.textViewFechaEventoPaginaPrincipal);
            textViewHorarioEvento = itemView.findViewById(R.id.textViewHorarioEventoPaginaPrincipal);
            textViewLugarEvento = itemView.findViewById(R.id.textViewLugarEventoPaginaPrincipal);
            textViewCostoEvento = itemView.findViewById(R.id.textViewCostoEventoPaginaPrincipal);
            textViewTipoEvento = itemView.findViewById(R.id.textViewTipoEventoPaginaPrincipal);

        }
    }
}