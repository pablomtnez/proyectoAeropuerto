package es.deusto.spq.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Plane {

    @PrimaryKey
    private String iataCode;

    @Persistent
    private String name;

    @Persistent
    private int seats;

    public Plane() {
    }

    public Plane(String iataCode, String name, int seats) {
        this.iataCode = iataCode;
        this.name = name;
        this.seats = seats;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String toString() {
        return String.format("%s: %s (%d seats)", iataCode, name, seats);
    }

    public static Plane parseCSV(String data) throws Exception{
        try {
            String[] fields = data.split(",");
            return new Plane(fields[0], fields[1], Integer.valueOf(fields[2]));
        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Plane.class, data));
        }
    }
}