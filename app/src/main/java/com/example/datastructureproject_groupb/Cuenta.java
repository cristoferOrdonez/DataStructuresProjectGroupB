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

import com.example.datastructureproject_groupb.db.DbUsuarios;
import com.example.datastructureproject_groupb.entidades.Usuarios;

import java.util.List;

public class Cuenta extends AppCompatActivity {
    Button botonPaginaPrincipal, botonEventos, botonDescubrir, botonAcceder, botonCrearCuentaUsuario, botonCrearCuentaExpositor;
    EditText editTextcorreoElectronico, editTextcontrasena;



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

        editTextcorreoElectronico=findViewById(R.id.editTextCorreo);
        editTextcontrasena=findViewById(R.id.editTextContrasena);


        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonEventos.setOnClickListener(view -> cambiarAEventos());
        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());
        botonAcceder.setOnClickListener(view -> acceder(new View(this)));
        botonCrearCuentaUsuario.setOnClickListener(view -> cambiarARegistroUsuario());
        botonCrearCuentaExpositor.setOnClickListener(view -> cambiarARegistroExpositor());


    }

    protected void onDestroy() {
        super.onDestroy();
    }




    public void acceder(View view) {

        DbUsuarios dbUsuarios = new DbUsuarios(this);

        if(verificarExistencia(dbUsuarios.obtenerCorreosElectronicos())){

            Usuarios usuario = dbUsuarios.verUsuario(editTextcorreoElectronico.getText().toString().toLowerCase());

            if(editTextcontrasena.getText().toString().equals(usuario.getContrasena())){

                Intent intent = new Intent(this, PaginaPrincipal.class);
                intent.putExtra("correoElectronico", usuario.getCorreoElectronico());
                startActivity(intent);
                finishAffinity();

            } else {

                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

            }


        } else {

            Toast.makeText(this, "El correo electronico ingresado no se encuentra registrado.", Toast.LENGTH_SHORT).show();

        }

    }

    public boolean verificarExistencia(List<String> correos){

        boolean existencia = false;

        for(String correo : correos){

            existencia = correo.equalsIgnoreCase(editTextcorreoElectronico.getText().toString());

            if(existencia)
                break;

        }

        return existencia;

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