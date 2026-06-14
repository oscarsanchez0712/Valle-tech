package com.valletech.model;

public class Usuario {
    private int id;
    private String usuario;
    private String nombreCompleto;

    public Usuario() {}

    public Usuario(int id, String usuario, String nombreCompleto) {
        this.id = id;
        this.usuario = usuario;
        this.nombreCompleto = nombreCompleto;
    }

    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }

    public String getUsuario()                  { return usuario; }
    public void setUsuario(String usuario)      { this.usuario = usuario; }

    public String getNombreCompleto()                       { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto)    { this.nombreCompleto = nombreCompleto; }
}
