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
    private Aeropuerto origen;

    @Persistent
    private Aeropuerto destino;

    @Persistent
    private Aerolinea aerolinea;

    @Persistent
    private Avion avion;

    @Persistent
    private List<Reserva> reservas;

    @Persistent
    private int duracion;

    @Persistent
    private int seats;

    @Persistent
    private float precio;

    @Persistent(mappedBy = "vuelo", dependentElement = "true")
    @Join
    Set<Message> messages = new HashSet<>();

    // Constructor sin argumentos
    public Vuelo() {

    }

    // Constructor con todos los atributos
    public Vuelo(String code, Aeropuerto origen, Aeropuerto destino, Aerolinea aerolinea, Avion avion, List<Reserva> reservas, int duracion, int seats, float precio) {
        this.code = code;
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.avion = avion;
        this.reservas = reservas;
        this.duracion = duracion;
        this.seats = seats;
        this.precio = precio;
    }

    // Getters y Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public void setOrigen(Aeropuerto origen) {
        this.origen = origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public void setDestino(Aeropuerto destino) {
        this.destino = destino;
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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
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
       return "Vuelo: Code --> " + this.code + ", Origen --> " + this.origen + ", Destino --> " + this.destino + ", Aerolinea --> " + this.aerolinea + ", Avion --> " + this.avion + ", Reservas --> " + this.reservas + ", Duracion --> " + this.duracion + ", Asientos --> " + this.seats + ", Precio --> " + this.precio +  ", messages --> [" + messagesStr + "]"; 
    }
}