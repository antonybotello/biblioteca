package com.usta.model;

enum TipoDocumento{
    CC,
    TI,
    CE,
    PP
}
public class Usuario {
    private String correoElectronico;
    private String nombre;
    private String apellido;
    private String documento;
    private TipoDocumento tipoDocumento;


    
    public Usuario() {
    }
    
    public Usuario(String correoElectronico, String nombre, String apellido, String documento,
            TipoDocumento tipoDocumento) {
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
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
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    


}
  