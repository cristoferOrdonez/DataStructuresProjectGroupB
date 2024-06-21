package com.example.datastructureproject_groupb.fragments;

import android.icu.text.DecimalFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.MaxHeapAlfabeticoEventos;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.MaxHeapFechaEventos;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.MinHeapFechaEventos;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.MinHeapAlfabeticoEventos;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.adaptadores.AdaptadorPaginaDescubrir;
import com.example.datastructureproject_groupb.entidades.evento.Evento;
import com.example.datastructureproject_groupb.pickers.MostrarDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.Locale;

public class DescubrirFragment extends Fragment {

    TextInputEditText nombreEvento, fechaEvento, costoMinimoEvento, costoMaximoEvento;
    MaterialAutoCompleteTextView spinnerLocalidadEvento, spinnerCategoriaEvento;
    private Integer[] fecha = {0, -1, 0};
    Button botonAceptarFiltros, botonIniciarFiltros;
    private ArrayAdapter<String> categoriasAdapter, costoAdapter, localidadesAdapter;
    private LinearLayout layoutBoton, layoutCostoEvento;
    private TextInputLayout layoutNombreEvento, layoutFechaEvento, layoutLocalidadEvento, layoutTipoEvento;
    RecyclerView listaEventos;

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

        View root;

        if (Bocu.filtrosEventos.length == 0) {
            root = establecerContenidoFiltros(inflater, container);
        } else {
            root = establecerContenidoEventosFiltrados(inflater, container);
        }

