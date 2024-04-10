package es.deusto.spq.server.jdo;

//import java.util.HashSet;
import java.util.List;
//import java.util.Set;

//import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Date;

import es.deusto.spq.client.domain.Airline;
import es.deusto.spq.client.domain.Airport;
import es.deusto.spq.client.domain.Plane;
import es.deusto.spq.client.domain.Reservation;

@PersistenceCapable(detachable = "true")
public class Flight {
    @PrimaryKey
    private String code;

    @Persistent
    private Airport origen;

    @Persistent
    private Airport destino;

    @Persistent
    private Airline aerolinea;

    @Persistent
    private Plane avion;

    @Persistent
    private List<Reservation> reservas;

    @Persistent
    private int duracion;

    @Persistent
    private int seats;

    @Persistent
    private float precio;

    @Persistent
    private Date fecha;
/* 
    @Persistent(mappedBy = "flight", dependentElement = "true")
    @Join
    Set<Message> messages = new HashSet<>();
*/
    // Constructor sin argumentos
    public Flight() {

    }

    // Constructor con todos los atributos
    public Flight(String code, Airport origen, Airport destino, Airline aerolinea, Plane avion, List<Reservation> reservas, int duracion, int seats, float precio, Date fecha) {
        this.code = code;
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.avion = avion;
        this.reservas = reservas;
        this.duracion = duracion;
        this.seats = seats;
        this.precio = precio;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Airport getOrigen() {
        return origen;
    }

    public void setOrigen(Airport origen) {
        this.origen = origen;
    }

    public Airport getDestino() {
        return destino;
    }

    public void setDestino(Airport destino) {
        this.destino = destino;
    }

    public Airline getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Airline aerolinea) {
        this.aerolinea = aerolinea;
    }    
    
    public Plane getAvion() {
        return avion;
    }

    public void setAvion(Plane avion) {
        this.avion = avion;
    }
    
    public List<Reservation> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reservation> reservas) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
       return "Vuelo: Code --> " + this.code + ", Origen --> " + this.origen + ", Destino --> " + this.destino + ", Aerolinea --> " + this.aerolinea + ", Avion --> " + this.avion + ", Reservas --> " + this.reservas + ", Duracion --> " + this.duracion + ", Asientos --> " + this.seats + ", Precio --> " + this.precio +  ", messages --> [" + messagesStr + "]"; 
    }
    */
}