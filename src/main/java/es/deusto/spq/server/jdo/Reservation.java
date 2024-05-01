package es.deusto.spq.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import java.util.List;

@PersistenceCapable(detachable = "true")
public class Reservation {
    @PrimaryKey
    private String locator;

    @Persistent
    private Flight flight;

    @Persistent
    private long date;

    @Persistent
    private List<String> passengers;

    public Reservation() {
    }

    public Reservation(String locator, Flight flight, long date, List<String> passengers){
        this.locator = locator;
        this.flight = flight;
        this.date = date;
        this.passengers = passengers;
    }

    // Getters y setters
    public String getLocator() {
        return locator;
    }

    public void setLocator(String locator) {
        this.locator = locator;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<String> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "Reservation [locator=" + locator + ", flight=" + flight + ", date=" + date + ", passengers="
                + passengers + "]";
    }
}
