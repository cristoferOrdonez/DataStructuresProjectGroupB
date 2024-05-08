package com.example.datastructureproject_groupb.adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.MostrarUbicacionEvento;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.entidades.Evento;
import com.example.datastructureproject_groupb.fragmentos.PaginaPrincipalFragment;

public class AdaptadorPaginaPrincipal extends RecyclerView.Adapter<AdaptadorPaginaPrincipal.EventoViewHolder> {

    DynamicUnsortedList<Evento> listaEventos;

    public AdaptadorPaginaPrincipal(DynamicUnsortedList<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }
    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);

        return new EventoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = listaEventos.get(position);

        if (evento.getLocalidadEvento() != 21) {
            holder.textViewLugarEvento.setText("Lugar: " + evento.getDireccionEvento());
            holder.textViewTituloEvento.setText(evento.getNombreEvento() + " - Presencial");
        } else {
            holder.textViewTituloEvento.setText(evento.getNombreEvento() +  " - Virtual");
            holder.textViewLugarEvento.setText("Plataforma: " + evento.getUbicacionEvento());
        }
        holder.textViewFechaEvento.setText("" + evento.getFechaEventoString()+" • ");
        holder.textViewHorarioEvento.setText("" + evento.getHorarioEvento());
        holder.textViewCostoEvento.setText("Costo: " + evento.getCostoEventoConFormato());
        holder.textViewTipoEvento.setText("Tipo: " + Bocu.INTERESES[evento.getCategoriaEvento()]);

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

                Evento evento = listaEventos.get(getAdapterPosition());

                Activity activity = (Activity) v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.card_layout_presionado, null);
                TextView tituloEvento = view.findViewById(R.id.textViewTituloEventoPaginaPrincipal),
                        fechaEvento = view.findViewById(R.id.textViewFechaEventoPaginaPrincipal),
                        horarioEvento = view.findViewById(R.id.textViewHorarioEventoPaginaPrincipal),
                        lugarEvento = view.findViewById(R.id.textViewLugarEventoPaginaPrincipal),
                        costoEvento = view.findViewById(R.id.textViewCostoEventoPaginaPrincipal),
                        tipoEvento = view.findViewById(R.id.textViewTipoEventoPaginaPrincipal),
                        descripcionEvento = view.findViewById(R.id.textViewDescripcionEventoPaginaPrincipal);

                Button boton = view.findViewById(R.id.botonMostrarUbicacion);

                boton.setOnClickListener(i -> {
                        Intent miIntent = new Intent(v.getContext(), MostrarUbicacionEvento.class);
                        miIntent.putExtra("UBICACION_EVENTO", evento.getUbicacionEvento());
                        miIntent.putExtra("NOMBRE_EVENTO", evento.getNombreEvento());
                        v.getContext().startActivity(miIntent);
                });

                if (evento.getLocalidadEvento() != 21) {
                    tituloEvento.setText(evento.getNombreEvento() + " - Presencial");
                    lugarEvento.setText("Lugar: " + evento.getDireccionEvento());
                } else {
                    tituloEvento.setText(evento.getNombreEvento() + " - Virtual");
                    lugarEvento.setText("Plataforma: " + evento.getUbicacionEvento());
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

                if(PaginaPrincipalFragment.historialEventos.isEmpty())
                    showFadeInAnimation(PaginaPrincipalFragment.botonHistorial, 500);


                PaginaPrincipalFragment.historialEventos.push(evento);

            });

        }
    }

    private void showFadeInAnimation(View view, long duration){

        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(duration);

        view.clearAnimation();
        view.startAnimation(fadeIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
            }
        }, duration);

    }

}