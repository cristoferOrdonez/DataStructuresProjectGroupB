package com.example.datastructureproject_groupb.fragmentos;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CuentaFragment extends Fragment {

    Button botonAcceder, botonCrearCuentaUsuario, botonCrearCuentaExpositor, botonEditar, botonCerrarSesion, botonEliminar, botonGuardar, botonCancelar;
    EditText nombreAcceder, correoElectronicoAcceder, contrasenaAcceder, apellidoAcceder, edadAcceder;
    Spinner spinnerLocalidad, spinnerIntereses;
    ArrayAdapter<String> localidadesAdapter;
    ArrayAdapter<String> interesesAdapter;

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
        final View[] root = {inflater.inflate(R.layout.fragment_cuenta_expositor, container, false)};

        nombreAcceder = root[0].findViewById(R.id.editTextNombre);
        correoElectronicoAcceder = root[0].findViewById(R.id.editTextCorreo);
        contrasenaAcceder = root[0].findViewById(R.id.editTextContrasena);

        nombreAcceder.setText(((Artista)Bocu.usuario).getNombreArtista());
        correoElectronicoAcceder.setText(((Artista)Bocu.usuario).getCorreoElectronico());
        contrasenaAcceder.setText(((Artista)Bocu.usuario).getContrasena());

        nombreAcceder.setEnabled(false);
        correoElectronicoAcceder.setEnabled(false);
        contrasenaAcceder.setEnabled(false);

        spinnerLocalidad = root[0].findViewById(R.id.spinnerLocalidad);
        spinnerIntereses = root[0].findViewById(R.id.spinnerIntereses);

        botonEditar= root[0].findViewById(R.id.BotonEditarExpositor);
        botonCerrarSesion= root[0].findViewById(R.id.BotonCerrarSesionExpositor);
        botonEliminar= root[0].findViewById(R.id.BotonEliminarCuentaExpositor);
        DbSesion dbSesion=new DbSesion(getContext());
        botonCerrarSesion.setOnClickListener(view -> dbSesion.cerrarSesion());

        botonEliminar.setOnClickListener(view->eliminarCuentaExpositor((Artista)Bocu.usuario));


        nombreAcceder.setText(((Artista)Bocu.usuario).getNombreArtista());
        correoElectronicoAcceder.setText(((Artista)Bocu.usuario).getCorreoElectronico());
        contrasenaAcceder.setText(((Artista)Bocu.usuario).getContrasena());

        nombreAcceder.setEnabled(false);
        correoElectronicoAcceder.setEnabled(false);
        contrasenaAcceder.setEnabled(false);

        spinnerLocalidad = root[0].findViewById(R.id.spinnerLocalidad);
        spinnerIntereses = root[0].findViewById(R.id.spinnerIntereses);

        botonEditar= root[0].findViewById(R.id.BotonEditarExpositor);
        botonCerrarSesion= root[0].findViewById(R.id.BotonCerrarSesionExpositor);
        botonEliminar= root[0].findViewById(R.id.BotonEliminarCuentaExpositor);

        botonCerrarSesion.setOnClickListener(view -> dbSesion.cerrarSesion());
        botonEliminar.setOnClickListener(view->eliminarCuentaExpositor((Artista) Bocu.usuario));

        botonCancelar= root[0].findViewById(R.id.buttonCancelarExpositor);
        botonGuardar= root[0].findViewById(R.id.buttonGuardarExpositor);

        botonCancelar.setVisibility(View.INVISIBLE);
        botonGuardar.setVisibility(View.INVISIBLE);
        botonCancelar.setEnabled(false);
        botonGuardar.setEnabled(false);

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

        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreAcceder.setEnabled(true);
                correoElectronicoAcceder.setEnabled(true);
                contrasenaAcceder.setEnabled(true);
                spinnerLocalidad.setEnabled(true);
                spinnerIntereses.setEnabled(true);

                botonGuardar.setVisibility(View.VISIBLE);
                botonGuardar.setEnabled(true);
                botonCancelar.setVisibility(View.VISIBLE);
                botonCancelar.setEnabled(true);

                botonEditar.setEnabled(false);
                botonCerrarSesion.setEnabled(false);
                botonEliminar.setEnabled(false);
                botonEditar.setVisibility(View.INVISIBLE);
                botonCerrarSesion.setVisibility(View.INVISIBLE);
                botonEliminar.setVisibility(View.INVISIBLE);
            }
        });


        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View rootView = inflater.inflate(R.layout.fragment_cuenta_expositor, container, false);
                ViewGroup parent = (ViewGroup) root[0].getParent();
                int index = parent.indexOfChild(root[0]);
                parent.removeView(root[0]);
                root[0] = establecerContenidoExpositor(inflater, container);
                parent.addView(root[0], index);
            }
        });
        String correoInicial = correoElectronicoAcceder.getText().toString().trim();


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean flag = true;
                String mensajeError = "";

                if(nombreAcceder.getText().toString().trim().equals("")) {
                    mensajeError += "No ha ingresado nombres validos\n";
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

                if (flag){
                    String nombres = nombreAcceder.getText().toString().trim();
                    String correoElectronicoR = correoElectronicoAcceder.getText().toString();
                    String contrasenaR = contrasenaAcceder.getText().toString();
                    String localidadSeleccionada = spinnerLocalidad.getSelectedItem().toString();
                    String interesSeleccionado = spinnerIntereses.getSelectedItem().toString();
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


                    View rootView = inflater.inflate(R.layout.fragment_cuenta_expositor, container, false);
                    ViewGroup parent = (ViewGroup) root[0].getParent();
                    int index = parent.indexOfChild(root[0]);
                    parent.removeView(root[0]);
                    root[0] = establecerContenidoExpositor(inflater, container);
                    parent.addView(root[0], index);
                }

            }
        });


        ArrayAdapter<String> localidadesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Bocu.LOCALIDADES) {
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

        ArrayAdapter<String> interesesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Bocu.INTERESES) {
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





        localidadesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocalidad.setAdapter(localidadesAdapter);


        interesesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIntereses.setAdapter(interesesAdapter);

        spinnerLocalidad.setSelection(((Artista)Bocu.usuario).getLocalidad());

        spinnerIntereses.setSelection(((Artista)Bocu.usuario).getTipoDeEvento());

        spinnerIntereses.setEnabled(false);
        spinnerLocalidad.setEnabled(false);

        return root[0];

    }
    public void eliminarCuentaExpositor(Artista artista){
        /*
        for(int i=0; i<Bocu.expositores.size();i++){
            if (Bocu.expositores.get(i)==artista){
                Bocu.expositores.remove(i);
            }
        }*/
        DbExpositor dbExpositor = new DbExpositor(getContext());
        Boolean d=dbExpositor.eliminarExpositor(artista.getCorreoElectronico());
        if (d){
            Toast.makeText(getContext(), "usuario eliminado", Toast.LENGTH_SHORT);
        }
        DbSesion dbSesion = new DbSesion(getContext());
        dbSesion.cerrarSesion();

        Intent intent = new Intent(getContext(), PaginaInicio.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        System.exit(0);

    }
    public void eliminarCuentaUsuario(UsuarioRegistrado usuarioRegistrado){
        /*
        for(int i=0; i<Bocu.usuariosComunes.size();i++){
            if (Bocu.usuariosComunes.get(i)==usuarioRegistrado){
                Bocu.usuariosComunes.remove(i);
            }
        }*/
        DbUsuariosComunes dbUsuariosComunes = new DbUsuariosComunes(getContext());
        Boolean d= dbUsuariosComunes.eliminarUsuario(usuarioRegistrado.getCorreoElectronico());
        if (d){
            Toast.makeText(getContext(), "usuario eliminado", Toast.LENGTH_SHORT);
        }
        DbSesion dbSesion = new DbSesion(getContext());
        dbSesion.cerrarSesion();

        Intent intent = new Intent(getContext(), PaginaInicio.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        System.exit(0);

    }

    private View establecerContenidoUsuarioComun(LayoutInflater inflater, ViewGroup container){
        final View[] root = {inflater.inflate(R.layout.fragment_cuenta_usuario_comun, container, false)};

        nombreAcceder = root[0].findViewById(R.id.editTextNombre);
        correoElectronicoAcceder = root[0].findViewById(R.id.editTextCorreo);
        contrasenaAcceder = root[0].findViewById(R.id.editTextContrasena);
        apellidoAcceder = root[0].findViewById(R.id.editTextApellido);
        edadAcceder = root[0].findViewById(R.id.editTextEdad);

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

        spinnerLocalidad = root[0].findViewById(R.id.spinnerLocalidad);
        spinnerIntereses = root[0].findViewById(R.id.spinnerIntereses);

        botonEditar= root[0].findViewById(R.id.BotonEditarUsuario);
        botonCerrarSesion= root[0].findViewById(R.id.BotonCerrarSesion);
        botonEliminar= root[0].findViewById(R.id.BotonEliminarCuenta);
        DbSesion dbSesion=new DbSesion(getContext());
        botonCerrarSesion.setOnClickListener(view -> dbSesion.cerrarSesion());
        botonEliminar.setOnClickListener(view->eliminarCuentaUsuario((UsuarioComun)Bocu.usuario));

        botonCancelar= root[0].findViewById(R.id.buttonCancelarVistaEditarUsuario);
        botonGuardar= root[0].findViewById(R.id.buttonGuardarVistaEditarUsuario);

        botonCancelar.setVisibility(View.INVISIBLE);
        botonGuardar.setVisibility(View.INVISIBLE);
        botonCancelar.setEnabled(false);
        botonGuardar.setEnabled(false);

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

        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreAcceder.setEnabled(true);
                correoElectronicoAcceder.setEnabled(true);
                contrasenaAcceder.setEnabled(true);
                apellidoAcceder.setEnabled(true);
                edadAcceder.setEnabled(true);
                spinnerLocalidad.setEnabled(true);
                spinnerIntereses.setEnabled(true);

                botonGuardar.setVisibility(View.VISIBLE);
                botonGuardar.setEnabled(true);
                botonCancelar.setVisibility(View.VISIBLE);
                botonCancelar.setEnabled(true);

                botonEditar.setEnabled(false);
                botonCerrarSesion.setEnabled(false);
                botonEliminar.setEnabled(false);
                botonEditar.setVisibility(View.INVISIBLE);
                botonCerrarSesion.setVisibility(View.INVISIBLE);
                botonEliminar.setVisibility(View.INVISIBLE);
            }
        });


        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View rootView = inflater.inflate(R.layout.fragment_cuenta_usuario_comun, container, false);
                ViewGroup parent = (ViewGroup) root[0].getParent();
                int index = parent.indexOfChild(root[0]);
                parent.removeView(root[0]);
                root[0] = establecerContenidoUsuarioComun(inflater, container);
                parent.addView(root[0], index);
            }
        });
        String correoInicial = correoElectronicoAcceder.getText().toString().trim();


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean flag = true;
                String mensajeError = "";

                if(nombreAcceder.getText().toString().trim().equals("")) {
                    mensajeError += "No ha ingresado nombres validos\n";
                    flag = false;
                }
                if(apellidoAcceder.getText().toString().trim().equals("")) {
                    mensajeError += "No ha ingresado apellidos validos\n";
                    flag = false;
                }
                String edadString = edadAcceder.getText().toString().trim();
                if (edadString.isEmpty()) {
                    flag=false;
                    mensajeError +="Ingrese una edad válida";
                }



                if(edadAcceder.getText().toString().trim().equals("") || Integer.parseInt(edadAcceder.getText().toString().trim()) > 150) {
                    mensajeError += "No ha ingresado una edad valida\n";
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

                if (flag){
                    String nombres = nombreAcceder.getText().toString().trim();
                    String apellidos = apellidoAcceder.getText().toString().trim();
                    int edad = Integer.parseInt(edadAcceder.getText().toString());
                    String correoElectronicoR = correoElectronicoAcceder.getText().toString();
                    String contrasenaR = contrasenaAcceder.getText().toString();
                    String localidadSeleccionada = spinnerLocalidad.getSelectedItem().toString();
                    String interesSeleccionado = spinnerIntereses.getSelectedItem().toString();
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


                    View rootView = inflater.inflate(R.layout.fragment_cuenta_usuario_comun, container, false);
                    ViewGroup parent = (ViewGroup) root[0].getParent();
                    int index = parent.indexOfChild(root[0]);
                    parent.removeView(root[0]);
                    root[0] = establecerContenidoUsuarioComun(inflater, container);
                    parent.addView(root[0], index);
                }

            }
        });


        ArrayAdapter<String> localidadesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Bocu.LOCALIDADES) {
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

        ArrayAdapter<String> interesesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Bocu.INTERESES) {
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

        return root[0];

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