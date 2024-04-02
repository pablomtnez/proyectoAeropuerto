package es.deusto.spq.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Avion {

    @PrimaryKey
    private String code;

    @Persistent
    private String name;

    @Persistent
    private int seats;

    public Avion() {
    }

    public Avion(String code, String name, int seats) {
        this.code = code;
        this.name = name;
        this.seats = seats;
    }

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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String toString() {
        return String.format("%s: %s (%d seats)", code, name, seats);
    }

}
