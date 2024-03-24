package com.example.datastructureproject_groupb.entidades;

public class Usuarios {
    private long id;
    private String nombres;
    private String apellidos;
    private int edad;
    private String correoElectronico;
    private String contrasena;



    private int localidad;
    private int intereses;

    public Usuarios(long id, String nombres, String apellidos, int edad, String correoElectronico, String contrasena, int localidad, int intereses) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.intereses= intereses;
        this.localidad= localidad;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getEdad() {
        return edad;
    }//

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }
    public int getLocalidad() {
        return localidad;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    public int getIntereses() {
        return intereses;
    }

    public void setIntereses(int intereses) {
        this.intereses = intereses;
    }

}
