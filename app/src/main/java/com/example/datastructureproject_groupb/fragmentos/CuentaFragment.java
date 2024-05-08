package com.example.datastructureproject_groupb.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.CrearCuentaExpositor;
import com.example.datastructureproject_groupb.CrearCuentaUsuario;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.LinkedList;
import com.example.datastructureproject_groupb.PaginaInicio;
import com.example.datastructureproject_groupb.R;
import com.example.datastructureproject_groupb.db.DbExpositor;
import com.example.datastructureproject_groupb.db.DbSesion;
import com.example.datastructureproject_groupb.db.DbUsuariosComunes;
import com.example.datastructureproject_groupb.entidades.Artista;
import com.example.datastructureproject_groupb.entidades.UsuarioComun;
import com.example.datastructureproject_groupb.entidades.UsuarioRegistrado;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CuentaFragment extends Fragment {

    Button botonAcceder, botonCrearCuentaUsuario, botonCrearCuentaExpositor, botonEditar, botonCerrarSesion, botonEliminar, botonGuardar, botonCancelar;
    TextInputEditText nombreAcceder, correoElectronicoAcceder, contrasenaAcceder, apellidoAcceder, edadAcceder;
    MaterialAutoCompleteTextView spinnerLocalidad, spinnerIntereses;
    ArrayAdapter<String> localidadesAdapter;
    ArrayAdapter<String> interesesAdapter;
    LinearLayout linearLayoutBotonesCuenta, linearLayoutBotonesEdicion;
    TextInputLayout layoutNombre, layoutCorreoElectronico, layoutContrasena, layoutConfirmarContrasena, layoutTipoEvento, layoutLocalidad, layoutIntereses, layoutApellido, layoutEdad;


    public CuentaFragment() {
    }

    public static CuentaFragment newInstance() {
        CuentaFragment fragment = new CuentaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root;

        if (Bocu.estadoUsuario == Bocu.ARTISTA)
            root = establecerContenidoExpositor(inflater, container);
        else if (Bocu.estadoUsuario == Bocu.USUARIO_COMUN)
            root = establecerContenidoUsuarioComun(inflater, container);
        else
            root = establecerContenidoUsuarioNoRegistrado(inflater, container);

        return root;
    }

    private View establecerContenidoExpositor(LayoutInflater inflater, ViewGroup container){
        View root = inflater.inflate(R.layout.fragment_cuenta_expositor, container, false);

        nombreAcceder = root.findViewById(R.id.editTextNombre);
        correoElectronicoAcceder = root.findViewById(R.id.editTextCorreo);
        contrasenaAcceder = root.findViewById(R.id.editTextContrasena);

        linearLayoutBotonesCuenta = root.findViewById(R.id.layoutBotonesCuenta);
        linearLayoutBotonesEdicion = root.findViewById(R.id.layoutBotonesEdicion);

        spinnerLocalidad = root.findViewById(R.id.spinnerLocalidad);
        spinnerIntereses = root.findViewById(R.id.spinnerInteresesRegistroExpositor);

        botonEditar= root.findViewById(R.id.BotonEditarExpositor);
        botonCerrarSesion= root.findViewById(R.id.BotonCerrarSesionExpositor);
        botonEliminar= root.findViewById(R.id.BotonEliminarCuentaExpositor);

        botonCancelar = root.findViewById(R.id.buttonCancelarExpositor);
        botonGuardar = root.findViewById(R.id.buttonGuardarExpositor);

        layoutNombre = root.findViewById(R.id.layoutNombre);
        layoutCorreoElectronico = root.findViewById(R.id.layoutCorreoElectronico);
        layoutContrasena = root.findViewById(R.id.layoutContrasena);
        layoutTipoEvento = root.findViewById(R.id.layoutIntereses);
        layoutLocalidad = root.findViewById(R.id.layoutLocalidad);

        nombreAcceder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutNombre.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        correoElectronicoAcceder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutCorreoElectronico.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contrasenaAcceder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutContrasena.setErrorEnabled(false);

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



        spinnerLocalidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutLocalidad.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spinnerIntereses.addTextChangedListener(new TextWatcher() {
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



        KeyboardVisibilityEvent.setEventListener(getActivity(), isOpen -> {
            if(isOpen) {

                LinearLayout.LayoutParams nuevoParametro = (LinearLayout.LayoutParams) linearLayoutBotonesEdicion.getLayoutParams();
                nuevoParametro.width = 0;
                nuevoParametro.weight = 0;
                linearLayoutBotonesEdicion.setLayoutParams(nuevoParametro);

            } else {

                LinearLayout.LayoutParams nuevoParametro = (LinearLayout.LayoutParams) linearLayoutBotonesEdicion.getLayoutParams();

                nuevoParametro.width = ViewGroup.LayoutParams.MATCH_PARENT;
                nuevoParametro.weight = 1.9f;

                linearLayoutBotonesEdicion.setLayoutParams(nuevoParametro);

            }
        });

        nombreAcceder.setText(((Artista)Bocu.usuario).getNombreArtista());
        correoElectronicoAcceder.setText(((Artista)Bocu.usuario).getCorreoElectronico());
        contrasenaAcceder.setText(((Artista)Bocu.usuario).getContrasena());

        nombreAcceder.setEnabled(false);
        correoElectronicoAcceder.setEnabled(false);
        contrasenaAcceder.setEnabled(false);

        DbSesion dbSesion = new DbSesion(getContext());
        botonCerrarSesion.setOnClickListener(view -> dbSesion.cerrarSesion());
        botonEliminar.setOnClickListener(view->eliminarCuentaExpositor((Artista)Bocu.usuario));

        localidadesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Bocu.LOCALIDADES) {
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

        interesesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Bocu.INTERESES) {
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

        botonEditar.setOnClickListener(view -> {

            nombreAcceder.setEnabled(true);
            correoElectronicoAcceder.setEnabled(true);
            contrasenaAcceder.setEnabled(true);
            spinnerLocalidad.setEnabled(true);
            spinnerIntereses.setEnabled(true);

            LinearLayout.LayoutParams nuevoParametroEdicion = (LinearLayout.LayoutParams) linearLayoutBotonesEdicion.getLayoutParams();
            nuevoParametroEdicion.weight = 1.9f;
            linearLayoutBotonesEdicion.setLayoutParams(nuevoParametroEdicion);

            LinearLayout.LayoutParams nuevoParametroCuenta = (LinearLayout.LayoutParams) linearLayoutBotonesCuenta.getLayoutParams();
            nuevoParametroCuenta.weight = 0;
            linearLayoutBotonesCuenta.setLayoutParams(nuevoParametroCuenta);

        });


        botonCancelar.setOnClickListener(view -> {

            LinearLayout.LayoutParams nuevoParametroEdicion = (LinearLayout.LayoutParams) linearLayoutBotonesEdicion.getLayoutParams();
            nuevoParametroEdicion.weight = 0;
            linearLayoutBotonesEdicion.setLayoutParams(nuevoParametroEdicion);

            LinearLayout.LayoutParams nuevoParametroCuenta = (LinearLayout.LayoutParams) linearLayoutBotonesCuenta.getLayoutParams();
            nuevoParametroCuenta.weight = 4.4f;
            linearLayoutBotonesCuenta.setLayoutParams(nuevoParametroCuenta);

            nombreAcceder.setEnabled(false);
            correoElectronicoAcceder.setEnabled(false);
            contrasenaAcceder.setEnabled(false);
            spinnerIntereses.setEnabled(false);
            spinnerLocalidad.setEnabled(false);

        });
        String correoInicial = correoElectronicoAcceder.getText().toString().trim();


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean flag = true;
                String mensajeError = "";

                if(nombreAcceder.getText().toString().trim().equals("")) {
                    layoutNombre.setError("Ingrese nombres validos");
                    flag = false;
                }


                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

                Matcher mather = pattern.matcher(correoElectronicoAcceder.getText().toString());

                if(!mather.find()){
                    layoutCorreoElectronico.setError("Ingrese un correo electronico valido");
                    flag = false;
                }
                if(contrasenaAcceder.getText().toString().length() < 8){
                    layoutContrasena.setError("La contraseña debe tener por lo menos 8 caracteres.");
                    flag = false;
                }
                if(contrasenaAcceder.getText().toString().contains(" ")){
                    layoutContrasena.setError("La contraseña no puede contener espacios en blanco");
                    flag = false;
                }

                if (flag){
                    String nombres = nombreAcceder.getText().toString().trim();
                    String correoElectronicoR = correoElectronicoAcceder.getText().toString();
                    String contrasenaR = contrasenaAcceder.getText().toString();
                    String localidadSeleccionada = spinnerLocalidad.getText().toString();
                    String interesSeleccionado = spinnerIntereses.getText().toString();
                    int localidad = localidadesAdapter.getPosition(localidadSeleccionada);
                    int interes = interesesAdapter.getPosition(interesSeleccionado);

                    DbExpositor dbExpositor = new DbExpositor(getContext());
                    dbExpositor.actualizarExpositor(correoInicial, nombres, correoElectronicoR, contrasenaR,localidad, interes);

                    Artista artista = (Artista) Bocu.usuario;

                    artista.setNombreArtista(nombres);
                    artista.setCorreoElectronico(correoElectronicoR);
                    artista.setContrasena(contrasenaR);
                    artista.setLocalidad(localidad);
                    artista.setTipoDeEvento(interes);

                    Toast.makeText(getContext(), "Los cambios se guardaron correctamente", Toast.LENGTH_SHORT).show();

                    LinearLayout.LayoutParams nuevoParametroEdicion = (LinearLayout.LayoutParams) linearLayoutBotonesEdicion.getLayoutParams();
                    nuevoParametroEdicion.weight = 0;
                    linearLayoutBotonesEdicion.setLayoutParams(nuevoParametroEdicion);

                    LinearLayout.LayoutParams nuevoParametroCuenta = (LinearLayout.LayoutParams) linearLayoutBotonesCuenta.getLayoutParams();
                    nuevoParametroCuenta.weight = 4.4f;
                    linearLayoutBotonesCuenta.setLayoutParams(nuevoParametroCuenta);

                    nombreAcceder.setEnabled(false);
                    correoElectronicoAcceder.setEnabled(false);
                    contrasenaAcceder.setEnabled(false);
                    spinnerIntereses.setEnabled(false);
                    spinnerLocalidad.setEnabled(false);

                } else {
                }

            }
        });

        String localidadSeleccionada = Bocu.LOCALIDADES[((Artista)Bocu.usuario).getLocalidad()];
        ArrayAdapter<String> localidadesAdapter = (ArrayAdapter<String>) spinnerLocalidad.getAdapter();
        int posicionLocalidad = localidadesAdapter.getPosition(localidadSeleccionada);
        spinnerLocalidad.setText(localidadSeleccionada, false);

        String interesSeleccionado = Bocu.INTERESES[((Artista)Bocu.usuario).getTipoDeEvento()];
        ArrayAdapter<String> interesesAdapter = (ArrayAdapter<String>) spinnerIntereses.getAdapter();
        int posicionIntereses = interesesAdapter.getPosition(interesSeleccionado);
        spinnerIntereses.setText(interesSeleccionado, false);

        spinnerIntereses.setEnabled(false);
        spinnerLocalidad.setEnabled(false);

        return root;

    }
    public void eliminarCuentaExpositor(Artista artista){
        /*
        for(int i=0; i<Bocu.expositores.size();i++){
            if (Bocu.expositores.get(i)==artista){
                Bocu.expositores.remove(i);
            }
        }*/
        DbExpositor dbExpositor = new DbExpositor(getContext());
        dbExpositor.eliminarExpositor(artista.getCorreoElectronico());
        DbSesion dbSesion = new DbSesion(getContext());
        dbSesion.cerrarSesion();

    }
    public void eliminarCuentaUsuario(UsuarioRegistrado usuarioRegistrado){
        //RECORDARRRRR ELIMINAR CUENTA
        /*
        for(int i=0; i<Bocu.usuariosComunes.size();i++){
            if (Bocu.usuariosComunes.get(i)==usuarioRegistrado){
                Bocu.usuariosComunes.remove(i);
            }
        }*/
        DbUsuariosComunes dbUsuariosComunes = new DbUsuariosComunes(getContext());
        dbUsuariosComunes.eliminarUsuario(usuarioRegistrado.getCorreoElectronico());
        DbSesion dbSesion = new DbSesion(getContext());
        dbSesion.cerrarSesion();

    }

    private View establecerContenidoUsuarioComun(LayoutInflater inflater, ViewGroup container){
        View root = inflater.inflate(R.layout.fragment_cuenta_usuario_comun, container, false);

        nombreAcceder = root.findViewById(R.id.editTextNombre);
        correoElectronicoAcceder = root.findViewById(R.id.editTextCorreo);
        contrasenaAcceder = root.findViewById(R.id.editTextContrasena);
        apellidoAcceder = root.findViewById(R.id.editTextApellido);
        edadAcceder = root.findViewById(R.id.editTextEdad);

        linearLayoutBotonesCuenta = root.findViewById(R.id.layoutBotonesCuenta);
        linearLayoutBotonesEdicion = root.findViewById(R.id.layoutBotonesEdicion);

        spinnerLocalidad = root.findViewById(R.id.spinnerLocalidad);
        spinnerIntereses = root.findViewById(R.id.spinnerIntereses);

        botonEditar= root.findViewById(R.id.BotonEditarUsuario);
        botonCerrarSesion= root.findViewById(R.id.BotonCerrarSesion);
        botonEliminar= root.findViewById(R.id.BotonEliminarCuenta);

        botonCancelar= root.findViewById(R.id.buttonCancelarVistaEditarUsuario);
        botonGuardar= root.findViewById(R.id.buttonGuardarVistaEditarUsuario);

        layoutNombre = root.findViewById(R.id.layoutNombre);
        layoutApellido = root.findViewById(R.id.layoutApellido);
        layoutEdad = root.findViewById(R.id.layoutEdad);
        layoutCorreoElectronico = root.findViewById(R.id.layoutCorreoElectronico);
        layoutContrasena = root.findViewById(R.id.layoutContrasena);
        layoutLocalidad = root.findViewById(R.id.layoutLocalidad);
        layoutIntereses = root.findViewById(R.id.layoutIntereses);
        nombreAcceder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutNombre.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        apellidoAcceder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutApellido.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edadAcceder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutEdad.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        correoElectronicoAcceder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutCorreoElectronico.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contrasenaAcceder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutContrasena.setErrorEnabled(false);

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



        spinnerLocalidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutLocalidad.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spinnerIntereses.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                layoutIntereses.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        KeyboardVisibilityEvent.setEventListener(getActivity(), isOpen -> {
            if(isOpen) {

                LinearLayout.LayoutParams nuevoParametro = (LinearLayout.LayoutParams) linearLayoutBotonesEdicion.getLayoutParams();
                nuevoParametro.width = 0;
                nuevoParametro.weight = 0;
                linearLayoutBotonesEdicion.setLayoutParams(nuevoParametro);

            } else {

                LinearLayout.LayoutParams nuevoParametro = (LinearLayout.LayoutParams) linearLayoutBotonesEdicion.getLayoutParams();

                nuevoParametro.width = ViewGroup.LayoutParams.MATCH_PARENT;
                nuevoParametro.weight = 1.9f;

                linearLayoutBotonesEdicion.setLayoutParams(nuevoParametro);

            }
        });

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

        DbSesion dbSesion=new DbSesion(getContext());
        botonCerrarSesion.setOnClickListener(view -> dbSesion.cerrarSesion());
        botonEliminar.setOnClickListener(view->eliminarCuentaUsuario((UsuarioComun)Bocu.usuario));

        localidadesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Bocu.LOCALIDADES) {
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

        interesesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Bocu.INTERESES) {
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

        botonEditar.setOnClickListener(view -> {

            try {
                nombreAcceder.setEnabled(true);
                correoElectronicoAcceder.setEnabled(true);
                contrasenaAcceder.setEnabled(true);
                apellidoAcceder.setEnabled(true);
                edadAcceder.setEnabled(true);

                LinearLayout.LayoutParams nuevoParametroEdicion = (LinearLayout.LayoutParams) linearLayoutBotonesEdicion.getLayoutParams();
                nuevoParametroEdicion.weight = 1.9f;
                linearLayoutBotonesEdicion.setLayoutParams(nuevoParametroEdicion);

                LinearLayout.LayoutParams nuevoParametroCuenta = (LinearLayout.LayoutParams) linearLayoutBotonesCuenta.getLayoutParams();
                nuevoParametroCuenta.weight = 0;
                linearLayoutBotonesCuenta.setLayoutParams(nuevoParametroCuenta);
            }catch (Exception e){
                Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        botonCancelar.setOnClickListener(view -> {

            nombreAcceder.setEnabled(false);
            correoElectronicoAcceder.setEnabled(false);
            contrasenaAcceder.setEnabled(false);
            apellidoAcceder.setEnabled(false);
            edadAcceder.setEnabled(false);

            LinearLayout.LayoutParams nuevoParametroEdicion = (LinearLayout.LayoutParams) linearLayoutBotonesEdicion.getLayoutParams();
            nuevoParametroEdicion.weight = 0;
            linearLayoutBotonesEdicion.setLayoutParams(nuevoParametroEdicion);

            LinearLayout.LayoutParams nuevoParametroCuenta = (LinearLayout.LayoutParams) linearLayoutBotonesCuenta.getLayoutParams();
            nuevoParametroCuenta.weight = 4.4f;
            linearLayoutBotonesCuenta.setLayoutParams(nuevoParametroCuenta);

        });
        String correoInicial = correoElectronicoAcceder.getText().toString().trim();


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean flag = true;
                String mensajeError = "";

                if(nombreAcceder.getText().toString().trim().equals("")) {
                    layoutNombre.setError("Ingrese nombres validos");
                    flag = false;
                }
                if(apellidoAcceder.getText().toString().trim().equals("")) {
                    layoutApellido.setError("Ingrese apellidos validos");
                    flag = false;
                }
                String edadString = edadAcceder.getText().toString().trim();
                if (edadString.isEmpty()) {
                    flag=false;
                    layoutEdad.setError("Ingrese una edad valida");
                }



                if(edadAcceder.getText().toString().trim().equals("") || Integer.parseInt(edadAcceder.getText().toString().trim()) > 150) {
                    layoutEdad.setError("Ingrese una edad valida");
                    flag = false;
                }

                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

                Matcher mather = pattern.matcher(correoElectronicoAcceder.getText().toString());

                if(!mather.find()){
                    layoutCorreoElectronico.setError("Ingrese un correo electronico valido");
                    flag = false;
                }
                if(contrasenaAcceder.getText().toString().length() < 8){
                    layoutContrasena.setError("La contraseña debe tener por lo menos 8 caracteres.");
                    flag = false;
                }
                if(contrasenaAcceder.getText().toString().contains(" ")){
                    layoutContrasena.setError("La contraseña no puede contener espacios en blanco");
                    flag = false;
                }

                if (flag){
                    String nombres = nombreAcceder.getText().toString().trim();
                    String apellidos = apellidoAcceder.getText().toString().trim();
                    int edad = Integer.parseInt(edadAcceder.getText().toString());
                    String correoElectronicoR = correoElectronicoAcceder.getText().toString();
                    String contrasenaR = contrasenaAcceder.getText().toString();
                    String localidadSeleccionada = spinnerLocalidad.getText().toString();
                    String interesSeleccionado = spinnerIntereses.getText().toString();
                    int localidad = localidadesAdapter.getPosition(localidadSeleccionada);
                    int interes = interesesAdapter.getPosition(interesSeleccionado);

                    DbUsuariosComunes dbUsuariosComunes = new DbUsuariosComunes(getContext());
                    dbUsuariosComunes.actualizarUsuario(correoInicial, nombres, apellidos, edad, correoElectronicoR, contrasenaR,localidad, interes);
                    UsuarioComun usuarioComun = (UsuarioComun) Bocu.usuario;
                    usuarioComun.setNombres(nombres);
                    usuarioComun.setApellidos(apellidos);
                    usuarioComun.setEdad(edad);
                    usuarioComun.setCorreoElectronico(correoElectronicoR);
                    usuarioComun.setContrasena(contrasenaR);
                    usuarioComun.setLocalidad(localidad);
                    usuarioComun.setIntereses(interes);

                    Toast.makeText(getContext(), "Los cambios se guardaron correctamente", Toast.LENGTH_SHORT).show();


                    nombreAcceder.setEnabled(false);
                    correoElectronicoAcceder.setEnabled(false);
                    contrasenaAcceder.setEnabled(false);
                    apellidoAcceder.setEnabled(false);
                    edadAcceder.setEnabled(false);

                    LinearLayout.LayoutParams nuevoParametroEdicion = (LinearLayout.LayoutParams) linearLayoutBotonesEdicion.getLayoutParams();
                    nuevoParametroEdicion.weight = 0;
                    linearLayoutBotonesEdicion.setLayoutParams(nuevoParametroEdicion);

                    LinearLayout.LayoutParams nuevoParametroCuenta = (LinearLayout.LayoutParams) linearLayoutBotonesCuenta.getLayoutParams();
                    nuevoParametroCuenta.weight = 4.4f;
                    linearLayoutBotonesCuenta.setLayoutParams(nuevoParametroCuenta);
                } else {
                }

            }
        });

        String localidadSeleccionada = Bocu.LOCALIDADES[((UsuarioComun)Bocu.usuario).getLocalidad()];
        ArrayAdapter<String> localidadesAdapter = (ArrayAdapter<String>) spinnerLocalidad.getAdapter();
        int posicionLocalidad = localidadesAdapter.getPosition(localidadSeleccionada);
        spinnerLocalidad.setText(localidadSeleccionada, false);

        String interesSeleccionado = Bocu.INTERESES[((UsuarioComun)Bocu.usuario).getIntereses()];
        ArrayAdapter<String> interesesAdapter = (ArrayAdapter<String>) spinnerIntereses.getAdapter();
        int posicionIntereses = interesesAdapter.getPosition(interesSeleccionado);
        spinnerIntereses.setText(interesSeleccionado, false);

        spinnerIntereses.setEnabled(false);
        spinnerLocalidad.setEnabled(false);

        return root;

    }


    private View establecerContenidoUsuarioNoRegistrado(LayoutInflater inflater, ViewGroup container){
        View root = inflater.inflate(R.layout.fragment_cuenta_usuario_no_registrado, container, false);

        if(Bocu.expositores == null){
            Bocu.expositores = new DbExpositor(getActivity()).obtenerExpositores();
        }
        if(Bocu.usuariosComunes == null){
            Bocu.usuariosComunes = new DbUsuariosComunes(getActivity()).obtenerUsuariosComunes();
        }

        botonAcceder = root.findViewById(R.id.botonAccederCuenta);
        botonCrearCuentaUsuario = root.findViewById(R.id.botonCrearCuentaUsuario);
        botonCrearCuentaExpositor = root.findViewById(R.id.botonCrearCuentaExpositor);

        botonAcceder.setOnClickListener(i -> VerificarInformacionAcceso());
        correoElectronicoAcceder = root.findViewById(R.id.editTextCorreo);
        contrasenaAcceder = root.findViewById(R.id.editTextContrasena);


        botonCrearCuentaUsuario.setOnClickListener(view -> cambiarARegistroUsuario());
        botonCrearCuentaExpositor.setOnClickListener(view -> cambiarARegistroExpositor());

        return root;

    }


    public void acceder(UsuarioRegistrado usuario, int tipoUsuario) {

        Bocu.usuario = usuario;
        Bocu.estadoUsuario = tipoUsuario;
        if (getContext() != null) {
            if (tipoUsuario == Bocu.USUARIO_COMUN) {
                Toast.makeText(getContext(), "Ingreso correctamente como Usuario", Toast.LENGTH_SHORT).show();
            } else {
                establecerEventosExpositor();
                Toast.makeText(getContext(), "Ingreso correctamente como Artista", Toast.LENGTH_SHORT).show();
            }
        }
        cambiarAPaginaPrincipal();

    }

    public void cambiarAPaginaPrincipal(){
        if (getContext() != null) {
            PaginaInicio.intensionInicializacion = PaginaInicio.PAGINA_PRINCIPAL;
            FragmentActivity activity = getActivity();
            Intent miIntent = new Intent(activity, PaginaInicio.class);
            activity.startActivity(miIntent);
            activity.finishAffinity();
        } else {

            Log.e("Error", "El contexto es nulo en cambiarAPaginaPrincipal()");
        }
    }

    public void revisar(){

        int opcion = verificarExistencia();
        if (opcion > -1) {
            if (opcion == 0) {
                UsuarioComun usuario = verUsuarioComun(correoElectronicoAcceder.getText().toString().toLowerCase());
                if (contrasenaAcceder.getText().toString().equals(usuario.getContrasena())) {
                    acceder(usuario, Bocu.USUARIO_COMUN);
                    DbSesion dbSesion= new DbSesion(getContext());
                    dbSesion.mantenerSesionIniciada(1, correoElectronicoAcceder.getText().toString().toLowerCase());
                } else {
                    Toast.makeText(getContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            } else if (opcion == 1) {
                Artista artista = verExpositor(correoElectronicoAcceder.getText().toString().toLowerCase());
                if (contrasenaAcceder.getText().toString().equals(artista.getContrasena())) {
                    acceder(artista, Bocu.ARTISTA);
                    DbSesion dbSesion= new DbSesion(getContext());
                    dbSesion.mantenerSesionIniciada(2, correoElectronicoAcceder.getText().toString().toLowerCase());
                } else {
                    Toast.makeText(getContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }

        } else {

            Toast.makeText(getContext(), "El correo electronico ingresado no se encuentra registrado.", Toast.LENGTH_SHORT).show();

        }

    }

    public void VerificarInformacionAcceso () {

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
            revisar();
        else
            Toast.makeText(getContext(), mensajeError, Toast.LENGTH_SHORT).show();
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

    public void cambiarARegistroUsuario() {
        Intent miIntent = new Intent(getActivity(), CrearCuentaUsuario.class);
        startActivity(miIntent);
        getActivity().finishAffinity();


    }
    public void cambiarARegistroExpositor() {
        Intent miIntent = new Intent(getActivity(), CrearCuentaExpositor.class);
        startActivity(miIntent);
        getActivity().finishAffinity();


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

    public static void establecerEventosExpositor(){
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