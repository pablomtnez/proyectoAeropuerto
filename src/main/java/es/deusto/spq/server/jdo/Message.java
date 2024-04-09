package es.deusto.spq.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import java.util.Date;

@PersistenceCapable
public class Message {
    Usuario usuario = null;
    String text = null;
    long timestamp;

    public Message(String text) {
        this.text = text;
        this.timestamp = System.currentTimeMillis();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String toString() {
        return "Message: message --> " + this.text + ", timestamp -->  " + new Date(this.timestamp);
    }
}
