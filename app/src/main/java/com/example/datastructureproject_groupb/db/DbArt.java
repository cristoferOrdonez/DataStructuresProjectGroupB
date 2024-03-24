package com.example.datastructureproject_groupb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbArt extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
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
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombresUsuario TEXT NOT NULL, " +
                "apellidosUsuario TEXT NOT NULL, " +
                "edadUsuario INTEGER NOT NULL, " +
                "correoUsuarioUsuarios TEXT NOT NULL," +
                "localidadUsuario INTEGER NOT NULL," +
                "interesesUsuario INTEGER NOT NULL," +
                "contrasenaUsuario TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ARTISTAS + "(" +
                "idArista INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombresArtista TEXT NOT NULL, " +
                "correoArtista TEXT NOT NULL," +
                "tipoDeEventoArtista INTEGER NOT NULL," +
                "contrasenaArtista TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_EVENTOS + "(" +
                "idEvento INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreEvento TEXT NOT NULL, " +
                "fechaEvento DATE NOT NULL, " +
                "ubicacionEvento TEXT NOT NULL, " +
                "localidadEvento INTEGER NOT NULL, " +
                "costoEvento INTEGER NOT NULL, " +
                "horarioEvento TIME NOT NULL, " +
                "categoriaEvento INTEGER NOT NULL, " +
                "descripcionEvento TEXT NOT NULL)");



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