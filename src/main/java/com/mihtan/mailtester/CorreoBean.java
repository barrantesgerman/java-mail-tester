package com.mihtan.mailtester;

import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class CorreoBean implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(CorreoBean.class);
    private static final long serialVersionUID = 1L;

    private Correo correo;
    private Contacto contacto;

    public CorreoBean() {
    }

    @PostConstruct
    public void init() {
        correo = new Correo();
        contacto = new Contacto();
    }

    public Correo getCorreo() {
        return correo;
    }

    public void setCorreo(Correo correo) {
        this.correo = correo;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public String enviar() {
        correo.getDestinatarios().clear();
        correo.getDestinatarios().add(contacto);
        correo.getAdjuntos().clear();
        correo.getAdjuntos().add(new Adjunto("Contenido de prueba".getBytes(), "Adjunto.txt", "text/plain"));
        try {
            correo.enviar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Correo enviado Exitosamente."));
        } catch (Exception ex) {
            LOG.error("Ocurrio un error al enviar el correo.", ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getLocalizedMessage()));
        }
        return "";
    }

}
