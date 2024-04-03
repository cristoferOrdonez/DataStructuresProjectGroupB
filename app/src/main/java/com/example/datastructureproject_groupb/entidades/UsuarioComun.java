package com.example.datastructureproject_groupb.entidades;

public class UsuarioComun extends UsuarioRegistrado{

    private String nombres, apellidos;
    private int edad, intereses;

    public UsuarioComun(long id, String nombres, String apellidos, int edad, String correoElectronico, String contrasena, int localidad, int intereses) {
        super(id, correoElectronico, contrasena, localidad);
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.intereses= intereses;
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

}
