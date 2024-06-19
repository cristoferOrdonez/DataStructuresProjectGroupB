package com.example.datastructureproject_groupb.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.MaxHeapAlfabeticoEventos;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.MaxHeapFechaEventos;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.MinHeapFechaEventos;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.MinHeapAlfabeticoEventos;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.entidades.Evento;

public class DescubrirFragment extends Fragment {

    public DescubrirFragment() {
    }

    public static DescubrirFragment newInstance() {
        DescubrirFragment fragment = new DescubrirFragment();
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
        return inflater.inflate(R.layout.fragment_descubrir, container, false);
    }

    public static DynamicUnsortedList<Evento> OrdenarEventosA_Z(DynamicUnsortedList<Evento> entradaFiltrada){
        int size=entradaFiltrada.size();
        MinHeapAlfabeticoEventos minHeap = new MinHeapAlfabeticoEventos(size);


        for(int i=0;i<size;i++){
            minHeap.insert(entradaFiltrada.get(i));
        }

        DynamicUnsortedList<Evento> eventosOrdenadosA_Z = new DynamicUnsortedList<Evento>();

        for(int i=0;i<size;i++){
            eventosOrdenadosA_Z.insert(minHeap.extractMin());
        }

        return eventosOrdenadosA_Z;

    }
    public static DynamicUnsortedList<Evento> OrdenarEventosZ_A(DynamicUnsortedList<Evento> entradaFiltrada){
        int size=entradaFiltrada.size();
        MaxHeapAlfabeticoEventos maxHeap = new MaxHeapAlfabeticoEventos(size);

        for(int i=0;i<size;i++){
            maxHeap.insert(entradaFiltrada.get(i));
        }

        DynamicUnsortedList<Evento> eventosOrdenadosZ_A = new DynamicUnsortedList<Evento>();

        for(int i=0;i<size;i++){
            eventosOrdenadosZ_A.insert(maxHeap.extractMax());
        }
        return eventosOrdenadosZ_A;


    }

    public static  DynamicUnsortedList<Evento> OrdenarFechaReciente(DynamicUnsortedList<Evento> eventosBocu){

        int size=eventosBocu.size();
        MaxHeapFechaEventos maxHeap = new MaxHeapFechaEventos(size);

        for(int i=0;i<size;i++){
            maxHeap.insert(eventosBocu.get(i));
        }

        DynamicUnsortedList<Evento> eventosOrdenadosFechaReciente = new DynamicUnsortedList<Evento>();

        for(int i=0;i<size;i++){
            eventosOrdenadosFechaReciente.insert(maxHeap.extractMax());
        }
        return eventosOrdenadosFechaReciente;
    }

    public static  DynamicUnsortedList<Evento> OrdenarFechaAntigua(DynamicUnsortedList<Evento> eventosBocu){
        int size=eventosBocu.size();
        MinHeapFechaEventos maxHeap = new MinHeapFechaEventos(size);

        for(int i=0;i<size;i++){
            maxHeap.insert(eventosBocu.get(i));
        }

        DynamicUnsortedList<Evento> eventosOrdenadosFechaAntigua = new DynamicUnsortedList<Evento>();

        for(int i=0;i<size;i++){
            eventosOrdenadosFechaAntigua.insert(maxHeap.extractMin());
        }
        return eventosOrdenadosFechaAntigua;
    }

}