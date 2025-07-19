package com.mihtan.mailtester;

/**
 * Modelo que representa un adjunto.
 *
 * @author Herman Barrantes
 * @since 2015-01-08
 */
public class Adjunto {

    private byte[] contenido;
    private String nombre;
    private String descripcion;
    private String mimeType;

    public Adjunto() {
    }

    public Adjunto(byte[] contenido, String nombre, String mimeType) {
        this.contenido = contenido;
        this.nombre = nombre;
        this.mimeType = mimeType;
    }

    public Adjunto(byte[] contenido, String nombre, String descripcion, String mimeType) {
        this.contenido = contenido;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mimeType = mimeType;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

}