        return root;

    }

    public View establecerContenidoFiltros(LayoutInflater inflater, ViewGroup container) {
        View root = inflater.inflate(R.layout.fragment_filtros_descubrir, container, false);

        layoutNombreEvento = root.findViewById(R.id.layoutNombreEvento);
        layoutFechaEvento = root.findViewById(R.id.layoutFechaEvento);
        layoutLocalidadEvento = root.findViewById(R.id.layoutLocalidadEvento);
        layoutCostoEvento = root.findViewById(R.id.layoutCostoEvento);
        layoutTipoEvento = root.findViewById(R.id.layoutTipoEvento);
        layoutBoton = root.findViewById(R.id.layoutBotones);

        nombreEvento = root.findViewById(R.id.editTextNombreEvento);
        fechaEvento = root.findViewById(R.id.editTextFechaEvento);
        costoMinimoEvento = root.findViewById(R.id.editTextCostoMinimo);
        costoMaximoEvento = root.findViewById(R.id.editTextCostoMaximo);

        spinnerCategoriaEvento = root.findViewById(R.id.spinnerCategoriaEvento);
        spinnerLocalidadEvento = root.findViewById(R.id.spinnerLocalidadEvento);

        botonAceptarFiltros = root.findViewById(R.id.botonAplicarFiltros);

        fechaEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutFechaEvento.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        spinnerLocalidadEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutLocalidadEvento.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spinnerCategoriaEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutTipoEvento.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fechaEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarDatePicker();
        });
        fechaEvento.setOnClickListener(view -> mostrarDatePicker());

        localidadesAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item_dropdown_menu, Bocu.LOCALIDADES);
        spinnerLocalidadEvento.setAdapter(localidadesAdapter);

        categoriasAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item_dropdown_menu, Bocu.INTERESES);
        spinnerCategoriaEvento.setAdapter(categoriasAdapter);

        formatoCostoDinero(costoMinimoEvento);
        formatoCostoDinero(costoMaximoEvento);

        botonAceptarFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean flag = true;

                if (nombreEvento.getText().toString().trim().equals("") &&
                        fechaEvento.getText().toString().trim().equals("") &&
                        spinnerLocalidadEvento.getText().toString().equals("") &&
                        costoMinimoEvento.getText().toString().equals("") &&
                        costoMaximoEvento.getText().toString().equals("") &&
                        spinnerCategoriaEvento.getText().toString().equals("")) {
                    layoutNombreEvento.setError("Debe ingresar mínimo un filtro");
                    flag = false;
                }

                if (flag) {
                    String nombre = nombreEvento.getText().toString();
                    String fecha = fechaEvento.getText().toString();
                    String localidad = spinnerLocalidadEvento.getText().toString();
                    String costoMinimo = costoMinimoEvento.getText().toString();
                    String costoMaximo = costoMaximoEvento.getText().toString();
                    String spinner = spinnerCategoriaEvento.getText().toString();
                    Bocu.filtrosEventos = new String[]{nombre, fecha, localidad, costoMinimo, costoMaximo, spinner};
                    openFragment();
                }
            }
        });
        return root;
    }

    public View establecerContenidoEventosFiltrados(LayoutInflater inflater, ViewGroup container) {
        View root = inflater.inflate(R.layout.fragment_eventos_filtrados_pagina_descubrir, container, false);

        DynamicUnsortedList<Evento> eventosFltrados = aplicarFiltros(Bocu.filtrosEventos);


        listaEventos = root.findViewById(R.id.recyclerViewEventosFiltradosPaginaDescubrir);

        listaEventos.setLayoutManager(new LinearLayoutManager(getContext()));
        AdaptadorPaginaDescubrir adapter = new AdaptadorPaginaDescubrir(eventosFltrados);
        listaEventos.setAdapter(adapter);

        botonIniciarFiltros = root.findViewById(R.id.botonFiltrarEventos);

        botonIniciarFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bocu.filtrosEventos = new String[]{};
                openFragment();
            }
        });
        return root;
    }

    private void mostrarDatePicker() {
        MostrarDatePicker datePicker = new MostrarDatePicker(getContext(), this.fechaEvento, this.fecha);
    }

    public static DynamicUnsortedList<Evento> OrdenarEventosA_Z(DynamicUnsortedList<Evento> entradaFiltrada) {
        int size = entradaFiltrada.size();
        MinHeapAlfabeticoEventos minHeap = new MinHeapAlfabeticoEventos(size);


        for (int i = 0; i < size; i++) {
            minHeap.insert(entradaFiltrada.get(i));
        }

        DynamicUnsortedList<Evento> eventosOrdenadosA_Z = new DynamicUnsortedList<Evento>();

        for (int i = 0; i < size; i++) {
            eventosOrdenadosA_Z.insert(minHeap.extractMin());
        }

        return eventosOrdenadosA_Z;

    }

    public static DynamicUnsortedList<Evento> OrdenarEventosZ_A(DynamicUnsortedList<Evento> entradaFiltrada) {
        int size = entradaFiltrada.size();
        MaxHeapAlfabeticoEventos maxHeap = new MaxHeapAlfabeticoEventos(size);

        for (int i = 0; i < size; i++) {
            maxHeap.insert(entradaFiltrada.get(i));
        }

        DynamicUnsortedList<Evento> eventosOrdenadosZ_A = new DynamicUnsortedList<Evento>();

        for (int i = 0; i < size; i++) {
            eventosOrdenadosZ_A.insert(maxHeap.extractMax());
        }
        return eventosOrdenadosZ_A;


    }

    private void openFragment(){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameContenedor, DescubrirFragment.newInstance());
        fragmentTransaction.commit();
    }

    private void formatoCostoDinero (TextInputEditText textInputCosto){
        textInputCosto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No es necesario
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No es necesario
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();

                String digitsOnly = input.replaceAll("[^\\d]", "");
                String formattedCost = formatearCosto(digitsOnly);

                if (!textInputCosto.getText().toString().equals(formattedCost)) {
                    textInputCosto.removeTextChangedListener(this);

                    textInputCosto.setText(formattedCost);
                    textInputCosto.setSelection(formattedCost.length());

                    textInputCosto.addTextChangedListener(this);
                }
            }

            private String formatearCosto(String input) {
                try {
                    if (input.isEmpty()) {
                        return "";
                    }
                    double costo = Double.parseDouble(input);

                    // Formatear el número como una moneda sin el símbolo de moneda
                    NumberFormat formatoMoneda = NumberFormat.getNumberInstance(new Locale("es", "CO"));
                    formatoMoneda.setMinimumFractionDigits(0); // Elima decimales

                    return formatoMoneda.format(costo);
                } catch (NumberFormatException e) {
                    return "";
                }
            }
        });
    }

    public static DynamicUnsortedList<Evento> OrdenarFechaReciente(DynamicUnsortedList<Evento> eventosBocu) {

        int size = eventosBocu.size();
        MaxHeapFechaEventos maxHeap = new MaxHeapFechaEventos(size);

        for (int i = 0; i < size; i++) {
            maxHeap.insert(eventosBocu.get(i));
        }

        DynamicUnsortedList<Evento> eventosOrdenadosFechaReciente = new DynamicUnsortedList<Evento>();

        for (int i = 0; i < size; i++) {
            eventosOrdenadosFechaReciente.insert(maxHeap.extractMax());
        }
        return eventosOrdenadosFechaReciente;
    }

    public static DynamicUnsortedList<Evento> OrdenarFechaAntigua(DynamicUnsortedList<Evento> eventosBocu) {
        int size = eventosBocu.size();
        MinHeapFechaEventos maxHeap = new MinHeapFechaEventos(size);

        for (int i = 0; i < size; i++) {
            maxHeap.insert(eventosBocu.get(i));
        }

        DynamicUnsortedList<Evento> eventosOrdenadosFechaAntigua = new DynamicUnsortedList<Evento>();

        for (int i = 0; i < size; i++) {
            eventosOrdenadosFechaAntigua.insert(maxHeap.extractMin());
        }
        return eventosOrdenadosFechaAntigua;
    }

    public DynamicUnsortedList<Evento> aplicarFiltros(String[] filtros) {
        DynamicUnsortedList<Evento> eventos = Bocu.eventos;
        DynamicUnsortedList<Evento> eventosFiltradosPorNombre = new DynamicUnsortedList<Evento>();

        return eventosFiltradosPorNombre;
    }

    private DynamicUnsortedList<Evento> filtrarEventoPorNombre(String nombre, DynamicUnsortedList<Evento> eventos) {
        DynamicUnsortedList<Evento> eventosFiltrados = new DynamicUnsortedList<Evento>();

        if (!nombre.equals("")) {
            for (int i = 0; i < eventos.size(); i++){
                if (eventos.get(i).getNombreEvento().toLowerCase().contains(nombre.toLowerCase())) {
                    eventosFiltrados.insert(eventos.get(i));
                }
            }
            return eventosFiltrados;
        } else {
            return eventos;
        }
    }

}