package com.example.datastructureproject_groupb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datastructureproject_groupb.db.DbEventos;

import java.util.Arrays;
import java.util.regex.Pattern;

public class CrearEventos extends AppCompatActivity{
    EditText nombreEvento, fechaEvento, ubicacionEvento, costoEvento, horarioEvento, descripcionEvento;
    Spinner spinnerLocalidadEvento, spinnerCategoriaEvento;
    Button cancelarCrearEvento, aceptarCrearEvento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_eventos);

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


        ArrayAdapter<String> localidadesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, localidades) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(getResources().getColor(R.color.black));
                return view;
            }
        };
        localidadesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocalidadEvento.setAdapter(localidadesAdapter);

        ArrayAdapter<String> categoriasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorias) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(getResources().getColor(R.color.black));
                return view;
            }
        };
        categoriasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoriaEvento.setAdapter(categoriasAdapter);

        String localidadPreseleccionada = "Usaquén"; // Por ejemplo, preseleccionamos "Usaquén"
        int index = Arrays.asList(localidades).indexOf(localidadPreseleccionada);
        spinnerLocalidadEvento.setSelection(index);


        String InteresPreseleccionada = "Musica"; // Por ejemplo, preseleccionamos "Usaquén"
        int index2 = Arrays.asList(categorias).indexOf(InteresPreseleccionada);
        spinnerCategoriaEvento.setSelection(index2);

        cancelarCrearEvento.setOnClickListener(view -> cambiarAEventos());
        aceptarCrearEvento.setOnClickListener(view -> crearEventoExpositor());

    }
    private static final String [] localidades= new String[]{ "Virtual","Usaquén", "Chapinero", "Santa Fe", "San Cristóbal", "Usme", "Tunjuelito", "Bosa", "Kennedy", "Fontibón", "Engativá", "Suba", "Barrios Unidos", "Teusaquillo", "Los Mártires", "Antonio Nariño", "Puente Aranda", "La Candelaria", "Rafael Uribe Uribe", "Ciudad Bolívar", "Sumapaz"   };
    private static final String [] categorias= new String[]{"Musica", "Talleres",     };

    public void cambiarAEventos() {
        Intent miIntent = new Intent(this, Eventos.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }

    public void crearEventoExpositor(){
        VerificarInformacionRegistro(new View(this));
    }

    public void mostrarLocalidades() {
        spinnerLocalidadEvento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocalidad = parent.getItemAtPosition(position).toString();
                // Puedes hacer algo con la localidad seleccionada si es necesario
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Este método se llama cuando no se ha seleccionado ningún elemento
            }
        });
    }

    public void mostrarCategorias() {
        spinnerCategoriaEvento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedInteres = parent.getItemAtPosition(position).toString();
                // Puedes hacer algo con el interés seleccionado si es necesario
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Este método se llama cuando no se ha seleccionado ningún elemento
            }
        });
    }

    public void VerificarInformacionRegistro(View view) {

        boolean flag = true;
        String mensajeError = "";

        if(nombreEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado nombre valido\n";
            flag = false;
        }

        String verificarFechaEvento = this.fechaEvento.getText().toString().trim();

        // Dividir la fecha por "/"
        String[] verificarTamanoFechaEvento = verificarFechaEvento.split("/");
        if(verificarFechaEvento.equals("") || verificarTamanoFechaEvento.length  != 3) {
            mensajeError += "No ha ingresado fecha valida\n";
            flag = false;
        }
        if(ubicacionEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado una ubicación valida\n";
            flag = false;
        }
        if(spinnerLocalidadEvento.getSelectedItem() == null || spinnerLocalidadEvento.getSelectedItem().toString().trim().equals("")) {
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
        if(spinnerCategoriaEvento.getSelectedItem() == null || spinnerCategoriaEvento.getSelectedItem().toString().trim().equals("")) {
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

        // Dividir la fecha por "/"
        String[] partesFecha = fechaEvento.split("/");
        // Obtener el día
        int diaEvento = Integer.parseInt(partesFecha[0]);
        // Obtener el mes
        int mesEvento = Integer.parseInt(partesFecha[1]);
        // Obtener el año
        int AnoEvento = Integer.parseInt(partesFecha[2]);

        String ubicacionEvento = this.ubicacionEvento.getText().toString();
        int costoEvento = Integer.parseInt(this.costoEvento.getText().toString());
        String horarioEvento = this.horarioEvento.getText().toString();
        String descripcionEvento = this.descripcionEvento.getText().toString();
        int localidad=0;
        int categoria=0;

        // Obtener una instancia de DbEventos (sustituye "NombreDeTuActividadOFragmento" por el nombre real)
        DbEventos dbEventos = new DbEventos(this);

        // Insertar el evento en la base de datos
        long idEventoInsertado = dbEventos.insertarEvento(nombreEvento, AnoEvento, mesEvento, diaEvento, ubicacionEvento,
                costoEvento, horarioEvento, descripcionEvento, localidad, categoria);

        if (idEventoInsertado != -1) {
            // El evento se insertó correctamente, puedes mostrar un mensaje de éxito o realizar otras acciones.
            Toast.makeText(this, "Evento creado con éxito", Toast.LENGTH_SHORT).show();
            cambiarAEventos();
        } else {
            // Ocurrió un error al insertar el evento, puedes mostrar un mensaje de error o manejar la situación de otra manera.
            Toast.makeText(this, "Error al crear el evento", Toast.LENGTH_SHORT).show();
        }
    }
}