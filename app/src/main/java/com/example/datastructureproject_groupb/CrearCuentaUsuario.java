package com.example.datastructureproject_groupb;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.LinkedList;
import com.example.datastructureproject_groupb.db.DbExpositor;
import com.example.datastructureproject_groupb.db.DbUsuariosComunes;
import com.example.datastructureproject_groupb.entidades.UsuarioComun;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearCuentaUsuario extends AppCompatActivity {
    EditText EdadRegistroUsuario,NombreRegistroUsuario, ApellidoRegistroUsuario, CorreoRegistroUsuario, ConfirmarContrasenaRegistroUsuario, ContrasenaRegistroUsuario;
    Spinner spinnerLocalidadRegistroUsuario,spinnerInteresesRegistroUsuario;
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
        spinnerInteresesRegistroUsuario=findViewById(R.id.spinnerInteresesRegistroUsuario);
        spinnerLocalidadRegistroUsuario=findViewById(R.id.spinnerLocalidadRegistroUsuario);
        cancelarRegistroUsuario=findViewById(R.id.botonCancelarRegistroUsuario);
        registrasrseRegistroUsuario=findViewById(R.id.botonRegistratseRegistroUsuario);

        ArrayAdapter<String> localidadesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Bocu.LOCALIDADES) {
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

        ArrayAdapter<String> interesesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Bocu.INTERESES) {
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

        String localidadPreseleccionada = "Usaquén";
        int index = Arrays.asList(Bocu.LOCALIDADES).indexOf(localidadPreseleccionada);
        spinnerLocalidadRegistroUsuario.setSelection(index);


        String InteresPreseleccionada = "Musica";
        int index2 = Arrays.asList(Bocu.INTERESES).indexOf(InteresPreseleccionada);
        spinnerInteresesRegistroUsuario.setSelection(index2);

        cancelarRegistroUsuario.setOnClickListener(view -> cambiarAPaginaPrincipal());
        registrasrseRegistroUsuario.setOnClickListener(view -> registrarseComoUsuario());
    }

    public void cambiarAPaginaPrincipal() {
        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        startActivity(miIntent);
        finishAffinity();
    }
    public void mostrarLocalidades() {
        spinnerLocalidadRegistroUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocalidad = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void mostrarIntereses() {
        spinnerInteresesRegistroUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedInteres = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void registrarseComoUsuario(){
        VerificarInformacionRegistro(new View(this));
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
        if(spinnerLocalidadRegistroUsuario.getSelectedItem() == null || spinnerLocalidadRegistroUsuario.getSelectedItem().toString().trim().equals("")) {
            mensajeError += "Seleccione una Localidad\n";
            flag = false;
        }
        if(spinnerInteresesRegistroUsuario.getSelectedItem() == null || spinnerInteresesRegistroUsuario.getSelectedItem().toString().trim().equals("")) {
            mensajeError += "Seleccione un Interes\n";
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

    public void Registrar(View view){
        String nombres = this.NombreRegistroUsuario.getText().toString().trim();
        String apellidos = this.ApellidoRegistroUsuario.getText().toString().trim();
        int edad = Integer.parseInt(this.EdadRegistroUsuario.getText().toString());
        String correoElectronicoR = this.CorreoRegistroUsuario.getText().toString();
        String contrasenaR = this.ContrasenaRegistroUsuario.getText().toString();
        int localidad = spinnerLocalidadRegistroUsuario.getSelectedItemPosition();
        int interes = spinnerInteresesRegistroUsuario.getSelectedItemPosition();

        UsuarioComun usuarioComun = new UsuarioComun(Bocu.usuariosComunes.size(), nombres, apellidos, edad, correoElectronicoR.toLowerCase(), contrasenaR, localidad, interes);

        if(!verificarRepeticion()){

            Bocu.usuariosComunes.insert(usuarioComun);
            new DbUsuariosComunes(this).agregarUsuario(nombres, apellidos, edad, correoElectronicoR.toLowerCase(), contrasenaR, localidad, interes);
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

    public boolean verificarRepeticion() {

        LinkedList<String> correos = new LinkedList<>();

        int veces = Bocu.expositores.size();

        for(int i = 0; i < veces; i++)
            correos.pushFront(Bocu.expositores.get(i).getCorreoElectronico());

        veces = Bocu.usuariosComunes.size();

        for(int i = 0; i < veces; i++)
            correos.pushFront(Bocu.usuariosComunes.get(i).getCorreoElectronico());

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
