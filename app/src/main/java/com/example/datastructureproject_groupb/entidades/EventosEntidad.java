package com.example.datastructureproject_groupb.entidades;

import android.widget.TimePicker;

import org.intellij.lang.annotations.JdkConstants;

import java.sql.Time;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class EventosEntidad {

    // atributos

    // constantes
    private static final int USAQUÉN = 1;
    private static final int CHAPINERO = 2;
    private static final int SANTA_FE = 3;
    private static final int SAN_CRISTÓBAL = 4;
    private static final int USME = 5;
    private static final int TUNJUELITO = 6;
    private static final int BOSA = 7;
    private static final int KENNEDY = 8;
    private static final int FONTIBÓN = 9;
    private static final int ENGATIVÁ = 10;
    private static final int SUBA = 11;
    private static final int BARRIOS_UNIDOS = 12;
    private static final int TEUSAQUILLO = 13;
    private static final int LOS_MÁRTIRES = 14;
    private static final int ANTONIO_NARIÑO = 15;
    private static final int PUENTE_ARANDA = 16;
    private static final int CANDELARIA = 17;
    private static final int RAFAEL_URIBE_URIBE = 18;
    private static final int CIUDAD_BOLÍVAR = 19;
    private static final int SUMAPAZ = 20;

    private int id;
    private String nombreEvento;
    private Date fechaEvento;
    private String ubicacionEvento;
    private int localidadEvento;
    private int costoEvento;
    private String horarioEvento;
    private int categoriaEvento;
    private String descripcionEvento;
    public EventosEntidad(int id,String nombreEvento,Date fechaEvento,String ubicacionEvento, int localidadEvento, int costoEvento, String horarioEvento, int categoriaEvento, String descripcionEvento){
        this.id=id;
        this.nombreEvento=nombreEvento;
        this.fechaEvento=fechaEvento;
        this.ubicacionEvento=ubicacionEvento;
        this.localidadEvento=localidadEvento;
        this.costoEvento=costoEvento;
        this.horarioEvento = horarioEvento;
        this.categoriaEvento=categoriaEvento;
        this.descripcionEvento=descripcionEvento;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getUbicacionEvento() {
        return ubicacionEvento;
    }

    public void setUbicacionEvento(String ubicacionEvento) {
        this.ubicacionEvento = ubicacionEvento;
    }

    public int getLocalidadEvento() {
        return localidadEvento;
    }

    public void setLocalidadEvento(int localidadEvento) {
        this.localidadEvento = localidadEvento;
    }

    public int getCostoEvento() {
        return costoEvento;
    }

    public String getCostoEventoConFormato(){

        if(costoEvento == 0)
            return "Gratuito";

        return NumberFormat.getCurrencyInstance(new Locale("es", "CO")).format(costoEvento) + " COP";

    }

    public void setCostoEvento(int costoEvento) {
        this.costoEvento = costoEvento;
    }

    public int getCategoriaEvento() {
        return categoriaEvento;
    }

    public void setCategoriaEvento(int categoriaEvento) {
        this.categoriaEvento = categoriaEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getHorarioEvento(){

        return horarioEvento;

    }

    public String getFechaEventoString(){
        return fechaEvento.getYear() + "/" + fechaEvento.getMonth() + "/" + fechaEvento.getDate();
    }

}
