package com.example.datastructureproject_groupb.entidades.info_sesion;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.LinkedList;
import com.example.datastructureproject_groupb.entidades.pagina_inicio.PaginaInicio;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.db.DbSesion;
import com.example.datastructureproject_groupb.db.DbUsuariosComunes;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearCuentaUsuario extends AppCompatActivity {
    TextInputEditText edadRegistroUsuario,nombreRegistroUsuario, apellidoRegistroUsuario, correoRegistroUsuario, contrasenaRegistroUsuario, confirmarContrasenaRegistroUsuario;
    MaterialAutoCompleteTextView spinnerLocalidadRegistroUsuario,spinnerInteresesRegistroUsuario;
    Button cancelarRegistroUsuario, registrasrseRegistroUsuario;

    ArrayAdapter<String> localidadesAdapter, interesesAdapter;
    private LinearLayout layoutBotones;

    private TextInputLayout layoutNombre, layoutApellido, layoutEdad, layoutCorreoElectronico, layoutContrasena, layoutConfirmarContrasena, layoutLocalidad, layoutIntereses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

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

        nombreRegistroUsuario=findViewById(R.id.textViewNombreRegistroUsuario);
        apellidoRegistroUsuario=findViewById(R.id.textViewApellidoRegistroUsuario);
        correoRegistroUsuario=findViewById(R.id.textViewCorreoRegistroUsuario);
        confirmarContrasenaRegistroUsuario=findViewById(R.id.textViewConfirmarContrasenaRegistroUsuario);
        contrasenaRegistroUsuario=findViewById(R.id.textViewContrasenaRegistroUsuario);
        edadRegistroUsuario=findViewById(R.id.editTextEdadRegistroUsuario);
        spinnerInteresesRegistroUsuario=findViewById(R.id.spinnerInteresesRegistroUsuario);
        spinnerLocalidadRegistroUsuario=findViewById(R.id.spinnerLocalidadRegistroUsuario);
        cancelarRegistroUsuario=findViewById(R.id.botonCancelarRegistroUsuario);
        registrasrseRegistroUsuario=findViewById(R.id.botonRegistratseRegistroUsuario);

        layoutNombre = findViewById(R.id.layoutNombre);
        layoutApellido = findViewById(R.id.layoutApellido);
        layoutEdad = findViewById(R.id.layoutEdad);
        layoutCorreoElectronico = findViewById(R.id.layoutCorreoElectronico);
        layoutContrasena = findViewById(R.id.layoutContrasena);
        layoutConfirmarContrasena = findViewById(R.id.layoutConfirmarContrasena);
        layoutLocalidad = findViewById(R.id.layoutLocalidad);
        layoutIntereses = findViewById(R.id.layoutIntereses);

        // Deshabilita el setErrorEnable después de un intento de filtrado fallido
        deshabilitarSetError(nombreRegistroUsuario, layoutNombre);
        deshabilitarSetError(apellidoRegistroUsuario, layoutApellido);
        deshabilitarSetError(edadRegistroUsuario, layoutEdad);
        deshabilitarSetError(correoRegistroUsuario, layoutCorreoElectronico);
        deshabilitarSetError(confirmarContrasenaRegistroUsuario, layoutConfirmarContrasena);
        deshabilitarSetError(spinnerLocalidadRegistroUsuario, layoutLocalidad);
        deshabilitarSetError(spinnerInteresesRegistroUsuario, layoutIntereses);

        contrasenaRegistroUsuario.addTextChangedListener(new TextWatcher() {
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

        localidadesAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.LOCALIDADES);
        spinnerLocalidadRegistroUsuario.setAdapter(localidadesAdapter);

        interesesAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.INTERESES);
        spinnerInteresesRegistroUsuario.setAdapter(interesesAdapter);


        cancelarRegistroUsuario.setOnClickListener(view -> cambiarAPaginaPrincipal());
        registrasrseRegistroUsuario.setOnClickListener(view -> registrarseComoUsuario());

    }

    public void cambiarAPaginaPrincipal() {
        PaginaInicio.intensionInicializacion = PaginaInicio.CUENTA;
        Intent miIntent = new Intent(this, PaginaInicio.class);
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

        if(nombreRegistroUsuario.getText().toString().trim().equals("")) {
            layoutNombre.setError("Ingrese nombres validos");
            flag = false;
        }
        if(apellidoRegistroUsuario.getText().toString().trim().equals("")) {
            layoutApellido.setError("Ingrese apellidos validos");
            flag = false;
        }

        if(edadRegistroUsuario.getText().toString().trim().equals("") || Integer.parseInt(edadRegistroUsuario.getText().toString().trim()) > 150) {
            layoutEdad.setError("Ingrese una edad valida");
            flag = false;
        }

        if(spinnerLocalidadRegistroUsuario.getText().toString().trim().equals("") || localidadesAdapter.getPosition(spinnerLocalidadRegistroUsuario.getText().toString()) == -1) {
            layoutLocalidad.setError("Seleccione una localidad");
            flag = false;
        }
        if(spinnerInteresesRegistroUsuario.getText().toString().trim().equals("") || interesesAdapter.getPosition(spinnerInteresesRegistroUsuario.getText().toString()) == -1) {
            layoutIntereses.setError("Seleccione un interes");
            flag = false;
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correoRegistroUsuario.getText().toString());

        if(!mather.find()){
            layoutCorreoElectronico.setError("Ingrese un correo electronico valido");
            flag = false;
        }
        if(contrasenaRegistroUsuario.getText().toString().contains(" ")){
            layoutContrasena.setError("La contraseña no puede contener espacios en blanco");
            flag = false;
        }else if(contrasenaRegistroUsuario.getText().toString().length() < 8){
            layoutContrasena.setError("La contraseña debe tener por lo menos 8 caracteres.");
            flag = false;
        }
        if(!contrasenaRegistroUsuario.getText().toString().equals(confirmarContrasenaRegistroUsuario.getText().toString())){
            layoutConfirmarContrasena.setError("Las contraseñas no coinciden");
            flag = false;
        }

        if(flag)
            Registrar(view);

    }

    public void Registrar(View view){
        String nombres = this.nombreRegistroUsuario.getText().toString().trim();
        String apellidos = this.apellidoRegistroUsuario.getText().toString().trim();
        int edad = Integer.parseInt(this.edadRegistroUsuario.getText().toString());
        String correoElectronicoR = this.correoRegistroUsuario.getText().toString();
        String contrasenaR = this.contrasenaRegistroUsuario.getText().toString();
        int localidad = localidadesAdapter.getPosition(spinnerLocalidadRegistroUsuario.getText().toString());
        int interes = interesesAdapter.getPosition(spinnerInteresesRegistroUsuario.getText().toString());

        UsuarioComun usuarioComun = new UsuarioComun(Bocu.usuariosComunes.size(), nombres, apellidos, edad, correoElectronicoR.toLowerCase(), contrasenaR, localidad, interes);


        if(!verificarRepeticion()){

            Bocu.usuariosComunes.insert(usuarioComun);
            new DbUsuariosComunes(this).agregarUsuario(nombres, apellidos, edad, correoElectronicoR.toLowerCase(), contrasenaR, localidad, interes);
            Toast.makeText(this, "Se ha registrado como usuario exitosamente.", Toast.LENGTH_SHORT).show();
            DbSesion dbSesion= new DbSesion(this);
            dbSesion.mantenerSesionIniciada(1, correoElectronicoR);

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

            repeticion = correo.equalsIgnoreCase(correoRegistroUsuario.getText().toString().trim());

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
