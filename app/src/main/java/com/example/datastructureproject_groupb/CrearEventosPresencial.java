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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class CrearEventosPresencial extends AppCompatActivity{
    TextInputEditText nombreEvento, fechaEvento, ubicacionEvento, costoEvento, horarioEvento, descripcionEvento;
    MaterialAutoCompleteTextView spinnerLocalidadEvento, spinnerCategoriaEvento;
    Button cancelarCrearEvento, aceptarCrearEvento;
    private ArrayAdapter<String> categoriasAdapter, localidadesAdapter;
    private int dia = 0, mes = -1, ano = 0, horaInicio = -1, horaFinal = -1, minutosInicio = -1, minutosFinal = -1;
    private GoogleMap gMap;
    private ImageButton botonAceptarUbicacion, botonCancelarUbicación;
    private ConstraintLayout layoutMap;

    private LatLng bogota, ubicacionMarker, ubicacionDefinitiva;
    Marker marker;
    private LinearLayout layoutBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_eventos_presencial);

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

        marker = null;
        ubicacionMarker = null;
        ubicacionDefinitiva = null;
        bogota = new LatLng(4.709870584581264, -74.07212528110854);
        layoutMap = findViewById(R.id.linearLayoutMap);
        botonAceptarUbicacion = findViewById(R.id.imageButtonAceptarUbicacion);
        botonCancelarUbicación = findViewById(R.id.imageButtonCancelarUbicacion);

        nombreEvento = findViewById(R.id.editTextNombreEvento);
        fechaEvento = findViewById(R.id.editTextFechaEvento);
        ubicacionEvento = findViewById(R.id.editTextUbicacionEvento);
        costoEvento = findViewById(R.id.editTextCostoEvento);
        horarioEvento = findViewById(R.id.editTextHorarioEvento);
        descripcionEvento = findViewById(R.id.editTextDescripcionEvento);
        spinnerLocalidadEvento = findViewById(R.id.spinnerLocalidadEvento);
        spinnerCategoriaEvento = findViewById(R.id.spinnerCategoriaEvento);

        cancelarCrearEvento = findViewById(R.id.botonCancelarCrearEvento);
        aceptarCrearEvento = findViewById(R.id.botonAceptarCrearEvento);

        ubicacionEvento.setOnClickListener(view -> mostrarMapa());
        ubicacionEvento.setOnFocusChangeListener((view, hasFocus) -> {

            if (hasFocus)
                mostrarMapa();

        });

        localidadesAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.LOCALIDADES);
        spinnerLocalidadEvento.setAdapter(localidadesAdapter);

        categoriasAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.INTERESES);
        spinnerCategoriaEvento.setAdapter(categoriasAdapter);

        cancelarCrearEvento.setOnClickListener(view -> cambiarAEventos());
        aceptarCrearEvento.setOnClickListener(view -> crearEventoExpositor());
        fechaEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarDatePicker();
        });
        fechaEvento.setOnClickListener(view -> mostrarDatePicker());
        horarioEvento.setOnClickListener(view -> mostrarTimePicker());
        horarioEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarTimePicker();
        });

        botonAceptarUbicacion.setOnClickListener(view -> {

            ubicacionDefinitiva = ubicacionMarker;

            if(ubicacionDefinitiva != null) {

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                List<Address> listaDireccion = null;
                try {
                    listaDireccion = geocoder.getFromLocation(ubicacionDefinitiva.latitude, ubicacionDefinitiva.longitude, 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String ubicacion = listaDireccion.get(0).getAddressLine(0).split(",")[0];

                ubicacionEvento.setText(ubicacion);
            }
            layoutMap.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            marker.remove();

        });

        botonCancelarUbicación.setOnClickListener(view -> {

            layoutMap.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            marker.remove();

        });

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaCrearEventos);

        supportMapFragment.getMapAsync(googleMap -> {

            gMap = googleMap;

            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 10.0f));
            gMap.getUiSettings().setZoomControlsEnabled(true);
            LatLngBounds bogotaBounds = new LatLngBounds(
                    new LatLng(4.4625, -74.2346),
                    new LatLng(4.8159, -73.9875)
            );

            gMap.setMinZoomPreference(10.0f);
            gMap.setLatLngBoundsForCameraTarget(bogotaBounds);
            gMap.setOnMapClickListener(latLng -> {

                ubicacionMarker = latLng;

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {

                    List<Address> listaDireccion = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String ubicacion = listaDireccion.get(0).getAddressLine(0).split(",")[0];

                    if(marker != null)
                        marker.remove();
                    marker = gMap.addMarker(new MarkerOptions().position(latLng).title(ubicacion));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });

        });

    }

    private void mostrarMapa(){

        layoutMap.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if(ubicacionDefinitiva != null) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            List<Address> listaDireccion = null;
            try {
                listaDireccion = geocoder.getFromLocation(ubicacionDefinitiva.latitude, ubicacionDefinitiva.longitude, 1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String ubicacion = listaDireccion.get(0).getAddressLine(0).split(",")[0];

            marker = gMap.addMarker(new MarkerOptions().position(ubicacionDefinitiva).title(ubicacion));

        }

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

    public void crearEventoExpositor(){
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
        if(ubicacionEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado una ubicación valida\n";
            flag = false;
        }
        if(spinnerLocalidadEvento.getText().toString().equals("") || localidadesAdapter.getPosition(spinnerLocalidadEvento.getText().toString()) == -1) {
            mensajeError += "Seleccione una Localidad\n";
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
        if(descripcionEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado una descripción valida\n";
            flag = false;
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        if(flag)
            CrearEvento(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();
    }
    public void CrearEvento(View view) {
        String nombreEvento = this.nombreEvento.getText().toString().trim();
        String fechaEvento = this.fechaEvento.getText().toString().trim();

        String[] partesFecha = fechaEvento.split("/");
        int diaEvento = Integer.parseInt(partesFecha[0]);
        int mesEvento = Integer.parseInt(partesFecha[1]);
        int AnoEvento = Integer.parseInt(partesFecha[2]);

        String ubicacionEvento = ubicacionDefinitiva.latitude + " - " + ubicacionDefinitiva.longitude;
        int costoEvento = Integer.parseInt(this.costoEvento.getText().toString());
        String horarioEvento = this.horarioEvento.getText().toString();
        String descripcionEvento = this.descripcionEvento.getText().toString();
        int localidad = localidadesAdapter.getPosition(spinnerLocalidadEvento.getText().toString());
        int categoria = categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString());

        long newId = new DbEventos(this).insertarEvento(nombreEvento,
                AnoEvento,
                mesEvento,
                diaEvento,
                ubicacionEvento,
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
                new Date(AnoEvento, mesEvento, diaEvento),
                ubicacionEvento,
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
}