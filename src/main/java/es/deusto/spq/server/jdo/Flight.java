package es.deusto.spq.server.jdo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(detachable = "true")
public class Flight {

    @PrimaryKey
    private String code;

    @Persistent
    private Airport from;

    @Persistent
    private Airport to;

    @Persistent
    private Airline airline;

    @Persistent
    private Plane plane;

    @Persistent
    private List<Reservation> reservations;

    @Persistent
    private int duration;

    @Persistent
    private int seats;

    @Persistent
    private double price;

    public Flight() {
    }

    public Flight(String code, Airport from, Airport to,
				  Airline airline, Plane plane, int duration, float price) {
		this.code = code;
		this.from = from;
		this.to = to;
		this.airline = airline;
		this.plane = plane;
		this.duration = duration;		
		this.seats = (plane == null) ? 0 : plane.getSeats();
		this.price = price;
		this.reservations = new ArrayList<>();
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Flight [code=" + code + ", from=" + from + ", to=" + to + ", airline=" + airline + ", plane=" + plane
                + ", reservations=" + reservations + ", duration=" + duration + ", seats=" + seats + ", price=" + price
                + "]";
    }

    

}
