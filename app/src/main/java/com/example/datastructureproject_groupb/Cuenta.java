package com.example.datastructureproject_groupb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.view.ContextThemeWrapper;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.LinkedList;
import com.example.datastructureproject_groupb.db.DbExpositor;
import com.example.datastructureproject_groupb.db.DbUsuariosComunes;
import com.example.datastructureproject_groupb.entidades.Artista;
import com.example.datastructureproject_groupb.entidades.UsuarioComun;
import com.example.datastructureproject_groupb.entidades.UsuarioRegistrado;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cuenta extends AppCompatActivity {
    Button botonPaginaPrincipal, botonEventos, botonDescubrir, botonAcceder, botonCrearCuentaUsuario, botonCrearCuentaExpositor;
    EditText nombreAcceder, correoElectronicoAcceder, contrasenaAcceder, apellidoAcceder, edadAcceder;
    Spinner spinnerLocalidad, spinnerIntereses;
    private static final String [] localidades= new String[]{ "Virtual","Usaquén", "Chapinero", "Santa Fe", "San Cristóbal", "Usme", "Tunjuelito", "Bosa", "Kennedy", "Fontibón", "Engativá", "Suba", "Barrios Unidos", "Teusaquillo", "Los Mártires", "Antonio Nariño", "Puente Aranda", "La Candelaria", "Rafael Uribe Uribe", "Ciudad Bolívar", "Sumapaz"   };
    private static final String [] intereses= new String[]{"Musica", "Talleres",     };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Bocu.estadoUsuario == Bocu.ARTISTA)
            establecerContenidoExpositor();
        else if (Bocu.estadoUsuario == Bocu.USUARIO_COMUN)
            establecerContenidoUsuarioComun();
        else
            establecerContenidoUsuarioNoRegistrado();

    }

    private void establecerContenidoExpositor(){
        setContentView(R.layout.activity_cuenta_expositor);
        botonPaginaPrincipal = findViewById(R.id.botonPaginaPrincipalCuenta);
        botonDescubrir = findViewById(R.id.botonDescubrirCuenta);
        botonEventos = findViewById(R.id.botonEventosCuenta);

        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonEventos.setOnClickListener(view -> cambiarAEventos());
        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());

        nombreAcceder = findViewById(R.id.editTextNombre);
        correoElectronicoAcceder = findViewById(R.id.editTextCorreo);
        contrasenaAcceder = findViewById(R.id.editTextContrasena);

        nombreAcceder.setText(((Artista)Bocu.usuario).getNombreArtista());
        correoElectronicoAcceder.setText(((Artista)Bocu.usuario).getCorreoElectronico());
        contrasenaAcceder.setText(((Artista)Bocu.usuario).getContrasena());

        nombreAcceder.setEnabled(false);
        correoElectronicoAcceder.setEnabled(false);
        contrasenaAcceder.setEnabled(false);

        spinnerLocalidad = findViewById(R.id.spinnerLocalidad);
        spinnerIntereses = findViewById(R.id.spinnerIntereses);

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
        spinnerLocalidad.setAdapter(localidadesAdapter);

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
        spinnerIntereses.setAdapter(interesesAdapter);

        spinnerLocalidad.setSelection(((Artista)Bocu.usuario).getLocalidad());

        spinnerIntereses.setSelection(((Artista)Bocu.usuario).getTipoDeEvento());

        spinnerIntereses.setEnabled(false);
        spinnerLocalidad.setEnabled(false);

    }

    private void establecerContenidoUsuarioComun(){
        setContentView(R.layout.activity_cuenta_usuario_comun);
        botonPaginaPrincipal = findViewById(R.id.botonPaginaPrincipalCuenta);
        botonDescubrir = findViewById(R.id.botonDescubrirCuenta);
        botonEventos = findViewById(R.id.botonEventosCuenta);

        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonEventos.setOnClickListener(view -> cambiarAEventos());
        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());

        nombreAcceder = findViewById(R.id.editTextNombre);
        correoElectronicoAcceder = findViewById(R.id.editTextCorreo);
        contrasenaAcceder = findViewById(R.id.editTextContrasena);
        apellidoAcceder = findViewById(R.id.editTextApellido);
        edadAcceder = findViewById(R.id.editTextEdad);

        nombreAcceder.setText(((UsuarioComun)Bocu.usuario).getNombres());
        apellidoAcceder.setText(((UsuarioComun)Bocu.usuario).getApellidos());
        edadAcceder.setText(String.valueOf(((UsuarioComun)Bocu.usuario).getEdad()));
        correoElectronicoAcceder.setText(((UsuarioComun)Bocu.usuario).getCorreoElectronico());
        contrasenaAcceder.setText(((UsuarioComun)Bocu.usuario).getContrasena());

        nombreAcceder.setEnabled(false);
        correoElectronicoAcceder.setEnabled(false);
        contrasenaAcceder.setEnabled(false);
        apellidoAcceder.setEnabled(false);
        edadAcceder.setEnabled(false);

        spinnerLocalidad = findViewById(R.id.spinnerLocalidad);
        spinnerIntereses = findViewById(R.id.spinnerIntereses);

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
        spinnerLocalidad.setAdapter(localidadesAdapter);

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
        spinnerIntereses.setAdapter(interesesAdapter);

        spinnerLocalidad.setSelection(((UsuarioComun)Bocu.usuario).getLocalidad());

        spinnerIntereses.setSelection(((UsuarioComun)Bocu.usuario).getIntereses());

        spinnerIntereses.setEnabled(false);
        spinnerLocalidad.setEnabled(false);
    }

    private void establecerContenidoUsuarioNoRegistrado(){
        setContentView(R.layout.activity_cuenta_usuario_no_registrado);

        if(Bocu.expositores == null){
            Bocu.expositores = new DbExpositor(this).obtenerExpositores();
        }
        if(Bocu.usuariosComunes == null){
            Bocu.usuariosComunes = new DbUsuariosComunes(this).obtenerUsuariosComunes();
        }

        botonPaginaPrincipal=findViewById(R.id.botonPaginaPrincipalCuenta);
        botonEventos=findViewById(R.id.botonEventosCuenta);
        botonDescubrir=findViewById(R.id.botonDescubrirCuenta);
        botonAcceder=findViewById(R.id.botonAccederCuenta);
        botonCrearCuentaUsuario=findViewById(R.id.botonCrearCuentaUsuario);
        botonCrearCuentaExpositor=findViewById(R.id.botonCrearCuentaExpositor);

        correoElectronicoAcceder =findViewById(R.id.editTextCorreo);
        contrasenaAcceder =findViewById(R.id.editTextContrasena);


        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonEventos.setOnClickListener(view -> cambiarAEventos());
        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());
        botonCrearCuentaUsuario.setOnClickListener(view -> cambiarARegistroUsuario());
        botonCrearCuentaExpositor.setOnClickListener(view -> cambiarARegistroExpositor());
    }

    protected void onDestroy() {
        super.onDestroy();
    }


    public void acceder(UsuarioRegistrado usuario, int tipoUsuario) {

        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        Bocu.usuario = usuario;
        Bocu.estadoUsuario = tipoUsuario;
        if (tipoUsuario == Bocu.USUARIO_COMUN) {
            Toast.makeText(this, "Ingreso correctamente como Usuario", Toast.LENGTH_SHORT).show();
        } else {
            establecerEventosExpositor();
            Toast.makeText(this, "Ingreso correctamente como Artista", Toast.LENGTH_SHORT).show();
        }
        startActivity(miIntent);
        finishAffinity();
        Bocu.expositores = null;
        Bocu.usuariosComunes = null;
    }

    public void revisar(View view){

        int opcion = verificarExistencia();
        if (opcion > -1) {
            if (opcion == 0) {
                UsuarioComun usuario = verUsuarioComun(correoElectronicoAcceder.getText().toString().toLowerCase());
                if (contrasenaAcceder.getText().toString().equals(usuario.getContrasena())) {
                    acceder(usuario, Bocu.USUARIO_COMUN);
                } else {
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            } else if (opcion == 1) {
                Artista artista = verExpositor(correoElectronicoAcceder.getText().toString().toLowerCase());
                if (contrasenaAcceder.getText().toString().equals(artista.getContrasena())) {
                    acceder(artista, Bocu.ARTISTA);
                } else {
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }

        } else {

            Toast.makeText(this, "El correo electronico ingresado no se encuentra registrado.", Toast.LENGTH_SHORT).show();

        }

    }

    public void VerificarInformacionAcceso (View view) {

        boolean flag = true;
        String mensajeError = "";

        if(correoElectronicoAcceder.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado un correo valido\n";
            flag = false;
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correoElectronicoAcceder.getText().toString());

        if(!mather.find()){
            mensajeError += "No ha ingresado un correo electronico valido\n";
            flag = false;
        }
        if(contrasenaAcceder.getText().toString().length() < 8){
            mensajeError += "Debe ingresar una contraseña de por lo menos 8 caracteres\n";
            flag = false;
        }
        if(contrasenaAcceder.getText().toString().contains(" ")){
            mensajeError += "La contraseña no puede contener espacios en blanco\n";
            flag = false;
        }

        if(flag)
            revisar(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();
    }

    public int verificarExistencia(){

        LinkedList<String> correosArtista = new LinkedList<>(), correosUsuario = new LinkedList<>();

        int veces = Bocu.expositores.size();

        for(int i = 0; i < veces; i++){

            correosArtista.pushFront(Bocu.expositores.get(i).getCorreoElectronico());

        }

        veces = Bocu.usuariosComunes.size();

        for(int i = 0; i < veces; i++){

            correosUsuario.pushFront(Bocu.usuariosComunes.get(i).getCorreoElectronico());

        }

        boolean existencia;

        for(String correo : correosUsuario){

            existencia = correo.equalsIgnoreCase(correoElectronicoAcceder.getText().toString());
            if(existencia)
                return 0;
        }
        for(String correo : correosArtista){
            existencia = correo.equalsIgnoreCase(correoElectronicoAcceder.getText().toString());
            if(existencia)
                return 1;
        }

        return -1;

    }

    public void cambiarAPaginaPrincipal() {
        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarAEventos() {
        if (Bocu.estadoUsuario == Bocu.ARTISTA) {
            Intent miIntent = new Intent(this, Eventos.class);
            startActivity(miIntent);
            finishAffinity();
        } else{
            Toast.makeText(this, "Usted no es un Artista", Toast.LENGTH_SHORT).show();
        }
    }
    public void cambiarADescubrir() {
        Intent miIntent = new Intent(this, Descubrir.class);
        startActivity(miIntent);
        finishAffinity();
    }

    public void cambiarARegistroUsuario() {
        Intent miIntent = new Intent(this, com.example.datastructureproject_groupb.CrearCuentaUsuario.class);
        startActivity(miIntent);
        finishAffinity();


    }
    public void cambiarARegistroExpositor() {
        Intent miIntent = new Intent(this, com.example.datastructureproject_groupb.CrearCuentaExpositor.class);
        startActivity(miIntent);
        finishAffinity();


    }

    private UsuarioComun verUsuarioComun(String correoElectronico){
        int veces = Bocu.usuariosComunes.size();
        for(int i = 0; i < veces; i++)
            if(Bocu.usuariosComunes.get(i).getCorreoElectronico().equals(correoElectronico))
                return Bocu.usuariosComunes.get(i);

        return null;
    }

    private Artista verExpositor(String correoElectronico){
        int veces = Bocu.expositores.size();
        for(int i = 0; i < veces; i++)
            if(Bocu.expositores.get(i).getCorreoElectronico().equals(correoElectronico))
                return Bocu.expositores.get(i);
        return null;
    }

    private void establecerEventosExpositor(){
        Bocu.eventosExpositor = new DynamicUnsortedList<>();
        Bocu.posicionesEventosExpositor = new DynamicUnsortedList<>();

        int veces = Bocu.eventos.size();

        String correoElectronico = Bocu.usuario.getCorreoElectronico();

        for(int i = 0; i < veces; i++)
            if(Bocu.eventos.get(i).getCorreoAutor().equals(correoElectronico)){
                Bocu.eventosExpositor.insert(Bocu.eventos.get(i));
                Bocu.posicionesEventosExpositor.insert(i);
            }

    }

}
