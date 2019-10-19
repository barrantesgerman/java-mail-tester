package com.hbv.mail.tester.model;

import java.util.ArrayList;
import java.util.List;
import javax.mail.Session;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.HtmlEmail;

/**
 * Modelo que representa un correo.
 *
 * @author Herman Barrantes
 * @since 2015-01-08
 */
public class Correo {

    private String asunto = "";
    private String mensaje = "";
    private List<Contacto> destinatarios = new ArrayList<Contacto>();
    private List<Contacto> conCopia = new ArrayList<Contacto>();
    private List<Contacto> conCopiaOculta = new ArrayList<Contacto>();
    private List<Adjunto> adjuntos = new ArrayList<Adjunto>();
    private Configuracion configuracion = new Configuracion();

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Contacto> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<Contacto> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public List<Contacto> getConCopia() {
        return conCopia;
    }

    public void setConCopia(List<Contacto> conCopia) {
        this.conCopia = conCopia;
    }

    public List<Contacto> getConCopiaOculta() {
        return conCopiaOculta;
    }

    public void setConCopiaOculta(List<Contacto> conCopiaOculta) {
        this.conCopiaOculta = conCopiaOculta;
    }

    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * Envia el correo.
     * <pre>
     *     <code>
     *         Documentos de referencia
     *         http://openejb.979440.n4.nabble.com/javax-mail-Session-resource-and-smtp-authentication-td4322915.html
     *         http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
     *         http://commons.apache.org/proper/commons-email/
     *     </code>
     * </pre>
     *
     * @throws Exception Si ocurre un error al enviar
     * @author Herman Barrantes
     * @since 2014-05-26
     */
    public void enviar() throws Exception {
        if (!getAsunto().trim().isEmpty()
                && !getMensaje().trim().isEmpty()
                && (!getDestinatarios().isEmpty()
                || !getConCopia().isEmpty()
                || !getConCopiaOculta().isEmpty())) {

            Session session = getConfiguracion().getSession();

            HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setMailSession(session);
            //Agregar los contactos
            for (Contacto destinatario : getDestinatarios()) {
                htmlEmail.addTo(destinatario.getCorreo(), destinatario.getNombre());
            }
            //Agregar los con copia
            for (Contacto copia : getConCopia()) {
                htmlEmail.addCc(copia.getCorreo(), copia.getNombre());
            }
            //Agregar los con copia oculta
            for (Contacto copiaOculta : getConCopiaOculta()) {
                htmlEmail.addBcc(copiaOculta.getCorreo(), copiaOculta.getNombre());
            }
            //Agrega los adjuntos
            for (Adjunto adjunto : getAdjuntos()) {
                htmlEmail.attach(new ByteArrayDataSource(
                        adjunto.getContenido(), adjunto.getMimeType()),
                        adjunto.getNombre(),
                        adjunto.getDescripcion());
            }
            //Agregar el asunto y cuerpo del mensaje
            htmlEmail.setSubject(getAsunto());
            htmlEmail.setHtmlMsg(getMensaje());
            htmlEmail.setTextMsg(getMensaje());

            htmlEmail.send();
        } else {
            throw new RuntimeException("Error al enviar correo.");
        }
    }
}
