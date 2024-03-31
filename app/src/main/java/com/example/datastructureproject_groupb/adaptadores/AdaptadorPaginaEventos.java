package com.example.datastructureproject_groupb.adaptadores;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datastructureproject_groupb.CrearEventos;
import com.example.datastructureproject_groupb.EditarEventos;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StaticUnsortedList;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

public class AdaptadorPaginaEventos extends RecyclerView.Adapter<AdaptadorPaginaEventos.EventoViewHolder>{

    StaticUnsortedList<EventosEntidad> listaEventos;
    String correoElectronico;

    public AdaptadorPaginaEventos(StaticUnsortedList<EventosEntidad> listaEventos, String correoElectronico) {
        this.listaEventos = listaEventos;
        this.correoElectronico = correoElectronico;
        this.idEvento = -1;
    }

    private int idEvento;

    public int getIdEvento() {
        return idEvento;
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

        holder.botonEliminarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar la acción de eliminar el evento en la posición "position"
                // Puedes mostrar un diálogo de confirmación o eliminar el evento directamente de la lista
                // y notificar al adaptador para actualizar la vista.
            }
        });

        holder.botonEditarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idEvento = evento.getId();
                Intent intent = new Intent(v.getContext(), EditarEventos.class);
                intent.putExtra("ID_EVENTO", idEvento);
                v.getContext().startActivity(intent);}
        });
    }

    @Override
    public int getItemCount() {
        return listaEventos.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTituloEvento, textViewFechaEvento,textViewHorarioEvento, textViewLugarEvento, textViewCostoEvento, textViewTipoEvento;
        Button botonEditarEvento, botonEliminarEvento;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTituloEvento = itemView.findViewById(R.id.textViewTituloEventoPaginaEventos);
            textViewFechaEvento = itemView.findViewById(R.id.textViewFechaEventoPaginaEventos);
            textViewHorarioEvento = itemView.findViewById(R.id.textViewHorarioEventoPaginaEventos);
            textViewLugarEvento = itemView.findViewById(R.id.textViewLugarEventoPaginaEventos);
            textViewCostoEvento = itemView.findViewById(R.id.textViewCostoEventoPaginaEventos);
            textViewTipoEvento = itemView.findViewById(R.id.textViewTipoEventoPaginaEventos);

            botonEditarEvento = itemView.findViewById(R.id.botonEditarEventos);
            botonEliminarEvento = itemView.findViewById(R.id.botonEliminarEventos);
        }
    }
}
