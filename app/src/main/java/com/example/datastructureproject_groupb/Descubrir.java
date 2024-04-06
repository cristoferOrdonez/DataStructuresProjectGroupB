package com.example.datastructureproject_groupb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class Descubrir extends AppCompatActivity {

    Button botonPaginaPrincipal, botonCuenta, botonEventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descubrir);

        botonPaginaPrincipal=findViewById(R.id.botonPaginaPrincipalDescubrir);
        botonCuenta=findViewById(R.id.botonCuentaDescubrir);
        botonEventos=findViewById(R.id.botonEventosDescubrir);

        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonCuenta.setOnClickListener(view -> cambiarACuenta());
        botonEventos.setOnClickListener(view -> cambiarAEventos());

    }

    public void cambiarAPaginaPrincipal() {
        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        startActivity(miIntent);
        finishAffinity();
    }
    
    public void cambiarACuenta() {
        Intent miIntent = new Intent(this, Cuenta.class);
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


}
