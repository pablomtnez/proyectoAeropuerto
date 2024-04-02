package es.deusto.spq.server.jdo;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Usuario {
    @PrimaryKey
    private String dni;

    @Persistent
    private String username;

    @Persistent
    private String nombre;

    @Persistent
    private String email;

    @Persistent
    private String password;

    @Persistent(mappedBy = "usuario", dependentElement = "true")
    @Join
    Set<Message> messages = new HashSet<>();

    // Constructor sin argumentos
    public Usuario() {

    }

    // Constructor con todos los atributos
    public Usuario(String username, String nombre, String email, String dni, String password) {
        this.username = username;
        this.nombre = nombre;
        this.email = email;
        this.dni = dni;
        this.password = password;
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public void removeMessage(Message message){
        messages.remove(message);
    }

    public Set<Message> getMessages(){
        return this.messages;
    }

    public String toString() {
       StringBuilder messagesStr = new StringBuilder();
       for(Message message : this.messages){
        messagesStr.append(message.toString() + " - ");
       }
       return "User: Nombre --> " + this.nombre + ", DNI --> " + this.dni + ", Email --> " + this.email + ", Username --> " + this.username + ", Password --> " + this.password + ", messages --> [" + messagesStr + "]"; 
    }
}
