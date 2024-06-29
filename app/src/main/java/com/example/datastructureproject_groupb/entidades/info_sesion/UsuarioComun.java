package com.example.datastructureproject_groupb.entidades.info_sesion;

public class UsuarioComun extends UsuarioRegistrado{

    private String nombres;
    private String apellidos;

    private String favoritos;
    private int edad, intereses;

    public UsuarioComun(long id, String nombres, String apellidos, int edad, String correoElectronico, String contrasena, int localidad, int intereses, String favoritos) {
        super(id, correoElectronico, contrasena, localidad);
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.intereses= intereses;
        this.favoritos = favoritos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIntereses() {
        return intereses;
    }

    public void setIntereses(int intereses) {
        this.intereses = intereses;
    }

    public String getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(String favoritos) {
        this.favoritos = favoritos;
    }

}
