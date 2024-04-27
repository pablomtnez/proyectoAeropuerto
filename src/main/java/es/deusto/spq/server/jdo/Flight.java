package es.deusto.spq.server.jdo;

//import java.util.HashSet;
import java.util.List;
//import java.util.Set;

//import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import es.deusto.spq.server.jdo.Airline;
import es.deusto.spq.server.jdo.Airport;
import es.deusto.spq.server.jdo.Plane;
import es.deusto.spq.server.jdo.Reservation;

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
    private double precio;

    // Constructor con todos los atributos
    public Flight(String code, Airport origen, Airport destino, Airline aerolinea, Plane avion, List<Reservation> reservas, int duracion, int seats, float precio) {
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

    public Flight() {
        //TODO Auto-generated constructor stub
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double d) {
        this.precio = d;
    }

    @Override
    public String toString() {
        return "Flight [code=" + code + ", origen=" + origen + ", destino=" + destino + ", aerolinea=" + aerolinea
                + ", avion=" + avion + ", reservas=" + reservas + ", duracion=" + duracion + ", seats=" + seats
                + ", precio=" + precio + "]";
    }
    
}