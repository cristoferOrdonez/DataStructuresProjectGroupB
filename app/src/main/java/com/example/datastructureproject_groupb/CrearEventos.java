package com.example.datastructureproject_groupb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CrearEventos extends AppCompatActivity {
    Button botonPaginaPrincipal, botonCuenta, botonDescubrir, botonEventos;
    String correoElectronico;
    RecyclerView listaEventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        botonPaginaPrincipal = findViewById(R.id.botonPaginaPrincipalEventos);
        botonCuenta = findViewById(R.id.botonCuentaEventos);
        botonDescubrir = findViewById(R.id.botonDescubrirEventos);
        botonEventos=findViewById(R.id.botonEventosDescubrir);

        botonPaginaPrincipal.setOnClickListener(view -> cambiarAPaginaPrincipal());
        botonCuenta.setOnClickListener(view -> cambiarACuenta());
        botonDescubrir.setOnClickListener(view -> cambiarADescubrir());
        botonEventos.setOnClickListener(view -> cambiarAEventos());
    }

    public void cambiarAPaginaPrincipal() {
        Intent miIntent = new Intent(this, PaginaPrincipal.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
    public void cambiarACuenta() {
        Intent miIntent = new Intent(this, Cuenta.class);
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
    public void cambiarAEventos() {
        Intent miIntent = new Intent(this, Eventos.class);
        //miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
}
