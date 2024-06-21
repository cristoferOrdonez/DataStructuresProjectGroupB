package com.example.datastructureproject_groupb.entidades.artista;

import com.example.datastructureproject_groupb.entidades.info_sesion.UsuarioRegistrado;

public class Artista extends UsuarioRegistrado {

    private String nombreArtista;
    private int tipoDeEvento;

    public Artista(long id, String nombreArtista, String correoElectronico, String contrasena, int tipoDeEvento, int localidad) {
        super(id, correoElectronico, contrasena, localidad);
        this.nombreArtista = nombreArtista;
        this.tipoDeEvento= tipoDeEvento;
    }

    public Artista(){
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public int getTipoDeEvento() {
        return tipoDeEvento;
    }

    public void setTipoDeEvento(int tipoDeEvento) {
        this.tipoDeEvento = tipoDeEvento;
    }

}
