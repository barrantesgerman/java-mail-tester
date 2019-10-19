package com.hbv.mail.tester.model;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.mail.DefaultAuthenticator;

/**
 * Modelo que representa una configuración de correo.
 *
 * @author Herman Barrantes
 * @since 2015-01-08
 */
public class Configuracion {

    private boolean autenticacion = true;
    private String usuario = "<user>@gmail.com";
    private String clave = "";
    private boolean esLocal = true;
    private String jndi = "mail/app";
    private String propiedades
            = "mail.smtp.host=smtp.gmail.com\n"
            + "mail.smtp.port=587\n"
            + "mail.smtp.user=<user>@gmail.com\n"
            + "mail.smtp.password=<password>\n"
            + "mail.smtp.from=<user>@gmail.com\n"
            + "mail.smtp.auth=true\n"
            + "mail.smtp.starttls.enable=true\n"
            + "mail.smtp.ssl.trust=smtp.gmail.com\n"
            + "mail.debug=true\n"
            + "mail.smtp.timeout=10000\n"
            + "mail.smtp.connectiontimeout=10000\n"
            + "mail.smtp.writetimeout=10000";

    public Configuracion() {
    }

    public boolean isAutenticacion() {
        return autenticacion;
    }

    public void setAutenticacion(boolean autenticacion) {
        this.autenticacion = autenticacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isEsLocal() {
        return esLocal;
    }

    public void setEsLocal(boolean esLocal) {
        this.esLocal = esLocal;
    }

    public String getJndi() {
        return jndi;
    }

    public void setJndi(String jndi) {
        this.jndi = jndi;
    }

    public String getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(String propiedades) {
        this.propiedades = propiedades;
    }

    public Session getSession() throws Exception {
        return esLocal ? cargarLocal() : cargarRemota();
    }

    private Session cargarLocal() throws IOException {
        final Properties properties = new Properties();
        properties.load(new StringReader(propiedades));
        //se ingresa la seguridad si se requiere
        if (autenticacion) {
            DefaultAuthenticator authenticator = new DefaultAuthenticator(usuario, clave);
            return Session.getInstance(properties, authenticator);
        } else {
            return Session.getInstance(properties);
        }
    }

    private Session cargarRemota() throws NamingException {
        final InitialContext ic = new InitialContext();
        return (Session) ic.lookup(jndi);
    }
}
