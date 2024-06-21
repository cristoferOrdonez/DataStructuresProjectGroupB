package com.example.datastructureproject_groupb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.entidades.evento.Evento;

import java.util.Date;

public class DbEventos extends DbArt {

    Context context;

    public DbEventos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarEvento(String nombreEvento, int AnoEvento, int mesEvento, int diaEvento, String direPlatEvento, int costoEvento, String horarioEvento, String descripcionEvento, int localidadEvento, int categoriaEvento, String correoAutor) {
        long id = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombreEvento", nombreEvento);
            values.put("AnoEvento", AnoEvento);
            values.put("mesEvento", mesEvento);
            values.put("diaEvento", diaEvento);
            values.put("direPlatEvento", direPlatEvento);
            values.put("costoEvento", costoEvento);
            values.put("horarioEvento", horarioEvento);
            values.put("descripcionEvento", descripcionEvento);
            values.put("ubicacionEvento", localidadEvento);
            values.put("categoriaEvento", categoriaEvento);
            values.put("correoAutor", correoAutor);

            id = db.insert(TABLE_EVENTOS, null, values);

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }


    public boolean editarEvento(String nombreEvento, int AnoEvento, int mesEvento, int diaEvento, String direPlatEvento, int costoEvento, String horarioEvento, String descripcionEvento, int localidadEvento, int categoriaEvento, String idEvento, String correoAutor) {
        boolean correcto;

        DbArt dbHelper = new DbArt(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombreEvento", nombreEvento);
            values.put("AnoEvento", AnoEvento);
            values.put("mesEvento", mesEvento);
            values.put("diaEvento", diaEvento);
            values.put("direPlatEvento", direPlatEvento);
            values.put("costoEvento", costoEvento);
            values.put("horarioEvento", horarioEvento);
            values.put("descripcionEvento", descripcionEvento);
            values.put("ubicacionEvento", localidadEvento);
            values.put("categoriaEvento", categoriaEvento);
            values.put("correoAutor", correoAutor);

            int rowsAffected = db.update(TABLE_EVENTOS, values, "idEvento = ?", new String[]{idEvento});

            correcto = (rowsAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public DynamicUnsortedList<Evento> obtenerEventos() {

        SQLiteDatabase db = this.getWritableDatabase();

        Evento evento;
        Cursor cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTOS, null);

        DynamicUnsortedList<Evento> listaEventos = new DynamicUnsortedList<>();

        if (cursorEventos.moveToFirst()) {

            do {

                evento = new Evento(context,
                        cursorEventos.getInt(0),
                        cursorEventos.getString(1),
                        new Date(cursorEventos.getInt(2), cursorEventos.getInt(3), cursorEventos.getInt(4)),
                        cursorEventos.getString(5),
                        cursorEventos.getInt(6),
                        cursorEventos.getInt(7),
                        cursorEventos.getString(8),
                        cursorEventos.getInt(9),
                        cursorEventos.getString(10),
                        cursorEventos.getString(11));

                listaEventos.insert(evento);


            } while (cursorEventos.moveToNext());

        }

        cursorEventos.close();

        return listaEventos;
    }
    public void eliminarEvento(long idEvento) {
        DbArt dbHelper = new DbArt(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TABLE_EVENTOS, "idEvento = ?", new String[]{String.valueOf(idEvento)});

        db.close();

    }

    public void guardarEventos(){

        int veces = Bocu.eventos.size();

        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_EVENTOS, null, null);

        ContentValues values;

        Evento evento;

        for(int i = 0; i < veces; i++) {
            try {
                evento = Bocu.eventos.get(i);
                values = new ContentValues();
                values.put("nombreEvento", evento.getNombreEvento());
                values.put("AnoEvento", evento.getFechaEvento().getYear());
                values.put("mesEvento", evento.getFechaEvento().getMonth());
                values.put("diaEvento", evento.getFechaEvento().getDate());
                values.put("direPlatEvento", evento.getDirePlatEvento());
                values.put("costoEvento", evento.getCostoEvento());
                values.put("horarioEvento", evento.getHorarioEvento());
                values.put("descripcionEvento", evento.getDescripcionEvento());
                values.put("ubicacionEvento", evento.getUbicacionEvento());
                values.put("categoriaEvento", evento.getCategoriaEvento());

                db.insert(TABLE_EVENTOS, null, values);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        db.close();

    }

}