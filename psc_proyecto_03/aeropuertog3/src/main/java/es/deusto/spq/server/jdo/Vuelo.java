package es.deusto.spq.server.jdo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import es.deusto.spq.client.domain.Aerolinea;
import es.deusto.spq.client.domain.Aeropuerto;
import es.deusto.spq.client.domain.Avion;
import es.deusto.spq.client.domain.Reserva;

@PersistenceCapable(detachable = "true")
public class Vuelo {
    @PrimaryKey
    private String code;

    @Persistent
    private Aeropuerto origin;

    @Persistent
    private Aeropuerto destination;

    @Persistent
    private Aerolinea aerolinea;

    @Persistent
    private Avion avion;

    @Persistent
    private List<Reserva> reservas;

    @Persistent
    private int duration;

    @Persistent
    private int seats;

    @Persistent
    private float price;

    @Persistent(mappedBy = "vuelo", dependentElement = "true")
    @Join
    Set<Message> messages = new HashSet<>();

    // Constructor sin argumentos
    public Vuelo() {

    }

    // Constructor con todos los atributos
    public Vuelo(String code, Aeropuerto origin, Aeropuerto destination, Aerolinea aerolinea, Avion avion, List<Reserva> reservas, int duration, int seats, float price) {
        this.code = code;
        this.origin = origin;
        this.destination = destination;
        this.aerolinea = aerolinea;
        this.avion = avion;
        this.reservas = reservas;
        this.duration = duration;
        this.seats = seats;
        this.price = price;
    }

    // Getters y Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Aeropuerto getOrigin() {
        return origin;
    }

    public void setOrigin(Aeropuerto origin) {
        this.origin = origin;
    }

    public Aeropuerto getDestination() {
        return destination;
    }

    public void setDestination(Aeropuerto destination) {
        this.destination = destination;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }    
    
    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }
    
    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
       return "Vuelo: Code --> " + this.code + ", Origen --> " + this.origin + ", Destino --> " + this.destination + ", Aerolinea --> " + this.aerolinea + ", Avion --> " + this.avion + ", Reservas --> " + this.reservas + ", Duracion --> " + this.duration + ", Asientos --> " + this.seats + ", Precio --> " + this.price +  ", messages --> [" + messagesStr + "]"; 
    }
}