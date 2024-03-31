package com.example.datastructureproject_groupb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.datastructureproject_groupb.db.DbExpositor;
import com.example.datastructureproject_groupb.db.DbUsuarios;
import com.example.datastructureproject_groupb.entidades.Artistas;
import com.example.datastructureproject_groupb.entidades.Usuarios;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cuenta extends AppCompatActivity {
    Button botonPaginaPrincipal, botonEventos, botonDescubrir, botonAcceder, botonCrearCuentaUsuario, botonCrearCuentaExpositor;
    EditText CorreoElectronicoAcceder, ContrasenaAcceder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
        botonPaginaPrincipal=findViewById(R.id.botonPaginaPrincipalCuenta);
        botonEventos=findViewById(R.id.botonEventosCuenta);
        botonDescubrir=findViewById(R.id.botonDescubrirCuenta);
        botonAcceder=findViewById(R.id.botonAccederCuenta);
        botonCrearCuentaUsuario=findViewById(R.id.botonCrearCuentaUsuario);
        botonCrearCuentaExpositor=findViewById(R.id.botonCrearCuentaExpositor);

        CorreoElectronicoAcceder=findViewById(R.id.editTextCorreo);
        ContrasenaAcceder=findViewById(R.id.editTextContrasena);


        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonEventos.setOnClickListener(view -> cambiarAEventos());
        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());
        botonCrearCuentaUsuario.setOnClickListener(view -> cambiarARegistroUsuario());
        botonCrearCuentaExpositor.setOnClickListener(view -> cambiarARegistroExpositor());


    }

    protected void onDestroy() {
        super.onDestroy();
    }


    public void acceder(View view, String correoElectronicoS, String tipoUsuario) {

        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        miIntent.putExtra("tipoUsuario", tipoUsuario);
        if (tipoUsuario == "Usuario") {
            Toast.makeText(this, "Ingreso correctamente como Usuario", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Ingreso correctamente como Artista", Toast.LENGTH_SHORT).show();
        startActivity(miIntent);
        finishAffinity();
    }

    public void revisar(View view){
        DbUsuarios dbUsuarios = new DbUsuarios(this);
        DbExpositor dbExpositor = new DbExpositor(this);
        String tipoUsuario = "Invitado";

        int opcion = verificarExistencia(dbUsuarios.obtenerCorreosElectronicos(), dbExpositor.obtenerCorreosElectronicosExpositores());
        if(opcion>-1){
            if(opcion==0){
                Usuarios usuario = dbUsuarios.verUsuario(CorreoElectronicoAcceder.getText().toString().toLowerCase());
                if(ContrasenaAcceder.getText().toString().equals(usuario.getContrasena())){
                    tipoUsuario = "Usuario";
                    acceder(view, CorreoElectronicoAcceder.getText().toString(), tipoUsuario);
                } else {
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }

            else if(opcion==1){
                Artistas usuario = dbExpositor.verUsuarioExpositor(CorreoElectronicoAcceder.getText().toString().toLowerCase());
                if(ContrasenaAcceder.getText().toString().equals(usuario.getContrasena())){
                    tipoUsuario = "Artista";
                    acceder(view, CorreoElectronicoAcceder.getText().toString(), tipoUsuario);
                } else {
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }

        } else {

            Toast.makeText(this, "El correo electronico ingresado no se encuentra registrado.", Toast.LENGTH_SHORT).show();

        }

        dbUsuarios.close();
        dbExpositor.close();
    }

    public void VerificarInformacionAcceso (View view) {

        boolean flag = true;
        String mensajeError = "";

        if(CorreoElectronicoAcceder.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado un correo valido\n";
            flag = false;
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(CorreoElectronicoAcceder.getText().toString());

        if(!mather.find()){
            mensajeError += "No ha ingresado un correo electronico valido\n";
            flag = false;
        }
        if(ContrasenaAcceder.getText().toString().length() < 8){
            mensajeError += "Debe ingresar una contraseña de por lo menos 8 caracteres\n";
            flag = false;
        }
        if(ContrasenaAcceder.getText().toString().contains(" ")){
            mensajeError += "La contraseña no puede contener espacios en blanco\n";
            flag = false;
        }

        if(flag)
            revisar(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();
    }

    public int verificarExistencia(List<String> correosUsuario, List<String> correosArtista){

        boolean existencia;

        for(String correo : correosUsuario){

            existencia = correo.equalsIgnoreCase(CorreoElectronicoAcceder.getText().toString());
            if(existencia)
                return 0;
        }
        for(String correo : correosArtista){
            existencia = correo.equalsIgnoreCase(CorreoElectronicoAcceder.getText().toString());
            if(existencia)
                return 1;
        }

        return -1;

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == KeyEvent.KEYCODE_BACK){

            SpannableString message = new SpannableString("¿Desea salir de BoCu?");
            message.setSpan(new ForegroundColorSpan(Color.WHITE), 0, message.length(), 0);

            SpannableString afirmacion = new SpannableString("Si");
            afirmacion.setSpan(new ForegroundColorSpan(Color.WHITE), 0, afirmacion.length(), 0);

            SpannableString negacion = new SpannableString("No");
            negacion.setSpan(new ForegroundColorSpan(Color.WHITE), 0, negacion.length(), 0);

            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Base_Theme_DataStructureProjectGroupB));
            builder.setMessage(message)
                    .setPositiveButton(afirmacion, (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            finishAndRemoveTask();
                        } else {
                            finishAffinity();
                            System.exit(0);
                        }

                    })
                    .setNegativeButton(negacion, (dialog, which) -> dialog.dismiss());
            builder.show();
        }

        return super.onKeyDown(keyCode, event);

    }





    public void cambiarAPaginaPrincipal() {
        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarAEventos() {
        Intent miIntent = new Intent(this, Eventos.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarADescubrir() {
        Intent miIntent = new Intent(this, Descubrir.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }

    public void cambiarARegistroUsuario() {
        Intent miIntent = new Intent(this, com.example.datastructureproject_groupb.CrearCuentaUsuario.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();


    }
    public void cambiarARegistroExpositor() {
        Intent miIntent = new Intent(this, com.example.datastructureproject_groupb.CrearCuentaExpositor.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();


    }



}