package com.example.datastructureproject_groupb.db;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.datastructureproject_groupb.Bocu;
import com.example.datastructureproject_groupb.PaginaInicio;
import com.example.datastructureproject_groupb.entidades.Artista;
import com.example.datastructureproject_groupb.entidades.InfoSesion;
import com.example.datastructureproject_groupb.entidades.UsuarioComun;
import com.example.datastructureproject_groupb.entidades.UsuarioRegistrado;
import com.example.datastructureproject_groupb.fragmentos.CuentaFragment;

public class DbSesion extends DbArt{
    Context context;

    public DbSesion(Context context) {
        super(context);
        this.context=context;
    }
    public void mantenerSesionIniciada(int tipoSesion, String correoSesion){
        InfoSesion infoSesion = new InfoSesion(0, tipoSesion, correoSesion);
        infoSesion.setTipoSesion(tipoSesion);
        infoSesion.setCorreoSesion(correoSesion);
        insertarInfoSesion(tipoSesion, correoSesion);
    }

    public void cerrarSesion(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFO_SESION, null, null);
        db.close();
        Bocu.usuario = null;
        Bocu.estadoUsuario = Bocu.SIN_REGISTRAR;
        this.getWritableDatabase().close();
        Intent intent = new Intent(context, PaginaInicio.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
    public void insertarInfoSesion(int tipoSesion, String correoSesion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tipoSesion", tipoSesion);
        values.put("CorreoSesion", correoSesion);

        long resultado = db.insert(TABLE_INFO_SESION, null, values);



        db.close();
    }

    public boolean verificarSesionActiva(){
        DbArt dbHelper = new DbArt(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorInfo = db.rawQuery("SELECT * FROM " + TABLE_INFO_SESION, null);
        boolean sesionActiva = false;
        if (cursorInfo.moveToFirst()) {
            InfoSesion infoSesion = new InfoSesion(0, cursorInfo.getInt(1), cursorInfo.getString(2));
            sesionActiva = (infoSesion.getTipoSesion() != 0);
        }
        cursorInfo.close();
        return sesionActiva;
    }

    public void sesionActiva(){
        String correo="";
        DbArt dbHelper = new DbArt(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorInfo = db.rawQuery("SELECT * FROM " + TABLE_INFO_SESION, null);
        if (cursorInfo.moveToFirst()) {
            InfoSesion infoSesion = new InfoSesion(0, cursorInfo.getInt(1), cursorInfo.getString(2));
            DbExpositor dbExpositor= new DbExpositor(context);
            DbUsuariosComunes dbUsuariosComunes= new DbUsuariosComunes(context);
            Bocu bocu= new Bocu();
            CuentaFragment cuentaFragment = new CuentaFragment();
            correo=cursorInfo.getString(2);
            if (verificarSesionActiva()) {
                if (infoSesion.getTipoSesion() == 1) {
                    UsuarioRegistrado usuarioRegistrado = dbUsuariosComunes.verUsuario(correo);
                    bocu.usuario = usuarioRegistrado;
                    bocu.estadoUsuario = 1;
                    cuentaFragment.acceder(usuarioRegistrado, 1);
                } else {
                    Artista artista = dbExpositor.verUsuarioExpositor(correo);
                    bocu.usuario = artista;
                    bocu.estadoUsuario = 2;
                    cuentaFragment.acceder(artista, 2);
                }
            }
        }
        cursorInfo.close();
    }
    }

