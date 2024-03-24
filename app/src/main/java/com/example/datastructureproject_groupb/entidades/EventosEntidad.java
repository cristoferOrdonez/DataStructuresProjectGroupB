package com.example.datastructureproject_groupb.entidades;

import android.widget.TimePicker;

import java.util.Date;

public class EventosEntidad {
    int id;
    String nombreEvento;
    Date fechaEvento;
    String ubicacionEvento;
    int localidadEvento;
    int costoEvento;


    //Time horarioEvento;


    int categoriaEvento;
    String descripcionEvento;

    public EventosEntidad(int id,String nombreEvento,Date fechaEvento,String ubicacionEvento, int localidadEvento, int costoEvento,    int categoriaEvento,String descripcionEvento){
        this.id=id;
        this.nombreEvento=nombreEvento;
        this.fechaEvento=fechaEvento;
        this.ubicacionEvento=ubicacionEvento;
        this.localidadEvento=localidadEvento;
        this.costoEvento=costoEvento;
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




}
