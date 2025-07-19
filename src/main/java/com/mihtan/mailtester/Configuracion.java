package com.mihtan.mailtester;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import jakarta.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
    private String propiedades = """
            mail.smtp.host=smtp.gmail.com
            mail.smtp.port=587
            mail.smtp.user=<user>@gmail.com
            mail.smtp.password=<password>
            mail.smtp.from=<user>@gmail.com
            mail.smtp.auth=true
            mail.smtp.starttls.enable=true
            mail.smtp.ssl.trust=smtp.gmail.com
            mail.debug=true
            mail.smtp.timeout=10000
            mail.smtp.connectiontimeout=10000
            mail.smtp.writetimeout=10000
            """;

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
            return Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuario, clave);
                }

            });
        } else {
            return Session.getInstance(properties);
        }
    }

    private Session cargarRemota() throws NamingException {
        final InitialContext ic = new InitialContext();
        return (Session) ic.lookup(jndi);
    }
}
