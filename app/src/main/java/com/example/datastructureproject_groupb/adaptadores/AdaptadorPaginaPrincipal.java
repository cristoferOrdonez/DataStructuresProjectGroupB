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
        /*
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_meta, null, false);

        return new EventoViewHolder(view);
         */

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {

        /*
        String fechaMetaString = listaMetas.get(position).getFechaMeta();

        holder.viewNombreMeta.setText(listaMetas.get(position).getNombreMeta());
        holder.viewFechaMeta.setText(MetodosComunes.obtenerPrefijoMes(Integer.parseInt(fechaMetaString.substring(0, 2))) + " " + fechaMetaString.substring(3));
        holder.viewMontoMeta.setText("Monto Total:  " + listaMetas.get(position).getMontoTotalFormateado() + "  COP");
        holder.viewMontoMensual.setText("Monto Mensual:  " + listaMetas.get(position).getMontoMensualFormateado() + "  COP"); // Nueva línea


         */


    }

    @Override
    public int getItemCount() {
        return listaEventos.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombreMeta, viewFechaMeta,viewMontoMensual, viewMontoMeta;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            /*
            viewNombreMeta=itemView.findViewById(R.id.viewNombreMeta);
            viewFechaMeta=itemView.findViewById(R.id.viewFechaMeta);
            viewMontoMeta=itemView.findViewById(R.id.viewMontoMeta);
            viewMontoMensual =itemView.findViewById(R.id.viewMontoMensual);

            itemView.setOnClickListener(view -> {

                Context context = view.getContext();
                Intent intent =new Intent(context, VerMeta.class );
                intent.putExtra("ID", listaMetas.get(getAdapterPosition()).getId());
                intent.putExtra("correoElectronico", correoElectronico); // Pasa el correo electrónico con el Intent
                context.startActivity(intent);
                ((Activity)context).finishAffinity();

            });

             */


        }
    }
}