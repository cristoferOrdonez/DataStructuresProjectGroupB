package com.example.datastructureproject_groupb.entidades.info_sesion;

public class InfoSesion {
    private int  TipoSesion;
    private String CorreoSesion;

    public InfoSesion(long id, int tipoSesion, String correoSesion){
        TipoSesion=tipoSesion;
        CorreoSesion=correoSesion;
    };
    public int getTipoSesion() {
        return TipoSesion;
    }

    public void setTipoSesion(int tipoSesion) {
        this.TipoSesion = tipoSesion;
    }
    public String getCorreoSesion() {
        return CorreoSesion;
    }

    public void setCorreoSesion(String correoSesion) {
        CorreoSesion = correoSesion;
    }



    ;

}
