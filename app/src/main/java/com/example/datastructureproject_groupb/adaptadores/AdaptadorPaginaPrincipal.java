package com.example.datastructureproject_groupb.adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StaticUnsortedList;
import com.example.datastructureproject_groupb.PaginaPrincipal;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

public class AdaptadorPaginaPrincipal extends RecyclerView.Adapter<AdaptadorPaginaPrincipal.EventoViewHolder> {

    StaticUnsortedList<EventosEntidad> listaEventos;
    String correoElectronico;

    public AdaptadorPaginaPrincipal(StaticUnsortedList<EventosEntidad> listaEventos, String correoElectronico) {
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
        holder.textViewFechaEvento.setText("Fecha: " + evento.getFechaEvento().getDate() + "/" + evento.getFechaEvento().getMonth() + "/" + evento.getFechaEvento().getYear());
        holder.textViewHorarioEvento.setText("Horario: " + evento.getHorarioEvento());
        holder.textViewLugarEvento.setText("Lugar: " + evento.getUbicacionEvento());
        holder.textViewCostoEvento.setText("Costo: " + evento.getCostoEventoConFormato());
        if(evento.getCategoriaEvento()==0)
            holder.textViewTipoEvento.setText("Tipo: Música");
        else
            holder.textViewTipoEvento.setText("Tipo: Talleres");
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

            itemView.setOnClickListener(v -> {

                EventosEntidad evento = listaEventos.get(getAdapterPosition());

                Activity activity = (Activity) v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = activity.getLayoutInflater();
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
                if(evento.getCategoriaEvento()==0)
                    tipoEvento.setText("Tipo: Música");
                else
                    tipoEvento.setText("Tipo: Talleres");
                descripcionEvento.setText(evento.getDescripcionEvento());

                builder.setView(view);

                builder.show();

                PaginaPrincipal.historialEventos.push(evento);

            });

        }
    }
}