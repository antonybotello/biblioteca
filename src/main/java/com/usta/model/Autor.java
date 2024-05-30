package com.usta.model;

import javafx.scene.control.RadioButton;

public class Autor {
    private int id;
    private String nombre;
    private String apellido;
    private Pais nacionalidad;
    private int anioNacimiento;
    private String documento;
    private String foto;

    public Autor() {
    }

   
    public Autor(String nombre, String apellido, Pais nacionalidad, int anioNacimiento, String documento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.anioNacimiento = anioNacimiento;
        this.documento=documento;
    }

    
    public Autor( String nombre, String apellido, Pais nacionalidad, int anioNacimiento, String documento, String foto) {
        
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.anioNacimiento = anioNacimiento;
        this.documento=documento;

        this.foto = foto;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Pais getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Pais nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido+" "+ id;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getFoto() {
        return foto;
    }


    public void setFoto(String foto) {
        this.foto = foto;
    }


    public String getDocumento() {
        return documento;
    }


    public void setDocumento(String documento) {
        this.documento = documento;
    }


}
