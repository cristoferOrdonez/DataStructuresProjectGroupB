package com.example.datastructureproject_groupb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.datastructureproject_groupb.entidades.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class DbUsuarios extends DbArt {

    Context context;
    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long agregarUsuario(String nombresUsuario, String apellidosUsuario, int edadUsuario, String correoUsuario, String contrasenaUsuario, int localidad, int intereses) {
        long id = 0;
        try {
            DbArt dbHelper = new DbArt(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombresUsuario", nombresUsuario);
            values.put("apellidosUsuario", apellidosUsuario);
            values.put("edadUsuario", edadUsuario);
            values.put("correoUsuarioUsuarios", correoUsuario);
            values.put("contrasenaUsuario", contrasenaUsuario);
            values.put("localidadUsuario", localidad);
            values.put("interesesUsuario", intereses);


            id = db.insert(TABLE_USUARIOS, null, values);

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;

    }

    public Usuarios verUsuario(String correoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        Usuarios UsuarioInfo = null;
        Cursor cursorUsuarios;

        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE correoUsuarioUsuarios = ? LIMIT 1", new String[]{correoUsuario});

        if (cursorUsuarios.moveToFirst()) {
            UsuarioInfo = new Usuarios(cursorUsuarios.getInt(0), cursorUsuarios.getString(1), cursorUsuarios.getString(2),cursorUsuarios.getInt(3), cursorUsuarios.getString(4), cursorUsuarios.getString(5), cursorUsuarios.getInt(6), cursorUsuarios.getInt(7));
        }
        cursorUsuarios.close();

        db.close();

        return UsuarioInfo;
    }

    public boolean actualizarUsuario(String correoInicial, String nombresUsuario, String apellidosUsuario, int edadUsuario, String correoUsuario, String contrasenaUsuario, int localidad, int intereses) {
        boolean correcto;

        DbArt dbHelper = new DbArt(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombresUsuario", nombresUsuario);
            values.put("apellidosUsuario", apellidosUsuario);
            values.put("edadUsuario", edadUsuario);
            values.put("correoUsuarioUsuarios", correoUsuario);
            values.put("contrasenaUsuario", contrasenaUsuario);
            values.put("localidadUsuario", localidad);
            values.put("interesesUsuario", intereses);

            int rowsAffected = db.update(TABLE_USUARIOS, values, "correoUsuarioUsuarios = ?", new String[]{correoInicial});

            correcto = (rowsAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }



    public List<String> obtenerCorreosElectronicos(){

        SQLiteDatabase db = this.getWritableDatabase();

        List<String> correos = new ArrayList<>();

        Cursor cursorCorreos = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS, null);


        if (cursorCorreos.moveToFirst()) {
            do {
                correos.add(cursorCorreos.getString(4));

            } while (cursorCorreos.moveToNext());
        }
        cursorCorreos.close();
        db.close();

        return correos;

    }

}