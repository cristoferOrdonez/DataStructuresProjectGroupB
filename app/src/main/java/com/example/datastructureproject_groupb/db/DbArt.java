package com.example.datastructureproject_groupb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbArt extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=3;
    private static final String DATABASE_NOMBRE = "datos.db";

    public static final String TABLE_USUARIOS = "t_usuarios";

    public static final String TABLE_EVENTOS ="t_eventos";

    public static final String TABLE_ARTISTAS="t_artistas";

    public DbArt(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIOS + "(" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT," + //0
                "nombresUsuario TEXT NOT NULL, " + //1
                "apellidosUsuario TEXT NOT NULL, " + //2
                "edadUsuario INTEGER NOT NULL, " + //3
                "correoUsuarioUsuarios TEXT NOT NULL," + //4
                "contrasenaUsuario TEXT NOT NULL,"+ //5
                "localidadUsuario INTEGER NOT NULL," + //6
                "interesesUsuario INTEGER NOT NULL)" ); //7

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ARTISTAS + "(" +
                "idArista INTEGER PRIMARY KEY AUTOINCREMENT," + //0
                "nombresArtista TEXT NOT NULL, " + //1
                "correoArtista TEXT NOT NULL," + //2
                "tipoDeEventoArtista INTEGER NOT NULL," + //3
                "localidadEventoArtista INTEGER NOT NULL," + //4
                "contrasenaArtista TEXT NOT NULL)"); //5

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_EVENTOS + "(" +
                "idEvento INTEGER PRIMARY KEY AUTOINCREMENT, " + //0
                "nombreEvento TEXT NOT NULL, " + //1
                "AnoEvento INTEGER NOT NULL, " + //2
                "mesEvento INTEGER NOT NULL, " + //3
                "diaEvento INTEGER NOT NULL, " + //4
                "ubicacionEvento TEXT NOT NULL, " + //5
                "localidadEvento INTEGER NOT NULL, " + //6
                "costoEvento INTEGER NOT NULL, " + //7
                "horarioEvento TEXT NOT NULL, " + //8
                "categoriaEvento INTEGER NOT NULL, " + //9
                "descripcionEvento TEXT NOT NULL," + //10
                "correoAutor TEXT NOT NULL)"); //11
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_EVENTOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTISTAS);

        onCreate(sqLiteDatabase);
    }

    public boolean actualizarCorreos(String correoAntiguo, String correoNuevo) {
        boolean correcto;

        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues valuesHistorico = new ContentValues(), valuesIngresos = new ContentValues(), valuesCatGastos = new ContentValues(), valuesGastos = new ContentValues(), valuesMetas = new ContentValues();
            valuesIngresos.put("correoUsuarioIngresos", correoNuevo.toLowerCase());
            valuesCatGastos.put("correocatgasto", correoNuevo.toLowerCase());
            valuesGastos.put("correogasto", correoNuevo.toLowerCase());
            valuesMetas.put("correoUsuarioMetas", correoNuevo.toLowerCase());
            valuesHistorico.put("correoUsuarioHistorico", correoNuevo.toLowerCase());

            int rowsAffectedMetas = db.update(TABLE_EVENTOS, valuesMetas, "correoUsuarioMetas = ?", new String[]{correoAntiguo});

            correcto = ( rowsAffectedMetas > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

}