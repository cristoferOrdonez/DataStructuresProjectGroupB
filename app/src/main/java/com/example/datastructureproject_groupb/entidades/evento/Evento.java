package com.example.datastructureproject_groupb.entidades.evento;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Evento {

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
    private static final int VIRTUAL = 21;

    private int ubicacionEvento, costoEvento, categoriaEvento;
    private long id;
    private String nombreEvento, direPlatEvento, horarioEvento, descripcionEvento, correoAutor;
    private Date fechaEvento;
    private Context context;

    public Evento(Context context, long id, String nombreEvento, Date fechaEvento, String direPlatEvento, int ubicacionEvento, int costoEvento, String horarioEvento, int categoriaEvento, String descripcionEvento, String correoAutor){
        this.context = context;
        this.id = id;
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.direPlatEvento = direPlatEvento;
        this.ubicacionEvento = ubicacionEvento;
        this.costoEvento = costoEvento;
        this.horarioEvento = horarioEvento;
        this.categoriaEvento =categoriaEvento;
        this.descripcionEvento = descripcionEvento;
        this.correoAutor = correoAutor;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getDirePlatEvento() {
        return direPlatEvento;
    }

    public String getDireccionEvento(){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> listaDireccion = null;
        try {
            listaDireccion = geocoder.getFromLocation(Double.parseDouble(direPlatEvento.substring(0, direPlatEvento.indexOf(" - "))), Double.parseDouble(direPlatEvento.substring(direPlatEvento.indexOf(" - ") + 3)), 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listaDireccion.get(0).getAddressLine(0).split(",")[0];
    }

    public void setDirePlatEvento(String direPlatEvento) {
        this.direPlatEvento = direPlatEvento;
    }

    public int getUbicacionEvento() {
        return ubicacionEvento;
    }

    public void setUbicacionEvento(int ubicacionEvento) {
        this.ubicacionEvento = ubicacionEvento;
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

    public int getAno(){
        return fechaEvento.getYear();
    }

    public int getMes(){
        return fechaEvento.getMonth();
    }

    public int getDia(){
        return fechaEvento.getDate();
    }

    public String getFechaEventoString(){
        return fechaEvento.getDate() + "/" + fechaEvento.getMonth() + "/" + fechaEvento.getYear();
    }

    public void setHorarioEvento(String horarioEvento){
        this.horarioEvento = horarioEvento;
    }

    public void setCorreoAutor(String correoAutor){
        this.correoAutor = correoAutor;
    }

    public String getCorreoAutor(){
        return correoAutor;
    }

}
