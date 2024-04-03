package com.example.datastructureproject_groupb;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Intent;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.entidades.Artista;
import com.example.datastructureproject_groupb.entidades.Evento;
import com.example.datastructureproject_groupb.entidades.UsuarioComun;

public class Bocu extends Application {

    public final static int SIN_REGISTRAR = 0;
    public final static int USUARIO_COMUN = 1;
    public final static int ARTISTA = 2;
    public static DynamicUnsortedList<Evento> eventos, nuevosEventos, eventosEditados;
    public static DynamicUnsortedList<Integer> eventosEliminados;
    public static DynamicUnsortedList<Artista> expositores = null;
    public static DynamicUnsortedList<UsuarioComun> usuariosComunes = null;
    public static String correoElectronico = null;
    public static int estadoUsuario = SIN_REGISTRAR;
    public static boolean cambiosEnEventos, cambiosEnUsuariosComunes, cambiosEnExpositores;

    @Override
    public void onCreate() {
        super.onCreate();
        eventos = new DbEventos(this).obtenerEventos();
        nuevosEventos = new DynamicUnsortedList<>();
        eventosEliminados = new DynamicUnsortedList<>();
        eventosEditados = new DynamicUnsortedList<>();
        cambiosEnEventos = false;
        cambiosEnUsuariosComunes = false;
        cambiosEnExpositores = false;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            if(cambiosEnEventos || cambiosEnUsuariosComunes || cambiosEnExpositores)
                startService(new Intent(this, ServicioGuardarDatos.class));
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
