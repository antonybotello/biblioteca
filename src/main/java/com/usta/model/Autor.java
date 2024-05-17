package com.usta.model;

public class Autor {
    private int id;
    private String nombre;
    private String apellido;
    private Pais nacionalidad;
    private int anioNacimiento;

    public Autor() {
    }

   
    public Autor(String nombre, String apellido, Pais nacionalidad, int anioNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.anioNacimiento = anioNacimiento;
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


}
