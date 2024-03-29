package com.example.datastructureproject_groupb.entidades;

public class Artistas {
    private long id;
    private String nombreArtista;
    private String correoElectronico;
    private String contrasena;
    private int tipoDeEvento;

    public Artistas(long id, String nombreArtista,  String correoElectronico, String contrasena, int tipoDeEvento) {
        this.id = id;
        this.nombreArtista = nombreArtista;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.tipoDeEvento= tipoDeEvento;
    }

    public Artistas(){

    }


    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
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


    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
