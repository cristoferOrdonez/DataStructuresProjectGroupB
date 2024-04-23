package com.example.datastructureproject_groupb;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.LinkedList;
import com.example.datastructureproject_groupb.db.DbExpositor;
import com.example.datastructureproject_groupb.db.DbSesion;
import com.example.datastructureproject_groupb.entidades.Artista;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearCuentaExpositor extends AppCompatActivity {
    private TextInputEditText nombreRegistroExpositor, correoRegistroExpositor, confirmarContrasenaRegistroExpositor, contrasenaRegistroExpositor;
    private MaterialAutoCompleteTextView spinnerLocalidadRegistroUsuario,spinnerInteresesRegistroUsuario;
    private Button cancelarRegistroExpositor, registrasrseRegistroExpositor;
    private ArrayAdapter<String> interesesAdapter, localidadesAdapter;
    private LinearLayout layoutBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_expositor);

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

        nombreRegistroExpositor =findViewById(R.id.textViewNombre);
        correoRegistroExpositor =findViewById(R.id.textViewCorreoRegistroExpositor);
        confirmarContrasenaRegistroExpositor =findViewById(R.id.textViewConfirmarContrasenaRegistroExpositor);
        contrasenaRegistroExpositor =findViewById(R.id.textViewContrasenaRegistroExpositor);
        spinnerInteresesRegistroUsuario=findViewById(R.id.spinnerInteresesRegistroExpositor);
        spinnerLocalidadRegistroUsuario=findViewById(R.id.spinnerLocalidadRegistroExpositor);
        cancelarRegistroExpositor =findViewById(R.id.botonCancelarRegistroExpositor);
        registrasrseRegistroExpositor =findViewById(R.id.botonRegistratseRegistroExpositor);

        cancelarRegistroExpositor.setOnClickListener(view -> cambiarAPaginaPrincipal());
        registrasrseRegistroExpositor.setOnClickListener(view -> registrarseComoExpositor());



        localidadesAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.LOCALIDADES);
        spinnerLocalidadRegistroUsuario.setAdapter(localidadesAdapter);

        interesesAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.INTERESES);
        spinnerInteresesRegistroUsuario.setAdapter(interesesAdapter);

    }

    public void cambiarAPaginaPrincipal() {
        PaginaInicio.intensionInicializacion = PaginaInicio.CUENTA;
        Intent miIntent = new Intent(this, PaginaInicio.class);
        startActivity(miIntent);
        finishAffinity();
    }
    public void registrarseComoExpositor(){
        VerificarInformacionRegistroExpositor(new View(this));
    }

    public void VerificarInformacionRegistroExpositor(View view) {

        boolean flag = true;
        String mensajeError = "";

        if(nombreRegistroExpositor.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado nombres validos\n";
            flag = false;
        }

        if(spinnerLocalidadRegistroUsuario.getText().toString().equals("") || localidadesAdapter.getPosition(spinnerLocalidadRegistroUsuario.getText().toString()) == -1) {
            mensajeError += "Seleccione una Localidad\n";
            flag = false;
        }
        if(spinnerInteresesRegistroUsuario.getText().toString().equals("") || interesesAdapter.getPosition(spinnerInteresesRegistroUsuario.getText().toString()) == -1) {
            mensajeError += "Seleccione un Interes\n";
            flag = false;
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correoRegistroExpositor.getText().toString());

        if(!mather.find()){
            mensajeError += "No ha ingresado un correo electronico valido\n";
            flag = false;
        }
        if(contrasenaRegistroExpositor.getText().toString().length() < 8){
            mensajeError += "Debe ingresar una contraseña de por lo menos 8 caracteres\n";
            flag = false;
        }
        if(contrasenaRegistroExpositor.getText().toString().contains(" ")){
            mensajeError += "La contraseña no puede contener espacios en blanco\n";
            flag = false;
        }
        if(!contrasenaRegistroExpositor.getText().toString().equals(confirmarContrasenaRegistroExpositor.getText().toString())){
            mensajeError += "Las contraseñas no coinciden\n";
            flag = false;
        }
        if(!contrasenaRegistroExpositor.getText().toString().equals(confirmarContrasenaRegistroExpositor.getText().toString())){
            mensajeError += "Las contraseñas no coinciden\n";
            flag = false;
        }

        if(flag)
            RegistrarExpositor(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();

    }

    public void RegistrarExpositor(View view) {
        String nombres = nombreRegistroExpositor.getText().toString().trim();

        String correoElectronicoR = correoRegistroExpositor.getText().toString();
        String contrasenaR = contrasenaRegistroExpositor.getText().toString();
        int localidad = localidadesAdapter.getPosition(spinnerLocalidadRegistroUsuario.getText().toString());
        int interes = interesesAdapter.getPosition(spinnerInteresesRegistroUsuario.getText().toString());

        Artista expositor = new Artista(Bocu.expositores.size(), nombres, correoElectronicoR.toLowerCase(), contrasenaR, interes, localidad);

        if(!verificarRepeticion()){

            Bocu.expositores.insert(expositor);
            new DbExpositor(this).agregarExpositor(nombres, correoElectronicoR.toLowerCase(), contrasenaR, localidad, interes);
            Toast.makeText(this, "Se ha registrado como expositor exitosamente.", Toast.LENGTH_SHORT).show();
            DbSesion dbSesion= new DbSesion(this);
            dbSesion.mantenerSesionIniciada(2, correoElectronicoR);
            cambiarAPaginaPrincipal();


        } else {

            Toast.makeText(this, "El correo electronico ingresado ya se encuentra registrado", Toast.LENGTH_SHORT).show();

        }

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

            repeticion = correo.equalsIgnoreCase(correoRegistroExpositor.getText().toString().trim());

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
