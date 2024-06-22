package com.example.datastructureproject_groupb.entidades.evento;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.entidades.pagina_inicio.PaginaInicio;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.pickers.MostrarDatePicker;
import com.example.datastructureproject_groupb.pickers.MostrarTimePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class CrearEventosVirtual extends AppCompatActivity{
    TextInputEditText nombreEvento, fechaEvento, costoEvento, horaInicioEvento, horaFinalEvento, descripcionEvento;
    MaterialAutoCompleteTextView spinnerCategoriaEvento, spinnerPlataformaEvento;
    Button cancelarCrearEvento, aceptarCrearEvento;
    private ArrayAdapter<String> categoriasAdapter, plataformasAdapter;
    private Integer[] horaMinutosInicio = {-1, -1}, horaMinutosFinal = {-1, -1}, fecha = {0, -1, 0};
    private LinearLayout layoutBotones;
    private TextInputLayout layoutNombreEvento, layoutFechaEvento, layoutPlataformaEvento, layoutCostoEvento, layoutHoraInicio, layoutHoraFinal, layoutTipoEvento, layoutDescripcionEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_eventos_virtual);

        layoutBotones = findViewById(R.id.layoutBotones);

        KeyboardVisibilityEvent.setEventListener(this, isOpen -> {
            if (isOpen) {

                LinearLayout.LayoutParams nuevoParametro = (LinearLayout.LayoutParams) layoutBotones.getLayoutParams();
                nuevoParametro.width = 0;
                nuevoParametro.weight = 0;

                layoutBotones.setLayoutParams(nuevoParametro);

            } else {

                LinearLayout.LayoutParams nuevoParametro = (LinearLayout.LayoutParams) layoutBotones.getLayoutParams();

                nuevoParametro.width = ViewGroup.LayoutParams.MATCH_PARENT;
                nuevoParametro.weight = 1.9f;

                layoutBotones.setLayoutParams(nuevoParametro);

            }
        });

        nombreEvento = findViewById(R.id.editTextNombreEvento);
        fechaEvento = findViewById(R.id.editTextFechaEvento);
        costoEvento = findViewById(R.id.editTextCostoEvento);
        horaInicioEvento = findViewById(R.id.editTextHoraInicioEvento);
        horaFinalEvento = findViewById(R.id.editTextHoraFinalEvento);
        descripcionEvento = findViewById(R.id.editTextDescripcionEvento);
        spinnerPlataformaEvento = findViewById(R.id.spinnerPlataformaEvento);
        spinnerCategoriaEvento = findViewById(R.id.spinnerCategoriaEvento);

        cancelarCrearEvento = findViewById(R.id.botonCancelarCrearEvento);
        aceptarCrearEvento = findViewById(R.id.botonAceptarCrearEvento);

        layoutNombreEvento = findViewById(R.id.layoutNombreEvento);
        layoutFechaEvento = findViewById(R.id.layoutFechaEvento);
        layoutPlataformaEvento = findViewById(R.id.layoutPlataformaEvento);
        layoutCostoEvento = findViewById(R.id.layoutCostoEvento);
        layoutHoraInicio = findViewById(R.id.layoutHoraInicio);
        layoutHoraFinal = findViewById(R.id.layoutHoraFinal);
        layoutTipoEvento = findViewById(R.id.layoutTipoEvento);
        layoutDescripcionEvento = findViewById(R.id.layoutDescripcionEvento);

        // Deshabilita el setErrorEnable después de un intento de filtrado fallido
        deshabilitarSetError(nombreEvento, layoutNombreEvento);
        deshabilitarSetError(fechaEvento, layoutFechaEvento);
        deshabilitarSetError(spinnerPlataformaEvento, layoutPlataformaEvento);
        deshabilitarSetError(horaInicioEvento, layoutHoraInicio);
        deshabilitarSetError(horaFinalEvento, layoutHoraFinal);
        deshabilitarSetError(spinnerCategoriaEvento, layoutTipoEvento);
        deshabilitarSetError(descripcionEvento, layoutDescripcionEvento);

        formatoCostoDinero(costoEvento);

        categoriasAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.INTERESES);
        spinnerCategoriaEvento.setAdapter(categoriasAdapter);

        plataformasAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.PLATAFORMAS);
        spinnerPlataformaEvento.setAdapter(plataformasAdapter);

        cancelarCrearEvento.setOnClickListener(view -> cambiarAEventos());
        aceptarCrearEvento.setOnClickListener(view -> crearEventoExpositor());

        fechaEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarDatePicker();
        });
        fechaEvento.setOnClickListener(view -> mostrarDatePicker());
        horaInicioEvento.setOnClickListener(view -> mostrarTimePicker(horaInicioEvento, horaMinutosInicio));
        horaInicioEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarTimePicker(horaInicioEvento, horaMinutosInicio);
        });

        horaFinalEvento.setOnClickListener(view -> mostrarTimePicker(horaFinalEvento, horaMinutosFinal));
        horaFinalEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarTimePicker(horaFinalEvento, horaMinutosFinal);
        });
    }

    private void mostrarTimePicker(EditText horarioEvento, Integer[] horaMinutosDefecto) {
        MostrarTimePicker timePicker = new MostrarTimePicker(
                this,
                horarioEvento,
                horaMinutosDefecto);
    }

    private void mostrarDatePicker(){
        MostrarDatePicker datePicker = new MostrarDatePicker(this, this.fechaEvento, this.fecha);
    }

    public void cambiarAEventos() {
        PaginaInicio.intensionInicializacion = PaginaInicio.EVENTOS;
        Intent miIntent = new Intent(this, PaginaInicio.class);
        startActivity(miIntent);
        finishAffinity();
    }

    public void crearEventoExpositor(){
        VerificarInformacionRegistro(new View(this));
    }

    public void VerificarInformacionRegistro(View view) {

        boolean flag = true;

        if(nombreEvento.getText().toString().trim().equals("")) {
            layoutNombreEvento.setError("Ingrese un nombre valido");
            flag = false;
        }

        String verificarFechaEvento = this.fechaEvento.getText().toString().trim();

        String[] verificarTamanoFechaEvento = verificarFechaEvento.split("/");
        if(verificarFechaEvento.equals("") || verificarTamanoFechaEvento.length  != 3) {
            layoutFechaEvento.setError("Ingrese una fecha valida");
            flag = false;
        }
        String verificarCostoEvento = costoEvento.getText().toString().trim();
        if(verificarCostoEvento.equals("")) {
            layoutCostoEvento.setError("Ingrese un costo valido");
            flag = false;
        }
        if(horaInicioEvento.getText().toString().trim().equals("")) {
            layoutHoraInicio.setError("Seleccione una hora de inicio");
            flag = false;
        }
        if(horaFinalEvento.getText().toString().trim().equals("")) {
            layoutHoraFinal.setError("Seleccione una hora de fin");
            flag = false;
        }
        String horarioInicio = this.horaInicioEvento.getText().toString().trim();
        String horarioFinal = this.horaFinalEvento.getText().toString().trim();
        if(horarioInicio.replaceAll("[^a-zA-Z]", "").equals("pm") && horarioFinal.replaceAll("[^a-zA-Z]", "").equals("am")){
            layoutHoraInicio.setError("La hora inicial deber ser menor que la hora final");
            flag = false;
        } else if ((horarioInicio.replaceAll("[^a-zA-Z]", "").equals("am") && horarioFinal.replaceAll("[^a-zA-Z]", "").equals("am")) || (horarioInicio.replaceAll("[^a-zA-Z]", "").equals("pm") && horarioFinal.replaceAll("[^a-zA-Z]", "").equals("pm"))) {
            String[] horaMinutoInicio = horarioInicio.split(":");
            String[] horaMinutoFinal = horarioFinal.split(":");
            if (horaMinutoInicio[0].equals(horaMinutoFinal[0]) && Integer.parseInt(horaMinutoInicio[1].replaceAll("[^\\d]", "")) >= Integer.parseInt(horaMinutoFinal[1].replaceAll("[^\\d]", ""))){
                layoutHoraInicio.setError("La hora inicial deber ser menor que la hora final");
                flag = false;
            }
        }
        if(spinnerCategoriaEvento.getText().toString().equals("") || categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString()) == -1) {
            layoutTipoEvento.setError("Seleccione un tipo de evento");
            flag = false;
        }
        if(spinnerPlataformaEvento.getText().toString().equals("") || plataformasAdapter.getPosition(spinnerPlataformaEvento.getText().toString()) == -1) {
            layoutPlataformaEvento.setError("Seleccione una plataforma");
            flag = false;
        }
        if(descripcionEvento.getText().toString().trim().equals("")) {
            layoutDescripcionEvento.setError("Ingrese una descripción valida");
            flag = false;
        }

        if(flag)
            CrearEvento(view);

    }

    public void CrearEvento(View view) {
        String nombreEvento = this.nombreEvento.getText().toString().trim();
        String fechaEvento = this.fechaEvento.getText().toString().trim();

        String[] partesFecha = fechaEvento.split("/");
        int diaEvento = Integer.parseInt(partesFecha[0]);
        int mesEvento = Integer.parseInt(partesFecha[1]);
        int AnioEvento = Integer.parseInt(partesFecha[2]);

        String plataformaEvento = spinnerPlataformaEvento.getText().toString();
        int costoEvento = Integer.parseInt(this.costoEvento.getText().toString().replaceAll("[^\\d,]", ""));
        String horarioEvento = this.horaInicioEvento.getText().toString() + " - " + this.horaFinalEvento.getText().toString();
        String descripcionEvento = this.descripcionEvento.getText().toString();
        int localidad = 21;
        int categoria = categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString());

        long newId = new DbEventos(this).insertarEvento(nombreEvento,
                AnioEvento,
                mesEvento,
                diaEvento,
                plataformaEvento,
                costoEvento,
                horarioEvento,
                descripcionEvento,
                localidad,
                categoria,
                Bocu.usuario.getCorreoElectronico());

        Evento evento = new Evento(
                this,
                newId,
                nombreEvento,
                new Date(AnioEvento, mesEvento, diaEvento),
                plataformaEvento,
                localidad,
                costoEvento,
                horarioEvento,
                categoria,
                descripcionEvento,
                Bocu.usuario.getCorreoElectronico()
        );

        try {
            Bocu.eventosExpositor.insert(evento);
            Bocu.posicionesEventosExpositor.insert(Bocu.eventos.size());
            Bocu.eventos.insert(evento);

            Toast.makeText(this, "Evento creado con éxito", Toast.LENGTH_SHORT).show();
            cambiarAEventos();

        } catch(Exception e){
            Toast.makeText(this, "Error al crear el evento", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatoCostoDinero (TextInputEditText textInputCosto){
        textInputCosto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                layoutCostoEvento.setErrorEnabled(false);

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

    private void deshabilitarSetError (TextInputEditText textInputEditText, TextInputLayout textInputLayout){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                textInputLayout.setErrorEnabled(false);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No es necesario
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No es necesario
            }
        });
    }

    private void deshabilitarSetError (MaterialAutoCompleteTextView materialAutoCompleteTextView, TextInputLayout textInputLayout){
        materialAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                textInputLayout.setErrorEnabled(false);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No es necesario
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No es necesario
            }
        });
    }
}
