package com.example.datastructureproject_groupb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Cuenta extends AppCompatActivity {
    Button botonPaginaPrincipal, botonEventos, botonDescubrir, botonAcceder, botonCrearCuentaUsuario, botonCrearCuentaExpositor;
    EditText correoElectronico, contrasena;



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

        correoElectronico=findViewById(R.id.editTextCorreo);
        contrasena=findViewById(R.id.editTextContrasena);


        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonEventos.setOnClickListener(view -> cambiarAEventos());
        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());
        botonAcceder.setOnClickListener(view -> Acceder());
        botonCrearCuentaUsuario.setOnClickListener(view -> cambiarARegistroUsuario());
        botonCrearCuentaExpositor.setOnClickListener(view -> cambiarARegistroExpositor());


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
    public void Acceder() {

    }
    public void cambiarARegistroUsuario() {


    }
    public void cambiarARegistroExpositor() {


    }



}