package com.example.datastructureproject_groupb;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.datastructureproject_groupb.db.DbEventos;

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
        Toast.makeText(this, "WAOS", Toast.LENGTH_SHORT).show();
        new DbEventos(this).guardarEventos();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
