package es.deusto.spq.server.jdo;
/* 
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Join;
*/
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import es.deusto.spq.client.domain.Country;

@PersistenceCapable(detachable = "true")
public class Airport {
    @PrimaryKey
    private String code;

    @Persistent
    private String name;

    @Persistent
    private String city;

    @Persistent
    private Country pais;

    //@Persistent(mappedBy = "aeropuerto", dependentElement = "true")
    //@Join
    //Set<Message> messages = new HashSet<>();

    // Constructor sin argumentos
    public Airport() {

    }

    // Constructor con todos los atributos
    public Airport(String code, String name, String city, Country pais) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.pais = pais;
    }

    // Getters y Setters

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getPais() {
        return pais;
    }

    public void setPais(Country pais) {
        this.pais = pais;
    }
/* 
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
       return "Aeropuerto: Code --> " + this.code + ", Nombre --> " + this.name + ", Pais --> " + this.pais + ", Ciudad --> " + this.city  + ", messages --> [" + messagesStr + "]"; 
    }
    */
}