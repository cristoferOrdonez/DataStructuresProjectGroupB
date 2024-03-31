package com.example.datastructureproject_groupb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.metrics.Event;

import androidx.annotation.Nullable;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StaticUnsortedList;
import com.example.datastructureproject_groupb.entidades.EventosEntidad;

import java.util.Date;

public class DbEventos extends DbArt {

    Context context;

    public DbEventos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarEvento(String nombreEvento, int anoEvento, int mesEvento, int diaEvento, String ubicacionEvento, int costoEvento, String horarioEvento, String descripcionEvento, int localidadEvento, int categoriaEvento) {
        long id = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();

            EventosEntidad evento;

            Cursor cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTOS, null);

            StaticUnsortedList<EventosEntidad> nuevosEventos = new StaticUnsortedList<>(cursorEventos.getCount() + 1);

            nuevosEventos.insert(new EventosEntidad(0 ,nombreEvento,
                    new Date(anoEvento, mesEvento, diaEvento), ubicacionEvento,
                    localidadEvento, costoEvento, horarioEvento,
                    categoriaEvento, descripcionEvento));

            for(int i = 0; i < cursorEventos.getCount(); i++){

                if (cursorEventos.moveToFirst()) {

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

                        nuevosEventos.insert(evento);


                    } while (cursorEventos.moveToNext());

                }

            }

            db.delete(TABLE_EVENTOS, null, null);

            ContentValues values;

            EventosEntidad eventoAlt;

            for(int i = 0; i < nuevosEventos.size(); i++){

                values = new ContentValues();

                eventoAlt = nuevosEventos.get(i);

                values.put("nombreEvento", eventoAlt.getNombreEvento());
                values.put("AnoEvento", eventoAlt.getAno());
                values.put("mesEvento", eventoAlt.getMes());
                values.put("diaEvento", eventoAlt.getDia());
                values.put("ubicacionEvento", eventoAlt.getUbicacionEvento());
                values.put("costoEvento", eventoAlt.getCostoEvento());
                values.put("horarioEvento", eventoAlt.getHorarioEvento());
                values.put("descripcionEvento", eventoAlt.getDescripcionEvento());
                values.put("localidadEvento", eventoAlt.getLocalidadEvento());
                values.put("categoriaEvento", eventoAlt.getCategoriaEvento());

                db.insert(TABLE_EVENTOS, null, values);

            }

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }


    public boolean editarEvento(String nombreEvento, int AnoEvento, int mesEvento, int diaEvento, String ubicacionEvento, int costoEvento, String horarioEvento, String descripcionEvento, int localidadEvento, int categoriaEvento, String idEvento) {
        boolean correcto = false;

        DbArt dbHelper = new DbArt(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        EventosEntidad evento;

        StaticUnsortedList<EventosEntidad> nuevosEventos = obtenerEventos();

        db.delete(TABLE_EVENTOS, null, null);

        for(int i = 0; i < nuevosEventos.size(); i++){

            evento = nuevosEventos.get(i);

            if(evento.getId() == Integer.parseInt(idEvento)){
                correcto = true;
                evento.setNombreEvento(nombreEvento);
                evento.setFechaEvento(new Date(AnoEvento, mesEvento, diaEvento));
                evento.setUbicacionEvento(ubicacionEvento);
                evento.setCostoEvento(costoEvento);
                evento.setHorarioEvento(horarioEvento);
                evento.setDescripcionEvento(descripcionEvento);
                evento.setLocalidadEvento(localidadEvento);
                evento.setCategoriaEvento(categoriaEvento);

            }

        }

        ContentValues values;

        EventosEntidad eventoAlt;

        for(int i = 0; i < nuevosEventos.size(); i++){

            values = new ContentValues();

            eventoAlt = nuevosEventos.get(i);

            values.put("nombreEvento", eventoAlt.getNombreEvento());
            values.put("AnoEvento", eventoAlt.getAno());
            values.put("mesEvento", eventoAlt.getMes());
            values.put("diaEvento", eventoAlt.getDia());
            values.put("ubicacionEvento", eventoAlt.getUbicacionEvento());
            values.put("costoEvento", eventoAlt.getCostoEvento());
            values.put("horarioEvento", eventoAlt.getHorarioEvento());
            values.put("descripcionEvento", eventoAlt.getDescripcionEvento());
            values.put("localidadEvento", eventoAlt.getLocalidadEvento());
            values.put("categoriaEvento", eventoAlt.getCategoriaEvento());

            db.insert(TABLE_EVENTOS, null, values);

        }

        db.close();

        return correcto;
    }

    public StaticUnsortedList<EventosEntidad> obtenerEventos() {

        SQLiteDatabase db = this.getWritableDatabase();

        EventosEntidad evento;
        Cursor cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTOS, null);

        StaticUnsortedList<EventosEntidad> listaEventos = new StaticUnsortedList<>(cursorEventos.getCount());

        if (cursorEventos.moveToFirst()) {

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
    public boolean eliminarEvento(int idEvento) {
        DbArt dbHelper = new DbArt(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean eliminado = false;

        EventosEntidad evento;

        Cursor cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTOS, null);

        StaticUnsortedList<EventosEntidad> nuevosEventos = new StaticUnsortedList<>(cursorEventos.getCount() - 1);

        for(int i = 0; i < cursorEventos.getCount(); i++){

            if (cursorEventos.moveToFirst()) {

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

                    if(idEvento != evento.getId()) {
                        nuevosEventos.insert(evento);
                        eliminado = true;
                    }

                } while (cursorEventos.moveToNext());

            }

        }

        db.delete(TABLE_EVENTOS, null, null);

        ContentValues values;

        EventosEntidad eventoAlt;

        for(int i = 0; i < nuevosEventos.size(); i++){

            values = new ContentValues();

            eventoAlt = nuevosEventos.get(i);

            values.put("nombreEvento", eventoAlt.getNombreEvento());
            values.put("AnoEvento", eventoAlt.getAno());
            values.put("mesEvento", eventoAlt.getMes());
            values.put("diaEvento", eventoAlt.getDia());
            values.put("ubicacionEvento", eventoAlt.getUbicacionEvento());
            values.put("costoEvento", eventoAlt.getCostoEvento());
            values.put("horarioEvento", eventoAlt.getHorarioEvento());
            values.put("descripcionEvento", eventoAlt.getDescripcionEvento());
            values.put("localidadEvento", eventoAlt.getLocalidadEvento());
            values.put("categoriaEvento", eventoAlt.getCategoriaEvento());

            db.insert(TABLE_EVENTOS, null, values);

        }

        db.close();

        return eliminado;
    }
}

