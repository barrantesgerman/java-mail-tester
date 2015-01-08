package com.hbv.mail.tester;

import com.hbv.mail.tester.model.Configuracion;
import com.hbv.mail.tester.model.Contacto;
import com.hbv.mail.tester.model.Correo;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "correoBean")
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
