package com.example.datastructureproject_groupb;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.entidades.Evento;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class EditarEventosVirtual extends AppCompatActivity {
    TextInputEditText nombreEvento, fechaEvento, costoEvento, horarioEvento, descripcionEvento;
    MaterialAutoCompleteTextView spinnerCategoriaEvento, spinnerPlataformaEvento;
    Button cancelarEditarEvento, aceptarEditarEvento;
    long idEvento;
    private ArrayAdapter<String> categoriasAdapter, plataformasAdapter;
    private int dia = 0, mes = -1, ano = 0, horaInicio = -1, horaFinal = -1, minutosInicio = -1, minutosFinal = -1;
    private LinearLayout layoutBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_eventos_virtual);

        layoutBotones = findViewById(R.id.layoutBotones);

        KeyboardVisibilityEvent.setEventListener(this, isOpen -> {
            if(isOpen)
                layoutBotones.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 0));
            else {

                LinearLayout.LayoutParams nuevoParametro = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
                nuevoParametro.leftMargin = 30;
                nuevoParametro.rightMargin = 30;
                nuevoParametro.bottomMargin = 40;
                nuevoParametro.topMargin = 40;

                layoutBotones.setLayoutParams(nuevoParametro);

            }
        });

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

        costoEvento = findViewById(R.id.editTextCostoEvento);
        costoEvento.setText(getIntent().getStringExtra("COSTO_EVENTO"));
        horarioEvento = findViewById(R.id.editTextHorarioEvento);
        horarioEvento.setText(horarioEventoS);
        descripcionEvento = findViewById(R.id.editTextDescripcionEvento);
        descripcionEvento.setText(getIntent().getStringExtra("DESCRIPCION_EVENTO"));
        spinnerPlataformaEvento = findViewById(R.id.spinnerPlataformaEvento);
        spinnerCategoriaEvento = findViewById(R.id.spinnerCategoriaEvento);

        cancelarEditarEvento = findViewById(R.id.botonCancelarEditarEvento);
        aceptarEditarEvento = findViewById(R.id.botonAceptarEditarEvento);

        plataformasAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.PLATAFORMAS);
        spinnerPlataformaEvento.setAdapter(plataformasAdapter);

        categoriasAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.INTERESES);
        spinnerCategoriaEvento.setAdapter(categoriasAdapter);

        spinnerPlataformaEvento.setText(Bocu.PLATAFORMAS[getIntent().getIntExtra("PLATAFORMA_EVENTO", -1)], false);


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

    public void VerificarInformacionRegistro(View view) {

        boolean flag = true;
        String mensajeError = "";

        if(nombreEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado nombre valido\n";
            flag = false;
        }

        String verificarFechaEvento = this.fechaEvento.getText().toString().trim();

        String[] verificarTamanoFechaEvento = verificarFechaEvento.split("/");
        if(verificarFechaEvento.equals("") || verificarTamanoFechaEvento.length  != 3) {
            mensajeError += "No ha ingresado fecha valida\n";
            flag = false;
        }
        String verificarCostoEvento = costoEvento.getText().toString().trim();
        if(verificarCostoEvento.equals("") || !TextUtils.isDigitsOnly(verificarCostoEvento)) {
            mensajeError += "No ha ingresado un costo valido\n";
            flag = false;
        }
        if(horarioEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado un horario valido\n";
            flag = false;
        }
        if(spinnerCategoriaEvento.getText().toString().equals("") || categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString()) == -1) {
            mensajeError += "Seleccione un Interes\n";
            flag = false;
        }
        if(spinnerPlataformaEvento.getText().toString().equals("") || plataformasAdapter.getPosition(spinnerPlataformaEvento.getText().toString()) == -1) {
            mensajeError += "Seleccione una plataforma\n";
            flag = false;
        }
        if(descripcionEvento.getText().toString().trim().equals("")) {
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

        String plataformaEvento = spinnerPlataformaEvento.getText().toString();
        int costoEvento = Integer.parseInt(this.costoEvento.getText().toString());
        String horarioEvento = this.horarioEvento.getText().toString();
        String descripcionEvento = this.descripcionEvento.getText().toString();
        int localidad = 21;
        int categoria = categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString());

        int position = getIntent().getIntExtra("POSITION", -1);
        idEvento = getIntent().getLongExtra("ID_EVENTO",-1);

        try {

            Evento evento = new Evento(
                    this,
                    idEvento,
                    nombreEvento,
                    new Date(AnoEvento, mesEvento, diaEvento),
                    plataformaEvento,
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
                    plataformaEvento,
                    costoEvento,
                    horarioEvento,
                    descripcionEvento,
                    localidad,
                    categoria,
                    String.valueOf(idEvento),
                    Bocu.usuario.getCorreoElectronico());

            Toast.makeText(this, "Evento editado con éxito", Toast.LENGTH_SHORT).show();
            cambiarAEventos();

        } catch(Exception e){
            Toast.makeText(this, "Error al editar el evento", Toast.LENGTH_SHORT).show();
        }

    }
}