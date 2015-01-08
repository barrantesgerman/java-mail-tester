package com.hbv.mail.tester.model;

/**
 * Modelo que representa un contacto.
 *
 * @author Herman Barrantes
 * @since 2015-01-08
 */
public class Contacto {

    private String nombre;
    private String correo;

    public Contacto() {
    }

    public Contacto(String correo) {
        this.correo = correo;
    }

    public Contacto(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNombre() {
        if (null == nombre) {
            nombre = "";
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
