package com.example.datastructureproject_groupb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


    public boolean editarEvento(String nombreEvento, int AnoEvento, int mesEvento, int diaEvento, String ubicacionEvento, int costoEvento, String horarioEvento, String descripcionEvento, int localidadEvento, int categoriaEvento, String idEvento) {
        boolean correcto;

        DbArt dbHelper = new DbArt(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
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

        // Define la cláusula WHERE para eliminar el evento por su ID
        String selection = "idEvento = ?";
        String[] selectionArgs = {String.valueOf(idEvento)};

        // Ejecuta la eliminación
        int rowsDeleted = db.delete("t_eventos  ", selection, selectionArgs);

        // Verifica si se eliminó al menos una fila
        if (rowsDeleted > 0) {
            eliminado = true;
        }

        db.close(); // Cierra la base de datos

        return eliminado;
    }
}

