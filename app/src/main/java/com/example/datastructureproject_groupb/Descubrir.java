package com.example.datastructureproject_groupb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Descubrir extends AppCompatActivity {

    Button botonPaginaPrincipal, botonCuenta, botonEventos;
    String correoElectronico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descubrir);

        correoElectronico = getIntent().getStringExtra("correoElectronico");

        botonPaginaPrincipal=findViewById(R.id.botonPaginaPrincipalDescubrir);
        botonCuenta=findViewById(R.id.botonCuentaDescubrir);
        botonEventos=findViewById(R.id.botonEventosDescubrir);

        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonCuenta.setOnClickListener(view -> cambiarACuenta());
        botonEventos.setOnClickListener(view -> cambiarAEventos());

    }

    public void cambiarAPaginaPrincipal() {
        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        miIntent.putExtra("correoElectronico",correoElectronico);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarACuenta() {
        Intent miIntent = new Intent(this, Cuenta.class);
        miIntent.putExtra("correoElectronico",correoElectronico);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarAEventos() {
        Intent miIntent = new Intent(this, Eventos.class);
        miIntent.putExtra("correoElectronico",correoElectronico);
        startActivity(miIntent);
        finishAffinity();
    }


}
