package es.deusto.spq.server.jdo;

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
    private int duration;

    @Persistent
    private double price;

    public Flight() {
    }

    public Flight(String code, Airport from, Airport to, Airline airline, Plane plane, int duration, double price) {
        this.code = code;
        this.from = from;
        this.to = to;
        this.airline = airline;
        this.plane = plane;
        this.duration = duration;
        this.price = price;
    }

    // Getters and setters

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
