package com.example.datastructureproject_groupb.entidades.info_sesion;

public abstract class UsuarioRegistrado {

    private long id;
    private String correoElectronico, contrasena;
    private int localidad;

    public UsuarioRegistrado(long id, String correoElectronico, String contrasena, int localidad) {
        this.id = id;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.localidad = localidad;
    }

    public UsuarioRegistrado(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setLocalidad(int localidad){
        this.localidad = localidad;
    }

    public int getLocalidad(){
        return localidad;
    }

}
