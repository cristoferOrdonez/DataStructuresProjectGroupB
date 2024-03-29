package com.example.datastructureproject_groupb;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datastructureproject_groupb.db.DbUsuarios;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearCuentaUsuario extends AppCompatActivity {
    EditText EdadRegistroUsuario,NombreRegistroUsuario, ApellidoRegistroUsuario, CorreoRegistroUsuario, ConfirmarContrasenaRegistroUsuario, ContrasenaRegistroUsuario;
    AutoCompleteTextView autoCompleteTextViewLocalidadRegistroUsuario,autoCompleteTextViewInteresesRegistroUsuario;
    Button cancelarRegistroUsuario, registrasrseRegistroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        NombreRegistroUsuario=findViewById(R.id.textViewNombreRegistroUsuario);
        ApellidoRegistroUsuario=findViewById(R.id.textViewApellidoRegistroUsuario);
        CorreoRegistroUsuario=findViewById(R.id.textViewCorreoRegistroUsuario);
        ConfirmarContrasenaRegistroUsuario=findViewById(R.id.textViewConfirmarContrasenaRegistroUsuario);
        ContrasenaRegistroUsuario=findViewById(R.id.textViewContrasenaRegistroUsuario);
        EdadRegistroUsuario=findViewById(R.id.editTextEdadRegistroUsuario);
        autoCompleteTextViewInteresesRegistroUsuario=findViewById(R.id.autoCompleteTextViewInteresesRegistroUsuario);
        autoCompleteTextViewLocalidadRegistroUsuario=findViewById(R.id.autoCompleteTextViewLocalidadRegistroUsuario);
        cancelarRegistroUsuario=findViewById(R.id.botonCancelarRegistroUsuario);
        registrasrseRegistroUsuario=findViewById(R.id.botonRegistratseRegistroUsuario);



        cancelarRegistroUsuario.setOnClickListener(view -> cambiarAPaginaPrincipal());
        registrasrseRegistroUsuario.setOnClickListener(view -> registrarseComoUsuario());



        autoCompleteTextViewLocalidadRegistroUsuario.setOnClickListener(view->mostrarLocalidades());
        autoCompleteTextViewInteresesRegistroUsuario.setOnClickListener(view->mostrarIntereses());









    }
    private static final String [] localidades= new String[]{ "Virtual","Usaquén", "Chapinero", "Santa Fe", "San Cristóbal", "Usme", "Tunjuelito", "Bosa", "Kennedy", "Fontibón", "Engativá", "Suba", "Barrios Unidos", "Teusaquillo", "Los Mártires", "Antonio Nariño", "Puente Aranda", "La Candelaria", "Rafael Uribe Uribe", "Ciudad Bolívar", "Sumapaz"   };
    private static final String [] intereses= new String[]{"Musica", "Talleres",     };

    public void cambiarAPaginaPrincipal() {
        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
    public void registrarseComoUsuario(){
        VerificarInformacionRegistro(new View(this));
    }
    public void mostrarLocalidades() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar Localidad");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, localidades);
        builder.setAdapter(adapter, (dialog, which) -> {
            String selectedLocalidad = localidades[which];
            autoCompleteTextViewLocalidadRegistroUsuario.setText(selectedLocalidad);
        });
        builder.show();
    }

    public void mostrarIntereses() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar Intereses");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, intereses);
        builder.setAdapter(adapter, (dialog, which) -> {
            String selectedInteres = intereses[which];
            autoCompleteTextViewInteresesRegistroUsuario.setText(selectedInteres);
        });

        builder.show();
    }
    public void VerificarInformacionRegistro(View view) {

        boolean flag = true;
        String mensajeError = "";

        if(NombreRegistroUsuario.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado nombres validos\n";
            flag = false;
        }
        if(ApellidoRegistroUsuario.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado apellidos validos\n";
            flag = false;
        }
        String edadString = EdadRegistroUsuario.getText().toString().trim();
        if (edadString.isEmpty()) {
            flag=false;
            Toast.makeText(this, "Ingrese una edad válida", Toast.LENGTH_SHORT).show();
        }
        if(autoCompleteTextViewLocalidadRegistroUsuario.getText().toString().trim().equals("")) {
            mensajeError += "Selecione una Localidad\n";
            flag = false;
        }
        if(autoCompleteTextViewInteresesRegistroUsuario.getText().toString().trim().equals("") ) {
            mensajeError += "Selecione un Interes\n";
            flag = false;
        }

        if(EdadRegistroUsuario.getText().toString().trim().equals("") || Integer.parseInt(EdadRegistroUsuario.getText().toString().trim()) > 150) {
            mensajeError += "No ha ingresado una edad valida\n";
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
            Registrar(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();

    }

    public void Registrar(View view) {
        String nombres = this.NombreRegistroUsuario.getText().toString().trim();
        String apellidos = this.ApellidoRegistroUsuario.getText().toString().trim();
        int edad = Integer.parseInt(this.EdadRegistroUsuario.getText().toString());
        String correoElectronicoR = this.CorreoRegistroUsuario.getText().toString();
        String contrasenaR = this.ContrasenaRegistroUsuario.getText().toString();
        int localidad=0;
        int interes=0;

        DbUsuarios dbUsuarios = new DbUsuarios(this);

        if(!verificarRepeticion(dbUsuarios.obtenerCorreosElectronicos())){

            long i = dbUsuarios.agregarUsuario(nombres, apellidos, edad, correoElectronicoR.toLowerCase(), contrasenaR, localidad, interes);
            Toast.makeText(this, "Se ha registrado como usuario exitosamente.", Toast.LENGTH_SHORT).show();

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
