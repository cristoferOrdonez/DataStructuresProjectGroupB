package com.example.datastructureproject_groupb;

import android.app.Application;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.entidades.Artista;
import com.example.datastructureproject_groupb.entidades.Evento;
import com.example.datastructureproject_groupb.entidades.UsuarioComun;

public class Bocu extends Application {

    public final static int SIN_REGISTRAR = 0;
    public final static int USUARIO_COMUN = 1;
    public final static int ARTISTA = 2;
    public static DynamicUnsortedList<Evento> eventos, eventosExpositor;
    public static DynamicUnsortedList<Integer> posicionesEventosExpositor;
    public static DynamicUnsortedList<Artista> expositores = null;
    public static DynamicUnsortedList<UsuarioComun> usuariosComunes = null;
    public static String correoElectronico = null;
    public static int estadoUsuario = SIN_REGISTRAR;

    @Override
    public void onCreate() {
        super.onCreate();

        DbEventos dbEventos = new DbEventos(this);

        eventos = dbEventos.obtenerEventos();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
