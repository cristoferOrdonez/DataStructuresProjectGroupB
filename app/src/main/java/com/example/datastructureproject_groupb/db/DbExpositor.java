package com.example.datastructureproject_groupb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.datastructureproject_groupb.entidades.Artistas;
import com.example.datastructureproject_groupb.entidades.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class DbExpositor extends DbArt {
    Context context;

    public DbExpositor(Context context) {
        super(context);
        this.context=context;
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

            int rowsAffected = db.update(TABLE_USUARIOS, values, "correoArtista = ?", new String[]{correoInicial});

            correcto = (rowsAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public List<String> obtenerCorreosElectronicosExpositores() {
        SQLiteDatabase db = getWritableDatabase();

        List<String> correos = new ArrayList<>();

        Cursor cursorCorreos = db.rawQuery("SELECT correoArtista FROM " + TABLE_ARTISTAS, null);

        if (cursorCorreos.moveToFirst()) {
            do {
                correos.add(cursorCorreos.getString(0));
            } while (cursorCorreos.moveToNext());
        }

        cursorCorreos.close();
        db.close();

        return correos;
    }


    public Artistas verUsuarioExpositor(String correoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        Artistas ArtistasInfo = null;
        Cursor cursorArtistas;

        cursorArtistas = db.rawQuery("SELECT * FROM " + TABLE_ARTISTAS + " WHERE correoArtista = ? LIMIT 1", new String[]{correoUsuario});

        if (cursorArtistas.moveToFirst()) {
            ArtistasInfo = new Artistas();
            ArtistasInfo.setCorreoElectronico(cursorArtistas.getString(2));
            ArtistasInfo.setContrasena(cursorArtistas.getString(5));
        }
        cursorArtistas.close();

        db.close();

        return ArtistasInfo;
    }
}
