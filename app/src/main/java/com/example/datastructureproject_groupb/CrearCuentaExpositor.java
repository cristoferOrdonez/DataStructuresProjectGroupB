package com.example.datastructureproject_groupb;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datastructureproject_groupb.db.DbExpositor;
import com.example.datastructureproject_groupb.db.DbUsuarios;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearCuentaExpositor extends AppCompatActivity {
    EditText NombreRegistroUsuario, CorreoRegistroUsuario, ConfirmarContrasenaRegistroUsuario, ContrasenaRegistroUsuario;
    Spinner spinnerLocalidadRegistroUsuario,spinnerInteresesRegistroUsuario;
    Button cancelarRegistroUsuario, registrasrseRegistroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_expositor);

        NombreRegistroUsuario=findViewById(R.id.textViewNombreRegistroExpositor);
        CorreoRegistroUsuario=findViewById(R.id.textViewCorreoRegistroExpositor);
        ConfirmarContrasenaRegistroUsuario=findViewById(R.id.textViewConfirmarContrasenaRegistroExpositor);
        ContrasenaRegistroUsuario=findViewById(R.id.textViewContrasenaRegistroExpositor);
        spinnerInteresesRegistroUsuario=findViewById(R.id.spinnerInteresesRegistroExpositor);
        spinnerLocalidadRegistroUsuario=findViewById(R.id.spinnerLocalidadRegistroExpositor);
        cancelarRegistroUsuario=findViewById(R.id.botonCancelarRegistroExpositor);
        registrasrseRegistroUsuario=findViewById(R.id.botonRegistratseRegistroExpositor);



        cancelarRegistroUsuario.setOnClickListener(view -> cambiarAPaginaPrincipal());
        registrasrseRegistroUsuario.setOnClickListener(view -> registrarseComoExpositor());



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
        spinnerLocalidadRegistroUsuario.setAdapter(localidadesAdapter);

        ArrayAdapter<String> interesesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, intereses) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(getResources().getColor(R.color.black));
                return view;
            }
        };
        interesesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInteresesRegistroUsuario.setAdapter(interesesAdapter);

        String localidadPreseleccionada = "Usaquén"; // Por ejemplo, preseleccionamos "Usaquén"
        int index = Arrays.asList(localidades).indexOf(localidadPreseleccionada);
        spinnerLocalidadRegistroUsuario.setSelection(index);


        String InteresPreseleccionada = "Musica"; // Por ejemplo, preseleccionamos "Usaquén"
        int index2 = Arrays.asList(intereses).indexOf(InteresPreseleccionada);
        spinnerInteresesRegistroUsuario.setSelection(index2);









    }
    private static final String [] localidades= new String[]{ "Virtual","Usaquén", "Chapinero", "Santa Fe", "San Cristóbal", "Usme", "Tunjuelito", "Bosa", "Kennedy", "Fontibón", "Engativá", "Suba", "Barrios Unidos", "Teusaquillo", "Los Mártires", "Antonio Nariño", "Puente Aranda", "La Candelaria", "Rafael Uribe Uribe", "Ciudad Bolívar", "Sumapaz"   };
    private static final String [] intereses= new String[]{"Musica", "Talleres",     };

    public void cambiarAPaginaPrincipal() {
        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
    public void registrarseComoExpositor(){
        VerificarInformacionRegistroExpositor(new View(this));
    }
    public void mostrarLocalidades() {
        spinnerLocalidadRegistroUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void mostrarIntereses() {
        spinnerInteresesRegistroUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    public void VerificarInformacionRegistroExpositor(View view) {

        boolean flag = true;
        String mensajeError = "";

        if(NombreRegistroUsuario.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado nombres validos\n";
            flag = false;
        }

        if(spinnerLocalidadRegistroUsuario.getSelectedItem() == null || spinnerLocalidadRegistroUsuario.getSelectedItem().toString().trim().equals("")) {
            mensajeError += "Seleccione una Localidad\n";
            flag = false;
        }
        if(spinnerInteresesRegistroUsuario.getSelectedItem() == null || spinnerInteresesRegistroUsuario.getSelectedItem().toString().trim().equals("")) {
            mensajeError += "Seleccione un Interes\n";
            flag = false;
        }



        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(CorreoRegistroUsuario.getText().toString());

        if(!mather.find()){
            mensajeError += "No ha ingresado un correo electronico valido\n";
            flag = false;
        }
        if(ContrasenaRegistroUsuario.getText().toString().length() < 8){
            mensajeError += "Debe ingresar una contraseña de por lo menos 8 caracteres\n";
            flag = false;
        }
        if(ContrasenaRegistroUsuario.getText().toString().contains(" ")){
            mensajeError += "La contraseña no puede contener espacios en blanco\n";
            flag = false;
        }
        if(!ContrasenaRegistroUsuario.getText().toString().equals(ConfirmarContrasenaRegistroUsuario.getText().toString())){
            mensajeError += "Las contraseñas no coinciden\n";
            flag = false;
        }
        if(!ContrasenaRegistroUsuario.getText().toString().equals(ConfirmarContrasenaRegistroUsuario.getText().toString())){
            mensajeError += "Las contraseñas no coinciden\n";
            flag = false;
        }

        if(flag)
            RegistrarExpositor(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();

    }

    public void RegistrarExpositor(View view) {
        String nombres = this.NombreRegistroUsuario.getText().toString().trim();

        String correoElectronicoR = this.CorreoRegistroUsuario.getText().toString();
        String contrasenaR = this.ContrasenaRegistroUsuario.getText().toString();
        int localidad=0;
        int interes=0;

        DbExpositor dbExpositor = new DbExpositor(this);
        DbUsuarios dbUsuarios = new DbUsuarios(this);



        if(!verificarRepeticion(dbExpositor.obtenerCorreosElectronicosExpositores()) && !verificarRepeticion(dbUsuarios.obtenerCorreosElectronicos())){

            long i = dbExpositor.agregarExpositor(nombres, correoElectronicoR.toLowerCase(), contrasenaR, localidad, interes);
            Toast.makeText(this, "Se ha registrado como expositor exitosamente.", Toast.LENGTH_SHORT).show();
            cambiarAPaginaPrincipal();

        } else {

            Toast.makeText(this, "El correo electronico ingresado ya se encuentra registrado", Toast.LENGTH_SHORT).show();

        }

    }
    public int stringAIntLocalidad(String s){
        return 0;

    }
    public int stringAIntInteres(String s){
        return 0;
    }

    public boolean verificarRepeticion(List<String> correos) {

        boolean repeticion = false;

        for(String correo : correos){

            repeticion = correo.equalsIgnoreCase(CorreoRegistroUsuario.getText().toString().trim());

            if(repeticion)
                break;

        }

        return repeticion;

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == KeyEvent.KEYCODE_BACK){

            cambiarAPaginaPrincipal();

        }

        return super.onKeyDown(keyCode, event);

    }
}
