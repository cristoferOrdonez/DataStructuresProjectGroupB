package com.example.datastructureproject_groupb;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.entidades.Evento;
import com.example.datastructureproject_groupb.pickers.MostrarDatePicker;
import com.example.datastructureproject_groupb.pickers.MostrarTimePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class CrearEventosPresencial extends AppCompatActivity {
    TextInputEditText nombreEvento, fechaEvento, direPlatEvento, costoEvento, horaInicioEvento, horaFinalEvento, descripcionEvento;
    MaterialAutoCompleteTextView spinnerLocalidadEvento, spinnerCategoriaEvento;
    Button cancelarCrearEvento, aceptarCrearEvento;
    private ArrayAdapter<String> categoriasAdapter, localidadesAdapter;
    private Integer[] horaMinutosInicio = {-1, -1}, horaMinutosFinal = {-1, -1}, fecha = {0, -1, 0};
    private GoogleMap gMap;
    private ImageButton botonAceptarUbicacion, botonCancelarUbicación;
    private ConstraintLayout layoutMap;
    private LatLng bogota, ubicacionMarker, ubicacionDefinitiva;
    Marker marker;
    private LinearLayout layoutBotones;
    private TextInputLayout layoutNombreEvento, layoutFechaEvento, layoutUbicacionEvento, layoutLocalidadEvento, layoutCostoEvento, layoutHoraInicio, layoutHoraFinal, layoutTipoEvento, layoutDescripcionEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_eventos_presencial);

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

        marker = null;
        ubicacionMarker = null;
        ubicacionDefinitiva = null;
        bogota = new LatLng(4.709870584581264, -74.07212528110854);
        layoutMap = findViewById(R.id.linearLayoutMap);
        botonAceptarUbicacion = findViewById(R.id.imageButtonAceptarUbicacion);
        botonCancelarUbicación = findViewById(R.id.imageButtonCancelarUbicacion);

        nombreEvento = findViewById(R.id.editTextNombreEvento);
        fechaEvento = findViewById(R.id.editTextFechaEvento);
        direPlatEvento = findViewById(R.id.editTextUbicacionEvento);
        costoEvento = findViewById(R.id.editTextCostoEvento);
        horaInicioEvento = findViewById(R.id.editTextHoraInicioEvento);
        horaFinalEvento = findViewById(R.id.editTextHoraFinalEvento);
        descripcionEvento = findViewById(R.id.editTextDescripcionEvento);
        spinnerLocalidadEvento = findViewById(R.id.spinnerLocalidadEvento);
        spinnerCategoriaEvento = findViewById(R.id.spinnerCategoriaEvento);

        cancelarCrearEvento = findViewById(R.id.botonCancelarCrearEvento);
        aceptarCrearEvento = findViewById(R.id.botonAceptarCrearEvento);

        direPlatEvento.setOnClickListener(view -> mostrarMapa());
        direPlatEvento.setOnFocusChangeListener((view, hasFocus) -> {

            if (hasFocus)
                mostrarMapa();

        });
        layoutNombreEvento = findViewById(R.id.layoutNombreEvento);
        layoutFechaEvento = findViewById(R.id.layoutFechaEvento);
        layoutUbicacionEvento = findViewById(R.id.layoutUbicacionEvento);
        layoutLocalidadEvento = findViewById(R.id.layoutLocalidadEvento);
        layoutCostoEvento = findViewById(R.id.layoutCostoEvento);
        layoutHoraInicio = findViewById(R.id.layoutHoraInicio);
        layoutHoraFinal = findViewById(R.id.layoutHoraFinal);
        layoutTipoEvento = findViewById(R.id.layoutTipoEvento);
        layoutDescripcionEvento = findViewById(R.id.layoutDescripcionEvento);

        nombreEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutNombreEvento.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

        direPlatEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutUbicacionEvento.setErrorEnabled(false);

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

        costoEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutCostoEvento.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        horaInicioEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutHoraInicio.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        horaFinalEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutHoraFinal.setErrorEnabled(false);

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

        descripcionEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutDescripcionEvento.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        direPlatEvento.setOnClickListener(view -> mostrarMapa());
        direPlatEvento.setOnFocusChangeListener((view, hasFocus) -> {

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

        botonAceptarUbicacion.setOnClickListener(view -> {
            if (marker != null) {
                ubicacionDefinitiva = ubicacionMarker;

                if (ubicacionDefinitiva != null) {

                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                    List<Address> listaDireccion = null;
                    try {
                        listaDireccion = geocoder.getFromLocation(ubicacionDefinitiva.latitude, ubicacionDefinitiva.longitude, 1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    String ubicacion = listaDireccion.get(0).getAddressLine(0).split(",")[0];

                    direPlatEvento.setText(ubicacion);
                }
                layoutMap.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
                marker.remove();
            } else {
                Toast.makeText(this, "Seleccione una ubicación", Toast.LENGTH_SHORT).show();
            }

        });

        botonCancelarUbicación.setOnClickListener(view -> {
            layoutMap.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            if (marker != null) {
                marker.remove();
            }
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

                    if (marker != null)
                        marker.remove();
                    marker = gMap.addMarker(new MarkerOptions().position(latLng).title(ubicacion));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });

        });

    }

    private void mostrarMapa() {

        layoutMap.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (ubicacionDefinitiva != null) {
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

    private void mostrarTimePicker(EditText horarioEvento, Integer[] horaMinutosDefecto) {
        MostrarTimePicker timePicker = new MostrarTimePicker(
                this,
                horarioEvento,
                horaMinutosDefecto);
    }

    private void mostrarDatePicker() {
        MostrarDatePicker datePicker = new MostrarDatePicker(this, this.fechaEvento, this.fecha);
    }

    public void cambiarAEventos() {
        PaginaInicio.intensionInicializacion = PaginaInicio.EVENTOS;
        Intent miIntent = new Intent(this, PaginaInicio.class);
        startActivity(miIntent);
        finishAffinity();
    }

    public void crearEventoExpositor() {
        VerificarInformacionRegistro(new View(this));
    }

    public void VerificarInformacionRegistro(View view) {

        boolean flag = true;

        if (nombreEvento.getText().toString().trim().equals("")) {
            layoutNombreEvento.setError("Ingrese un nombre valido");
            flag = false;
        }
        String verificarFechaEvento = this.fechaEvento.getText().toString().trim();
        String[] verificarTamanoFechaEvento = verificarFechaEvento.split("/");
        if (verificarFechaEvento.equals("") || verificarTamanoFechaEvento.length != 3) {
            layoutFechaEvento.setError("Ingrese una fecha valida");
            flag = false;
        }
        if (direPlatEvento.getText().toString().trim().equals("")) {
            layoutUbicacionEvento.setError("Ingrese una ubicación valida");
            flag = false;
        }
        if (spinnerLocalidadEvento.getText().toString().equals("")) {
            layoutLocalidadEvento.setError("Seleccione una localidad");
            flag = false;
        }
        String verificarCostoEvento = costoEvento.getText().toString().trim();
        if (verificarCostoEvento.equals("") || !TextUtils.isDigitsOnly(verificarCostoEvento)) {
            layoutCostoEvento.setError("Ingrese un costo valido");
            flag = false;
        }
        if (horaInicioEvento.getText().toString().trim().equals("")) {
            layoutHoraInicio.setError("Seleccione una hora de inicio");
            flag = false;
        }
        if (horaFinalEvento.getText().toString().trim().equals("")) {
            layoutHoraFinal.setError("Seleccione una hora de fin");
            flag = false;
        }
        String horarioInicio = this.horaInicioEvento.getText().toString().trim();
        String horarioFinal = this.horaFinalEvento.getText().toString().trim();
        if (horarioInicio.replaceAll("[^a-zA-Z]", "").equals("pm") && horarioFinal.replaceAll("[^a-zA-Z]", "").equals("am")) {
            layoutHoraInicio.setError("La hora inicial deber ser menor que la hora final");
            flag = false;
        } else if ((horarioInicio.replaceAll("[^a-zA-Z]", "").equals("am") && horarioFinal.replaceAll("[^a-zA-Z]", "").equals("am")) || (horarioInicio.replaceAll("[^a-zA-Z]", "").equals("pm") && horarioFinal.replaceAll("[^a-zA-Z]", "").equals("pm"))) {
            String[] horaMinutoInicio = horarioInicio.split(":");
            String[] horaMinutoFinal = horarioFinal.split(":");
            if (horaMinutoInicio[0].equals(horaMinutoFinal[0]) && Integer.parseInt(horaMinutoInicio[1].replaceAll("[^\\d]", "")) >= Integer.parseInt(horaMinutoFinal[1].replaceAll("[^\\d]", ""))) {
                layoutHoraInicio.setError("La hora inicial deber ser menor que la hora final");

                flag = false;
            }
        }
        if (spinnerCategoriaEvento.getText().toString().equals("") || categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString()) == -1) {
            layoutTipoEvento.setError("Seleccione un tipo de evento");
            flag = false;
        }
        if (descripcionEvento.getText().toString().trim().equals("")) {
            layoutDescripcionEvento.setError("Ingrese una descripción valida");
            flag = false;
        }

        if (flag)
            CrearEvento(view);
    }

    public void CrearEvento(View view) {
        String nombreEvento = this.nombreEvento.getText().toString().trim();
        String fechaEvento = this.fechaEvento.getText().toString().trim();

        String[] partesFecha = fechaEvento.split("/");
        int diaEvento = Integer.parseInt(partesFecha[0]);
        int mesEvento = Integer.parseInt(partesFecha[1]);
        int AnioEvento = Integer.parseInt(partesFecha[2]);

        String ubicacionEvento = ubicacionDefinitiva.latitude + " - " + ubicacionDefinitiva.longitude;
        int costoEvento = Integer.parseInt(this.costoEvento.getText().toString());
        String horarioEvento = this.horaInicioEvento.getText().toString() + " - " + this.horaFinalEvento.getText().toString();
        String descripcionEvento = this.descripcionEvento.getText().toString();
        int localidad = localidadesAdapter.getPosition(spinnerLocalidadEvento.getText().toString());
        int categoria = categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString());

        long newId = new DbEventos(this).insertarEvento(nombreEvento,
                AnioEvento,
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
                new Date(AnioEvento, mesEvento, diaEvento),
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

        } catch (Exception e) {
            Toast.makeText(this, "Error al crear el evento", Toast.LENGTH_SHORT).show();
        }
    }
}