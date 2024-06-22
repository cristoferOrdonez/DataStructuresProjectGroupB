package com.example.datastructureproject_groupb.entidades.artista;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.LinkedList;
import com.example.datastructureproject_groupb.entidades.pagina_inicio.PaginaInicio;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.db.DbExpositor;
import com.example.datastructureproject_groupb.db.DbSesion;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearCuentaExpositor extends AppCompatActivity {
    private TextInputEditText nombreRegistroExpositor, correoRegistroExpositor, confirmarContrasenaRegistroExpositor, contrasenaRegistroExpositor;
    private MaterialAutoCompleteTextView spinnerLocalidadRegistroUsuario,spinnerInteresesRegistroUsuario;
    private Button cancelarRegistroExpositor, registrasrseRegistroExpositor;
    private ArrayAdapter<String> interesesAdapter, localidadesAdapter;
    private LinearLayout layoutBotones;
    private TextInputLayout layoutNombre, layoutCorreoElectronico, layoutContrasena, layoutConfirmarContrasena, layoutTipoEvento, layoutLocalidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_expositor);

        layoutBotones = findViewById(R.id.layoutBotones);

        KeyboardVisibilityEvent.setEventListener(this, isOpen -> {
            if(isOpen) {

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

        nombreRegistroExpositor =findViewById(R.id.textViewNombre);
        correoRegistroExpositor =findViewById(R.id.textViewCorreoRegistroExpositor);
        confirmarContrasenaRegistroExpositor =findViewById(R.id.textViewConfirmarContrasenaRegistroExpositor);
        contrasenaRegistroExpositor =findViewById(R.id.textViewContrasenaRegistroExpositor);
        spinnerInteresesRegistroUsuario=findViewById(R.id.spinnerInteresesRegistroExpositor);
        spinnerLocalidadRegistroUsuario=findViewById(R.id.spinnerLocalidadRegistroExpositor);
        cancelarRegistroExpositor =findViewById(R.id.botonCancelarRegistroExpositor);
        registrasrseRegistroExpositor =findViewById(R.id.botonRegistratseRegistroExpositor);

        layoutNombre = findViewById(R.id.layoutNombre);
        layoutCorreoElectronico = findViewById(R.id.layoutCorreoElectronico);
        layoutContrasena = findViewById(R.id.layoutContrasena);
        layoutConfirmarContrasena = findViewById(R.id.layoutConfirmarContrasena);
        layoutTipoEvento = findViewById(R.id.layoutTipoEvento);
        layoutLocalidad = findViewById(R.id.layoutLocalidad);

        // Deshabilita el setErrorEnable después de un intento de filtrado fallido
        deshabilitarSetError(nombreRegistroExpositor, layoutNombre);
        deshabilitarSetError(correoRegistroExpositor, layoutCorreoElectronico);
        deshabilitarSetError(confirmarContrasenaRegistroExpositor, layoutConfirmarContrasena);
        deshabilitarSetError(spinnerLocalidadRegistroUsuario, layoutLocalidad);
        deshabilitarSetError(spinnerInteresesRegistroUsuario, layoutTipoEvento);

        contrasenaRegistroExpositor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                layoutContrasena.setErrorEnabled(false);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length() == 0)
                    layoutContrasena.setHelperTextEnabled(false);
                else if(s.toString().length() < 8)
                    layoutContrasena.setHelperText("Contraseña debil");
                else
                    layoutContrasena.setHelperText("Contraseña fuerte");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

        if(nombreRegistroExpositor.getText().toString().trim().equals("")) {
            layoutNombre.setError("Ingrese nombres validos");
            flag = false;
        }

        if(spinnerLocalidadRegistroUsuario.getText().toString().equals("") || localidadesAdapter.getPosition(spinnerLocalidadRegistroUsuario.getText().toString()) == -1) {
            layoutLocalidad.setError("Seleccione una localidad");
            flag = false;
        }
        if(spinnerInteresesRegistroUsuario.getText().toString().equals("") || interesesAdapter.getPosition(spinnerInteresesRegistroUsuario.getText().toString()) == -1) {
            layoutTipoEvento.setError("Seleccione un tipo de evento");
            flag = false;
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correoRegistroExpositor.getText().toString());

        if(!mather.find()){
            layoutCorreoElectronico.setError("Ingrese un correo electronico valido");
            flag = false;
        }

        if(contrasenaRegistroExpositor.getText().toString().contains(" ")){
            layoutContrasena.setError("La contraseña no puede contener espacios en blanco");
            flag = false;
        }else if(contrasenaRegistroExpositor.getText().toString().length() < 8){
            layoutContrasena.setError("La contraseña debe tener por lo menos 8 caracteres.");
            flag = false;
        }
        if(!contrasenaRegistroExpositor.getText().toString().equals(confirmarContrasenaRegistroExpositor.getText().toString())){
            layoutConfirmarContrasena.setError("Las contraseñas no coinciden");
            flag = false;
        }

        if(flag)
            RegistrarExpositor(view);

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

            layoutCorreoElectronico.setError("El correo electronico ya se encuentra en uso");

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

    private void deshabilitarSetError (TextInputEditText textInputEditText, TextInputLayout textInputLayout){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                textInputLayout.setErrorEnabled(false);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No es necesario
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No es necesario
            }
        });
    }

    private void deshabilitarSetError (MaterialAutoCompleteTextView materialAutoCompleteTextView, TextInputLayout textInputLayout){
        materialAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                textInputLayout.setErrorEnabled(false);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No es necesario
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No es necesario
            }
        });
    }
}
