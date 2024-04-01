package com.example.datastructureproject_groupb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.LinkedList;
import com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos.StaticUnsortedList;
import com.example.datastructureproject_groupb.entidades.Usuarios;

public class DbUsuarios extends DbArt {

    Context context;
    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long agregarUsuario(String nombresUsuario, String apellidosUsuario, int edadUsuario, String correoUsuario, String contrasenaUsuario, int localidad, int intereses) {
        long id = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();

            Cursor cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS, null);

            StaticUnsortedList<Usuarios> nuevosUsuarios = new StaticUnsortedList<>(cursorUsuarios.getCount() + 1);

            nuevosUsuarios.insert(new Usuarios(0, nombresUsuario, apellidosUsuario, edadUsuario, correoUsuario, contrasenaUsuario, localidad, intereses));

            if (cursorUsuarios.moveToFirst()) {
                do {
                    Usuarios usuario = new Usuarios(cursorUsuarios.getInt(0), cursorUsuarios.getString(1), cursorUsuarios.getString(2), cursorUsuarios.getInt(3), cursorUsuarios.getString(4), cursorUsuarios.getString(5), cursorUsuarios.getInt(6), cursorUsuarios.getInt(7));
                    nuevosUsuarios.insert(usuario);
                } while (cursorUsuarios.moveToNext());
            }

            db.delete(TABLE_USUARIOS, null, null);

            ContentValues values;

            for (int i = 0; i < nuevosUsuarios.size(); i++) {
                values = new ContentValues();
                Usuarios usuarioAlt = nuevosUsuarios.get(i);
                values.put("nombresUsuario", usuarioAlt.getNombres());
                values.put("apellidosUsuario", usuarioAlt.getApellidos());
                values.put("edadUsuario", usuarioAlt.getEdad());
                values.put("correoUsuarioUsuarios", usuarioAlt.getCorreoElectronico());
                values.put("contrasenaUsuario", usuarioAlt.getContrasena());
                values.put("localidadUsuario", usuarioAlt.getLocalidad());
                values.put("interesesUsuario", usuarioAlt.getIntereses());
                id = db.insert(TABLE_USUARIOS, null, values);
            }

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

    /*
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
     */



    public LinkedList<String> obtenerCorreosElectronicos(){

        SQLiteDatabase db = this.getWritableDatabase();

        LinkedList<String> correos = new LinkedList<>();

        Cursor cursorCorreos = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS, null);


        if (cursorCorreos.moveToFirst()) {
            do {
                correos.pushFront(cursorCorreos.getString(4));

            } while (cursorCorreos.moveToNext());
        }
        cursorCorreos.close();
        db.close();

        return correos;

    }

}