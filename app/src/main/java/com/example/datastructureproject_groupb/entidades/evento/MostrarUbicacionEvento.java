package com.example.datastructureproject_groupb.entidades.evento;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datastructureproject_groupb.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MostrarUbicacionEvento extends AppCompatActivity {

    private GoogleMap gMap;
    private ImageButton botonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ubicacion_evento);

        botonCancelar = findViewById(R.id.imageButtonCancelar);

        botonCancelar.setOnClickListener(view -> finish());

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaMostrarEventos);

        supportMapFragment.getMapAsync(googleMap -> {

            gMap = googleMap;

            String ubicacionEvento = getIntent().getStringExtra("UBICACION_EVENTO");

            LatLng latLng = new LatLng(Double.parseDouble(ubicacionEvento.substring(0, ubicacionEvento.indexOf(" - "))), Double.parseDouble(ubicacionEvento.substring(ubicacionEvento.indexOf(" - ") + 3)));

            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
            gMap.getUiSettings().setZoomControlsEnabled(true);
            LatLngBounds bogotaBounds = new LatLngBounds(
                    new LatLng(4.4625, -74.2346),
                    new LatLng(4.8159, -73.9875)
            );

            gMap.addMarker(new MarkerOptions().position(latLng).title(getIntent().getStringExtra("NOMBRE_EVENTO")));
            gMap.setMinZoomPreference(10.0f);
            gMap.setLatLngBoundsForCameraTarget(bogotaBounds);

        });


    }
}