package com.example.datastructureproject_groupb.fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.CrearEventosPresencial;
import com.example.datastructureproject_groupb.CrearEventosVirtual;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaEventos;

public class EventosFragment extends Fragment {

    ImageButton botonCrearEvento;
    RecyclerView listaEventos;

    public EventosFragment() {
    }

    public static EventosFragment newInstance() {
        EventosFragment fragment = new EventosFragment();
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

        View root = inflater.inflate(R.layout.fragment_eventos, container, false);

        botonCrearEvento = root.findViewById(R.id.imageButtonCrearEvento);

        botonCrearEvento.setOnClickListener(view -> mostrarDialogo());


        listaEventos = root.findViewById(R.id.RecyclerViewEventosPaginaEventos);

        listaEventos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));

        AdaptadorPaginaEventos adapter = new AdaptadorPaginaEventos(Bocu.eventosExpositor);
        listaEventos.setAdapter(adapter);
        listaEventos.scrollToPosition(Bocu.eventosExpositor.size() - 1);

        return root;

    }

    public void cambiarACrearEventosPresencial() {
        Intent miIntent = new Intent(getActivity(), CrearEventosPresencial.class);
        startActivity(miIntent);
        getActivity().finishAffinity();
    }
    public void cambiarACrearEventosVirtual() {
        Intent miIntent = new Intent(getActivity(), CrearEventosVirtual.class);
        startActivity(miIntent);
        getActivity().finishAffinity();
    }


    private void mostrarDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Selecciona la modalidad del evento")
                .setPositiveButton("Virtual", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cambiarACrearEventosVirtual();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Presencial", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cambiarACrearEventosPresencial();
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}