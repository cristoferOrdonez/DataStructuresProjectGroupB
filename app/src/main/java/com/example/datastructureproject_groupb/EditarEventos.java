package com.example.datastructureproject_groupb;

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

import java.util.Date;
import java.util.regex.Pattern;

public class EditarEventos extends AppCompatActivity {
    EditText nombreEvento, fechaEvento, ubicacionEvento, costoEvento, horarioEvento, descripcionEvento;
    Spinner spinnerLocalidadEvento, spinnerCategoriaEvento;
    Button cancelarEditarEvento, aceptarEditarEvento;
    long idEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_eventos);

        nombreEvento = findViewById(R.id.editTextNombreEvento);
        nombreEvento.setText(getIntent().getStringExtra("NOMBRE_EVENTO"));
        fechaEvento = findViewById(R.id.editTextFechaEvento);
        fechaEvento.setText(getIntent().getStringExtra("FECHA_EVENTO"));
        ubicacionEvento = findViewById(R.id.editTextUbicacionEvento);
        ubicacionEvento.setText(getIntent().getStringExtra("UBICACION_EVENTO"));
        costoEvento = findViewById(R.id.editTextCostoEvento);
        costoEvento.setText(getIntent().getStringExtra("COSTO_EVENTO"));
        horarioEvento = findViewById(R.id.editTextHorarioEvento);
        horarioEvento.setText(getIntent().getStringExtra("HORARIO_EVENTO"));
        descripcionEvento = findViewById(R.id.editTextDescripcionEvento);
        descripcionEvento.setText(getIntent().getStringExtra("DESCRIPCION_EVENTO"));
        spinnerLocalidadEvento = findViewById(R.id.spinnerLocalidadEvento);
        spinnerCategoriaEvento = findViewById(R.id.spinnerCategoriaEvento);

        cancelarEditarEvento = findViewById(R.id.botonCancelarEditarEvento);
        aceptarEditarEvento = findViewById(R.id.botonAceptarEditarEvento);

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

        spinnerLocalidadEvento.setSelection(getIntent().getIntExtra("LOCALIDAD_EVENTO", -1));


        spinnerCategoriaEvento.setSelection(getIntent().getIntExtra("CATEGORIA_EVENTO", -1));


        cancelarEditarEvento.setOnClickListener(view -> cambiarAEventos());
        aceptarEditarEvento.setOnClickListener(view -> editarEventoExpositor());

    }

    private static final String [] localidades= new String[]{ "Virtual","Usaquén", "Chapinero", "Santa Fe", "San Cristóbal", "Usme", "Tunjuelito", "Bosa", "Kennedy", "Fontibón", "Engativá", "Suba", "Barrios Unidos", "Teusaquillo", "Los Mártires", "Antonio Nariño", "Puente Aranda", "La Candelaria", "Rafael Uribe Uribe", "Ciudad Bolívar", "Sumapaz"   };
    private static final String [] categorias= new String[]{"Musica", "Talleres",     };

    public void cambiarAEventos() {
        Intent miIntent = new Intent(this, Eventos.class);
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
        if (spinnerLocalidadEvento.getSelectedItem() == null || spinnerLocalidadEvento.getSelectedItem().toString().trim().equals("")) {
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
        if (spinnerCategoriaEvento.getSelectedItem() == null || spinnerCategoriaEvento.getSelectedItem().toString().trim().equals("")) {
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
        int localidad = spinnerLocalidadEvento.getSelectedItemPosition();
        int categoria = spinnerCategoriaEvento.getSelectedItemPosition();

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