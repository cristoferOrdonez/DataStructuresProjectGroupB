package com.example.datastructureproject_groupb;

import android.app.Application;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.entidades.Artista;
import com.example.datastructureproject_groupb.entidades.Evento;
import com.example.datastructureproject_groupb.entidades.UsuarioComun;
import com.example.datastructureproject_groupb.entidades.UsuarioRegistrado;

public class Bocu extends Application {

    public final static int SIN_REGISTRAR = 0;
    public final static int USUARIO_COMUN = 1;
    public final static int ARTISTA = 2;
    public static DynamicUnsortedList<Evento> eventos, eventosExpositor;
    public static DynamicUnsortedList<Integer> posicionesEventosExpositor;
    public static DynamicUnsortedList<Artista> expositores;
    public static DynamicUnsortedList<UsuarioComun> usuariosComunes;
    public static UsuarioRegistrado usuario;
    public static int estadoUsuario = SIN_REGISTRAR;
    public static final String [] LOCALIDADES = new String[]{ "Virtual","Usaquén", "Chapinero", "Santa Fe", "San Cristóbal", "Usme", "Tunjuelito", "Bosa", "Kennedy", "Fontibón", "Engativá", "Suba", "Barrios Unidos", "Teusaquillo", "Los Mártires", "Antonio Nariño", "Puente Aranda", "La Candelaria", "Rafael Uribe Uribe", "Ciudad Bolívar", "Sumapaz"   };
    public static final String [] INTERESES = new String[]{"Musica", "Talleres",     };

    @Override
    public void onCreate() {
        super.onCreate();

        DbEventos dbEventos = new DbEventos(this);

        eventos = dbEventos.obtenerEventos();

        usuario = null;

        usuariosComunes = null;

        expositores = null;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
