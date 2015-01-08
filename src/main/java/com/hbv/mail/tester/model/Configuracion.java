package com.hbv.mail.tester.model;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.mail.DefaultAuthenticator;

/**
 * Modelo que representa una configuraciòn de correo.
 *
 * @author Herman Barrantes
 * @since 2015-01-08
 */
public class Configuracion {

    private boolean autenticacion;
    private String usuario;
    private String clave;
    private boolean esLocal;
    private String jndi;
    private String propiedades;
    private Session session;

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
        //Se obtiene la session
        if (esLocal) {
            session = cargarLocal();
        } else {
            session = cargarRemota();
        }
        return session;
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
