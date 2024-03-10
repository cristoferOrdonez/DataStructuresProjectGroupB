package com.example.datastructureproject_groupb.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicArray;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

public class AdaptadorPaginaEventos extends RecyclerView.Adapter<AdaptadorPaginaEventos.EventoViewHolder>{

    DynamicArray<EventosEntidad> listaEventos;
    String correoElectronico;

    public AdaptadorPaginaEventos(DynamicArray<EventosEntidad> listaEventos, String correoElectronico) {
        this.listaEventos = listaEventos;
        this.correoElectronico = correoElectronico;
    }
    @NonNull
    @Override
    public AdaptadorPaginaEventos.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_eventos_pagina_eventos, null, false);

        return new AdaptadorPaginaEventos.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPaginaEventos.EventoViewHolder holder, int position) {

        EventosEntidad evento = listaEventos.get(position);

        holder.textViewTituloEvento.setText(evento.getNombreEvento());
        holder.textViewFechaEvento.setText("Fecha: " + evento.getFechaEvento().getDate() + "/" + evento.getFechaEvento().getMonth() + "/" + evento.getFechaEvento().getYear());
        holder.textViewHorarioEvento.setText("Horario: " + evento.getHorarioEvento());
        holder.textViewLugarEvento.setText("Lugar: " + evento.getUbicacionEvento());
        holder.textViewCostoEvento.setText("Costo: " + evento.getCostoEventoConFormato());
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

            textViewTituloEvento = itemView.findViewById(R.id.textViewTituloEventoPaginaEventos);
            textViewFechaEvento = itemView.findViewById(R.id.textViewFechaEventoPaginaEventos);
            textViewHorarioEvento = itemView.findViewById(R.id.textViewHorarioEventoPaginaEventos);
            textViewLugarEvento = itemView.findViewById(R.id.textViewLugarEventoPaginaEventos);
            textViewCostoEvento = itemView.findViewById(R.id.textViewCostoEventoPaginaEventos);
            textViewTipoEvento = itemView.findViewById(R.id.textViewTipoEventoPaginaEventos);

        }
    }
}
