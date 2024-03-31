package com.example.datastructureproject_groupb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StaticUnsortedList;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

import java.util.Date;

public class DbEventos extends DbArt{

    Context context;

    public DbEventos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarEvento(String nombreEvento, int AnoEvento, int mesEvento, int diaEvento, String ubicacionEvento, int costoEvento, String horarioEvento, String descripcionEvento, int localidadEvento, int categoriaEvento) {
        long id = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombreEvento", nombreEvento);
            values.put("AnoEvento", AnoEvento);
            values.put("mesEvento", mesEvento);
            values.put("diaEvento", diaEvento);
            values.put("ubicacionEvento", ubicacionEvento);
            values.put("costoEvento", costoEvento);
            values.put("horarioEvento", horarioEvento);
            values.put("descripcionEvento", descripcionEvento);
            values.put("localidadEvento", localidadEvento);
            values.put("categoriaEvento", categoriaEvento);

            id = db.insert(TABLE_EVENTOS, null, values);

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public StaticUnsortedList<EventosEntidad> obtenerEventos(){

        SQLiteDatabase db = this.getWritableDatabase();

        EventosEntidad evento;
        Cursor cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTOS, null);

        StaticUnsortedList<EventosEntidad> listaEventos = new StaticUnsortedList<>(cursorEventos.getCount());

        if(cursorEventos.moveToFirst()){

            do {

                evento = new EventosEntidad(cursorEventos.getInt(0),
                        cursorEventos.getString(1),
                        new Date(cursorEventos.getInt(2), cursorEventos.getInt(3), cursorEventos.getInt(4)),
                        cursorEventos.getString(5),
                        cursorEventos.getInt(6),
                        cursorEventos.getInt(7),
                        cursorEventos.getString(8),
                        cursorEventos.getInt(9),
                        cursorEventos.getString(10));

                listaEventos.insert(evento);


            } while (cursorEventos.moveToNext());

        }

        cursorEventos.close();

        return listaEventos;


    }

}
