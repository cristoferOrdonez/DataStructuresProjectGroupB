package com.example.datastructureproject_groupb;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

public class Bocu extends Application {

    public static DynamicUnsortedList<EventosEntidad> eventos;

    @Override
    public void onCreate() {
        super.onCreate();
        eventos = new DbEventos(this).obtenerEventos();
        //startService(new Intent(this, ServicioGuardarDatos.class));

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            startService(new Intent(this, ServicioGuardarDatos.class));
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
