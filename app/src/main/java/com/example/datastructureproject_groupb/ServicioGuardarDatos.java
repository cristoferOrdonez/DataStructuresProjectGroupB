package com.example.datastructureproject_groupb;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.db.DbExpositor;
import com.example.datastructureproject_groupb.db.DbUsuariosComunes;

public class ServicioGuardarDatos extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        guardarDatos();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }

    private void guardarDatos() {
        if(Bocu.cambiosEnEventos) {
            Toast.makeText(this, "Guardando Eventos", Toast.LENGTH_SHORT).show();
            new DbEventos(this).guardarEventos();
            Bocu.cambiosEnEventos = false;
        }
        if(Bocu.cambiosEnExpositores){
            Toast.makeText(this, "Guardando Expositores", Toast.LENGTH_SHORT).show();
            new DbExpositor(this).guardarExpositores();
            Bocu.cambiosEnExpositores = false;
        }
        if(Bocu.cambiosEnUsuariosComunes){
            Toast.makeText(this, "Guardando Usuarios comunes", Toast.LENGTH_SHORT).show();
            new DbUsuariosComunes(this).guardarUsuariosComunes();
            Bocu.cambiosEnUsuariosComunes = false;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
