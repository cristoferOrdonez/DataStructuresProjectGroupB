package com.example.datastructureproject_groupb.adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.entidades.evento.EditarEventosPresencial;
import com.example.datastructureproject_groupb.entidades.evento.EditarEventosVirtual;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.entidades.evento.Evento;

public class AdaptadorPaginaEventos extends RecyclerView.Adapter<AdaptadorPaginaEventos.EventoViewHolder>{

    DynamicUnsortedList<Evento> listaEventos;

    public AdaptadorPaginaEventos(DynamicUnsortedList<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    @NonNull
    @Override
    public AdaptadorPaginaEventos.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_eventos, parent, false);

        return new AdaptadorPaginaEventos.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPaginaEventos.EventoViewHolder holder, int position) {

        Evento evento = listaEventos.get(position);

        if (evento.getUbicacionEvento() != 21) {
            holder.textViewTituloEvento.setText(evento.getNombreEvento() + " - Presencial");
            holder.textViewLugarEvento.setText("Lugar: " + evento.getDireccionEvento());
        } else {
            holder.textViewLugarEvento.setText("Plataforma: " + evento.getDirePlatEvento());
            holder.textViewTituloEvento.setText(evento.getNombreEvento() + " - Virtual");
        }
        holder.textViewFechaEvento.setText("Fecha: " + evento.getFechaEventoString());
        holder.textViewHorarioEvento.setText("Horario: " + evento.getHorarioEvento());
        holder.textViewCostoEvento.setText("Costo: " + evento.getCostoEventoConFormato());
        holder.textViewTipoEvento.setText("Tipo: " + Bocu.INTERESES[evento.getCategoriaEvento()]);


        holder.botonEliminarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Eliminar Evento");
                builder.setMessage("¿Está seguro de que desea eliminar este evento?");

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = holder.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listaEventos.remove(position);
                            if(!Bocu.eventos.get(Bocu.eventos.size() - 1).getCorreoAutor().equals(Bocu.usuario.getCorreoElectronico())) {
                                Bocu.eventos.remove(Bocu.posicionesEventosExpositor.get(position));
                                Bocu.posicionesEventosExpositor.remove(position);
                            } else {
                                Bocu.eventos.remove(Bocu.posicionesEventosExpositor.get(position));
                                int posicionOriginal = Bocu.posicionesEventosExpositor.get(position);
                                Bocu.posicionesEventosExpositor.remove(position);
                                Bocu.posicionesEventosExpositor.set(position, posicionOriginal);
                            }

                            new DbEventos(v.getContext()).eliminarEvento(evento.getId());
                            notifyDataSetChanged();
                        }

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.botonEditarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (evento.getUbicacionEvento() != 21) {
                    intent = new Intent(v.getContext(), EditarEventosPresencial.class);
                    intent.putExtra("UBICACION_EVENTO", evento.getDirePlatEvento());
                } else {
                    intent = new Intent(v.getContext(), EditarEventosVirtual.class);
                    intent.putExtra("PLATAFORMA_EVENTO", evento.getDirePlatEvento());
                }

                int position = holder.getAdapterPosition();
                intent.putExtra("ID_EVENTO",evento.getId());
                intent.putExtra("POSITION", position);
                intent.putExtra("NOMBRE_EVENTO", evento.getNombreEvento());
                intent.putExtra("FECHA_EVENTO", evento.getFechaEvento().getDate() + "/" + evento.getFechaEvento().getMonth() + "/" + evento.getFechaEvento().getYear());

                intent.putExtra("COSTO_EVENTO", String.valueOf(evento.getCostoEvento()));
                intent.putExtra("HORARIO_EVENTO", evento.getHorarioEvento());
                intent.putExtra("DESCRIPCION_EVENTO", evento.getDescripcionEvento());
                intent.putExtra("LOCALIDAD_EVENTO", evento.getUbicacionEvento());
                intent.putExtra("CATEGORIA_EVENTO", evento.getCategoriaEvento());
                v.getContext().startActivity(intent);
                ((Activity)v.getContext()).finishAffinity();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaEventos.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTituloEvento, textViewFechaEvento,textViewHorarioEvento, textViewLugarEvento, textViewCostoEvento, textViewTipoEvento;
        ImageButton botonEditarEvento, botonEliminarEvento;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTituloEvento = itemView.findViewById(R.id.textViewTituloEventoPaginaEventos);
            textViewFechaEvento = itemView.findViewById(R.id.textViewFechaEventoPaginaEventos);
            textViewHorarioEvento = itemView.findViewById(R.id.textViewHorarioEventoPaginaEventos);
            textViewLugarEvento = itemView.findViewById(R.id.textViewLugarEventoPaginaEventos);
            textViewCostoEvento = itemView.findViewById(R.id.textViewCostoEventoPaginaEventos);
            textViewTipoEvento = itemView.findViewById(R.id.textViewTipoEventoPaginaEventos);

            botonEditarEvento = itemView.findViewById(R.id.imageButtonEditarEvento);
            botonEliminarEvento = itemView.findViewById(R.id.imageButtonEliminarEvento);
        }
    }
}
