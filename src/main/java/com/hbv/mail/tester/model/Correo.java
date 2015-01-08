package com.hbv.mail.tester.model;

import java.util.ArrayList;
import java.util.List;
import javax.mail.Session;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.HtmlEmail;

/**
 * Modelo que representa un correo.
 *
 * @author Herman Barrantes
 * @since 2015-01-08
 */
public class Correo {

    private String asunto;
    private String mensajeHtml;
    private String mensajeText;
    private List<Contacto> destinatarios;
    private List<Contacto> conCopia;
    private List<Contacto> conCopiaOculta;
    private List<Adjunto> adjuntos;
    private Configuracion configuracion;

    public Correo() {
    }

    public String getAsunto() {
        if (null == asunto) {
            asunto = "";
        }
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensajeHtml() {
        if (null == mensajeHtml) {
            mensajeHtml = "";
        }
        return mensajeHtml;
    }

    public void setMensajeHtml(String mensajeHtml) {
        this.mensajeHtml = mensajeHtml;
    }

    public String getMensajeText() {
        if (null == mensajeText) {
            mensajeText = "";
        }
        return mensajeText;
    }

    public void setMensajeText(String mensajeText) {
        this.mensajeText = mensajeText;
    }

    public List<Contacto> getDestinatarios() {
        if (null == destinatarios) {
            destinatarios = new ArrayList<Contacto>();
        }
        return destinatarios;
    }

    public void setDestinatarios(List<Contacto> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public List<Contacto> getConCopia() {
        if (null == conCopia) {
            conCopia = new ArrayList<Contacto>();
        }
        return conCopia;
    }

    public void setConCopia(List<Contacto> conCopia) {
        this.conCopia = conCopia;
    }

    public List<Contacto> getConCopiaOculta() {
        if (null == conCopiaOculta) {
            conCopiaOculta = new ArrayList<Contacto>();
        }
        return conCopiaOculta;
    }

    public void setConCopiaOculta(List<Contacto> conCopiaOculta) {
        this.conCopiaOculta = conCopiaOculta;
    }

    public List<Adjunto> getAdjuntos() {
        if (null == adjuntos) {
            adjuntos = new ArrayList<Adjunto>();
        }
        return adjuntos;
    }

    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public Configuracion getConfiguracion() {
        if (null == configuracion) {
            configuracion = new Configuracion();
        }
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * Envia el correo.
     *
     * @throws Exception
     * @author Herman Barrantes
     * @since 2014-05-26
     * @see
     * http://openejb.979440.n4.nabble.com/javax-mail-Session-resource-and-smtp-authentication-td4322915.html
     * @see
     * http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
     * @see http://commons.apache.org/proper/commons-email/
     */
    public void enviar() throws Exception {
        if (!getAsunto().trim().equals("")
                && !getMensajeHtml().trim().equals("")
                && (!getDestinatarios().isEmpty()
                || !getConCopia().isEmpty()
                || !getConCopiaOculta().isEmpty())) {

            Session session = getConfiguracion().getSession();

            HtmlEmail he = new HtmlEmail();
            he.setMailSession(session);
            //Agregar los contactos
            for (Contacto destinatario : getDestinatarios()) {
                he.addTo(destinatario.getCorreo(),
                        destinatario.getNombre());
            }
            //Agregar los con copia
            for (Contacto copia : getConCopia()) {
                he.addCc(copia.getCorreo(),
                        copia.getNombre());
            }
            //Agregar los con copia oculta
            for (Contacto copiaOculta : getConCopiaOculta()) {
                he.addBcc(copiaOculta.getCorreo(),
                        copiaOculta.getNombre());
            }
            //Agrega los adjuntos
            for (Adjunto adjunto : getAdjuntos()) {
                he.attach(new ByteArrayDataSource(
                        adjunto.getContenido(), adjunto.getMimeType()),
                        adjunto.getNombre(),
                        adjunto.getDescripcion());
            }
            //Agregar el asunto y cuerpo del mensaje
            he.setSubject(getAsunto());
            he.setHtmlMsg(getMensajeHtml());
            he.setTextMsg(getMensajeText());

            he.send();
        } else {
            throw new RuntimeException("Error al enviar correo.");
        }
    }
}
