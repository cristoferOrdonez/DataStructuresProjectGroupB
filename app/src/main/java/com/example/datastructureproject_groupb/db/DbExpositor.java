package com.example.datastructureproject_groupb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.DynamicUnsortedList;
import com.example.datastructureproject_groupb.entidades.artista.Artista;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.LinkedList;

public class DbExpositor extends DbArt {
    Context context;

    public DbExpositor(Context context) {
        super(context);
        this.context=context;
    }

    public DynamicUnsortedList<Artista> obtenerExpositores(){
        DbArt dbHelper = new DbArt(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursorExpositores = db.rawQuery("SELECT * FROM " + TABLE_ARTISTAS, null);

        DynamicUnsortedList<Artista> expositores = new DynamicUnsortedList<Artista>();

        for (int i = 0; i < cursorExpositores.getCount(); i++) {
            if (cursorExpositores.moveToFirst()) {
                do {
                    Artista expositor = new Artista(cursorExpositores.getInt(0), cursorExpositores.getString(1), cursorExpositores.getString(2), cursorExpositores.getString(5), cursorExpositores.getInt(3), cursorExpositores.getInt(4));
                    expositores.insert(expositor);
                } while (cursorExpositores.moveToNext());
            }
        }

        db.close();

        return expositores;

    }
    public boolean eliminarExpositor(String correoExpositor) {
        boolean eliminado = false;
        SQLiteDatabase db = getWritableDatabase();
        try {
            int rowsAffected = db.delete(TABLE_ARTISTAS, "correoArtista = ?", new String[]{correoExpositor});
            eliminado = (rowsAffected > 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return eliminado;
    }

    public long agregarExpositor(String nombresExpositor, String correoExpositor,  String contrasenaExpositor,int localidadDeEventoExpositor, int tipoDeEventoExpositor) {
        long id = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombresArtista", nombresExpositor);
            values.put("correoArtista", correoExpositor);
            values.put("tipoDeEventoArtista", tipoDeEventoExpositor);
            values.put("contrasenaArtista", contrasenaExpositor);
            values.put("localidadEventoArtista", localidadDeEventoExpositor);

            id = db.insert(TABLE_ARTISTAS, null, values);

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }
    public boolean actualizarExpositor(String correoInicial, String nombresExpositor, String correoExpositor,  String contrasenaExpositor,int localidadDeEventoExpositor, int tipoDeEventoExpositor) {
        boolean correcto;

        DbArt dbHelper = new DbArt(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombresArtista", nombresExpositor);
            values.put("correoArtista", correoExpositor);
            values.put("tipoDeEventoArtista", tipoDeEventoExpositor);
            values.put("contrasenaArtista", contrasenaExpositor);
            values.put("localidadEventoArtista", localidadDeEventoExpositor);

            int rowsAffected = db.update(TABLE_ARTISTAS, values, "correoArtista = ?", new String[]{correoInicial});

            correcto = (rowsAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public LinkedList<String> obtenerCorreosElectronicosExpositores() {
        SQLiteDatabase db = getWritableDatabase();

        LinkedList<String> correos = new LinkedList<>();

        Cursor cursorCorreos = db.rawQuery("SELECT correoArtista FROM " + TABLE_ARTISTAS, null);

        if (cursorCorreos.moveToFirst()) {
            do {
                correos.pushBack(cursorCorreos.getString(0));
            } while (cursorCorreos.moveToNext());
        }

        cursorCorreos.close();
        db.close();

        return correos;
    }


    public Artista verUsuarioExpositor(String correoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        Artista ArtistasInfo = null;
        Cursor cursorArtistas;

        cursorArtistas = db.rawQuery("SELECT * FROM " + TABLE_ARTISTAS + " WHERE correoArtista = ? LIMIT 1", new String[]{correoUsuario});

        if (cursorArtistas.moveToFirst()) {
            ArtistasInfo = new Artista();
            ArtistasInfo.setNombreArtista(cursorArtistas.getString(1));
            ArtistasInfo.setCorreoElectronico(cursorArtistas.getString(2));
            ArtistasInfo.setContrasena(cursorArtistas.getString(5));
        }
        cursorArtistas.close();

        db.close();

        return ArtistasInfo;
    }

    public void guardarExpositores(){

        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_ARTISTAS, null, null);
        db.close();

        int veces = Bocu.expositores.size();

        Artista artista;

        for(int i = 0; i < veces; i++) {
            artista = Bocu.expositores.get(i);
            agregarExpositor(artista.getNombreArtista(), artista.getCorreoElectronico(), artista.getContrasena(), artista.getLocalidad(), artista.getTipoDeEvento());
        }

    }

}