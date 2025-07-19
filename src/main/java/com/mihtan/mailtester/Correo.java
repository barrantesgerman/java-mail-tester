package com.mihtan.mailtester;

import jakarta.activation.DataHandler;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import java.util.ArrayList;
import java.util.List;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import java.nio.charset.StandardCharsets;

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

            MimeMessage message = new MimeMessage(session);
            message.setSubject(getAsunto(), StandardCharsets.UTF_8.name());
            //Agregar los contactos
            for (Contacto contacto : getDestinatarios()) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(contacto.getCorreo()));
            }
            //Agregar los con copia
            for (Contacto copia : getConCopia()) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(copia.getCorreo()));
            }
            //Agregar los con copia oculta
            for (Contacto copiaOculta : getConCopiaOculta()) {
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(copiaOculta.getCorreo()));
            }

            // Mensaje HTML
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(getMensaje(), "text/html");

            // Se adjuntan los archivos
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            //Agrega los adjuntos
            for (Adjunto adjunto : getAdjuntos()) {
                BodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(adjunto.getContenido(), adjunto.getMimeType())));
                attachmentBodyPart.setFileName(adjunto.getNombre());
                multipart.addBodyPart(attachmentBodyPart);
            }
            // Obtiene mensaje html y archivos adjuntos
            message.setContent(multipart);
            // Enviar el correo
            Transport.send(message);
        } else {
            throw new RuntimeException("Error al enviar correo.");
        }
    }
}
