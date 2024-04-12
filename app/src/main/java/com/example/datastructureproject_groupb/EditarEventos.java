package com.example.datastructureproject_groupb;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.entidades.Evento;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class EditarEventos extends AppCompatActivity {
    TextInputEditText nombreEvento, fechaEvento, ubicacionEvento, costoEvento, horarioEvento, descripcionEvento;
    MaterialAutoCompleteTextView spinnerLocalidadEvento, spinnerCategoriaEvento;
    Button cancelarEditarEvento, aceptarEditarEvento;
    private ArrayAdapter<String> localidadesAdapter, categoriasAdapter;
    long idEvento;

    private int dia, mes, ano, horaInicio, horaFinal, minutosInicio, minutosFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_eventos);

        nombreEvento = findViewById(R.id.editTextNombreEvento);
        nombreEvento.setText(getIntent().getStringExtra("NOMBRE_EVENTO"));
        fechaEvento = findViewById(R.id.editTextFechaEvento);

        String fechaEventoS = getIntent().getStringExtra("FECHA_EVENTO");

        String[] fechaEventoArr = fechaEventoS.split("/");

        dia = Integer.parseInt(fechaEventoArr[0]);
        mes = Integer.parseInt(fechaEventoArr[1]) - 1;
        ano = Integer.parseInt(fechaEventoArr[2]);

        String horarioEventoS = getIntent().getStringExtra("HORARIO_EVENTO");

        String[] horarioEventoArr = horarioEventoS.split(" - ");

        String horaInicioS = horarioEventoArr[0], horaFinalS = horarioEventoArr[1];

        if(horaInicioS.charAt(horaInicioS.length() - 4) == 'p')
            horaInicio = Integer.parseInt(horaInicioS.substring(0, horaInicioS.indexOf(':'))) + 12;
        else
            horaInicio = Integer.parseInt(horaInicioS.substring(0, horaInicioS.indexOf(':')));

        if(horaFinalS.charAt(horaFinalS.length() - 4) == 'p')
            horaFinal = Integer.parseInt(horaFinalS.substring(0, horaFinalS.indexOf(':'))) + 12;
        else
            horaFinal = Integer.parseInt(horaFinalS.substring(0, horaFinalS.indexOf(':')));

        minutosInicio = Integer.parseInt(horaInicioS.substring(horaInicioS.indexOf(':') + 1, horaInicioS.indexOf('.') - 1));

        minutosFinal = Integer.parseInt(horaFinalS.substring(horaFinalS.indexOf(':') + 1, horaFinalS.indexOf('.') - 1));

        fechaEvento.setText(getIntent().getStringExtra("FECHA_EVENTO"));
        ubicacionEvento = findViewById(R.id.editTextUbicacionEvento);
        ubicacionEvento.setText(getIntent().getStringExtra("UBICACION_EVENTO"));
        costoEvento = findViewById(R.id.editTextCostoEvento);
        costoEvento.setText(getIntent().getStringExtra("COSTO_EVENTO"));
        horarioEvento = findViewById(R.id.editTextHorarioEvento);
        horarioEvento.setText(horarioEventoS);
        descripcionEvento = findViewById(R.id.editTextDescripcionEvento);
        descripcionEvento.setText(getIntent().getStringExtra("DESCRIPCION_EVENTO"));
        spinnerLocalidadEvento = findViewById(R.id.spinnerLocalidadEvento);
        spinnerCategoriaEvento = findViewById(R.id.spinnerCategoriaEvento);

        cancelarEditarEvento = findViewById(R.id.botonCancelarEditarEvento);
        aceptarEditarEvento = findViewById(R.id.botonAceptarEditarEvento);

        localidadesAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.LOCALIDADES);
        spinnerLocalidadEvento.setAdapter(localidadesAdapter);

        categoriasAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.INTERESES);
        spinnerCategoriaEvento.setAdapter(categoriasAdapter);

        spinnerLocalidadEvento.setText(Bocu.LOCALIDADES[getIntent().getIntExtra("LOCALIDAD_EVENTO", -1)], false);


        spinnerCategoriaEvento.setText(Bocu.INTERESES[getIntent().getIntExtra("CATEGORIA_EVENTO", -1)], false);


        cancelarEditarEvento.setOnClickListener(view -> cambiarAEventos());
        aceptarEditarEvento.setOnClickListener(view -> editarEventoExpositor());

        fechaEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarDatePicker();
        });
        fechaEvento.setOnClickListener(view -> mostrarDatePicker());

        horarioEvento.setOnClickListener(view -> mostrarTimePicker());
        horarioEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus)
                mostrarTimePicker();
        });

    }

    private void mostrarTimePicker(){

        int horaInicio, horaFinal, minutosInicio, minutosFinal;

        if(horarioEvento.getText().toString().equals("")){

            Calendar calendario = Calendar.getInstance();

            horaInicio = calendario.get(Calendar.HOUR_OF_DAY);
            horaFinal = calendario.get(Calendar.HOUR_OF_DAY);
            minutosFinal = calendario.get(Calendar.MINUTE);
            minutosInicio = calendario.get(Calendar.MINUTE);

        } else {

            horaInicio = this.horaInicio;
            horaFinal = this.horaFinal;
            minutosFinal = this.minutosFinal;
            minutosInicio = this.minutosInicio;

        }

        AtomicReference<String> horario = new AtomicReference<>("");

        TimePickerDialog pickerInicio = new TimePickerDialog(this, (x, y, z) -> {

            this.horaInicio = y;

            this.minutosInicio = z;

            String amOpm = (y > 12)?"p.m.":"a.m.";

            if(z > 9)
                horario.set(y%12 + ":" + z + amOpm);
            else
                horario.set(y%12 + ":0" + z + amOpm);

            TimePickerDialog pickerFinal = new TimePickerDialog(this, (x_alt, y_alt, z_alt) -> {

                this.horaFinal = y_alt;
                this.minutosFinal = z_alt;

                String amOpm_alt = (y_alt > 12)?"p.m.":"a.m.";

                if(z_alt > 9)
                    horarioEvento.setText(horario.get() + " - " + (y_alt%12) + ":" + z_alt + amOpm_alt);
                else
                    horarioEvento.setText(horario.get() + " - " + (y_alt%12) + ":0" + z_alt + amOpm_alt);

            }, horaFinal, minutosFinal, false);
            pickerFinal.show();

        }, horaInicio, minutosInicio, false);
        pickerInicio.show();

    }

    private void mostrarDatePicker(){

        int dia, mes, ano;

        if(fechaEvento.getText().toString().equals("")){
            Calendar calendario = Calendar.getInstance();
            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH);
            ano = calendario.get(Calendar.YEAR);
        } else {

            dia = this.dia;
            mes = this.mes;
            ano = this.ano;

        }

        DatePickerDialog picker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {

            this.dia = dayOfMonth;
            this.mes = month;
            this.ano = year;

            fechaEvento.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

        }, ano, mes, dia);
        picker.show();

    }

    public void cambiarAEventos() {
        PaginaInicio.intensionInicializacion = PaginaInicio.EVENTOS;
        Intent miIntent = new Intent(this, PaginaInicio.class);
        startActivity(miIntent);
        finishAffinity();
    }

    public void editarEventoExpositor(){
        VerificarInformacionRegistro(new View(this));
    }

    public void mostrarLocalidades() {
        spinnerLocalidadEvento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocalidad = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void mostrarCategorias() {
        spinnerCategoriaEvento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedInteres = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void VerificarInformacionRegistro(View view) {

        boolean flag = true;
        String mensajeError = "";

        if (nombreEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado nombre valido\n";
            flag = false;
        }

        String verificarFechaEvento = this.fechaEvento.getText().toString().trim();

        String[] verificarTamanoFechaEvento = verificarFechaEvento.split("/");
        if (verificarFechaEvento.equals("") || verificarTamanoFechaEvento.length != 3) {
            mensajeError += "No ha ingresado fecha valida\n";
            flag = false;
        }
        if (ubicacionEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado una ubicación valida\n";
            flag = false;
        }
        if(spinnerLocalidadEvento.getText().toString().equals("") || localidadesAdapter.getPosition(spinnerLocalidadEvento.getText().toString()) == -1) {
            mensajeError += "Seleccione una Localidad\n";
            flag = false;
        }
        String verificarCostoEvento = costoEvento.getText().toString().trim();
        if (verificarCostoEvento.equals("") || !TextUtils.isDigitsOnly(verificarCostoEvento)) {
            mensajeError += "No ha ingresado un costo valido\n";
            flag = false;
        }
        if (horarioEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado un horario valido\n";
            flag = false;
        }
        if(spinnerCategoriaEvento.getText().toString().equals("") || categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString()) == -1) {
            mensajeError += "Seleccione un Interes\n";
            flag = false;
        }
        if (descripcionEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado una descripción valida\n";
            flag = false;
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if(flag)
            EditarEvento(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();
    }
    public void EditarEvento(View view) {
        String nombreEvento = this.nombreEvento.getText().toString().trim();
        String fechaEvento = this.fechaEvento.getText().toString().trim();

        String[] partesFecha = fechaEvento.split("/");
        int diaEvento = Integer.parseInt(partesFecha[0]);
        int mesEvento = Integer.parseInt(partesFecha[1]);
        int AnoEvento = Integer.parseInt(partesFecha[2]);

        String ubicacionEvento = this.ubicacionEvento.getText().toString();
        int costoEvento = Integer.parseInt(this.costoEvento.getText().toString());
        String horarioEvento = this.horarioEvento.getText().toString();
        String descripcionEvento = this.descripcionEvento.getText().toString();
        int localidad = localidadesAdapter.getPosition(spinnerLocalidadEvento.getText().toString());
        int categoria = categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString());

        int position = getIntent().getIntExtra("POSITION", -1);
        idEvento = getIntent().getLongExtra("ID_EVENTO",-1);

        try {

            Evento evento = new Evento(
                    idEvento,
                    nombreEvento,
                    new Date(AnoEvento, mesEvento, diaEvento),
                    ubicacionEvento,
                    localidad,
                    costoEvento,
                    horarioEvento,
                    categoria,
                    descripcionEvento,
                    Bocu.usuario.getCorreoElectronico()
            );

            Bocu.eventosExpositor.set(position, evento);
            Bocu.eventos.set(Bocu.posicionesEventosExpositor.get(position), evento);
            new DbEventos(this).editarEvento(nombreEvento,
                    AnoEvento,
                    mesEvento,
                    diaEvento,
                    ubicacionEvento,
                    costoEvento,
                    horarioEvento,
                    descripcionEvento,
                    localidad,
                    categoria,
                    String.valueOf(idEvento),
                    Bocu.usuario.getCorreoElectronico());

            Toast.makeText(this, "Evento creado con éxito", Toast.LENGTH_SHORT).show();
            cambiarAEventos();

        } catch(Exception e){
            Toast.makeText(this, "Error al crear el evento", Toast.LENGTH_SHORT).show();
        }

    }
}