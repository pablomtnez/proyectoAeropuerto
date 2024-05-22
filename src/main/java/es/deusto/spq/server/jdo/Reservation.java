package es.deusto.spq.server.jdo;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import java.util.Arrays;
import java.util.List;

@PersistenceCapable(detachable = "true")
public class Reservation {
    
    @PrimaryKey
    private String locator;

    @Persistent(defaultFetchGroup = "true")
    private Flight flight;

    @Persistent
    private long date;

    @Join
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

    public static Reservation parseCSV(String data) throws Exception {
        try {
           String[] fields = data.split("#");			
			//El vuelo sólo tiene el código porque el resto de datos son desconocidos.
			return new Reservation(fields[0], 
								   new Flight(fields[1], null, null, null, null, 0, 0f), 
								   Long.valueOf(fields[2]), 
								   Arrays.asList(fields[3].split(";")));	
        } catch (Exception ex) {
            ex.printStackTrace();
			throw new Exception(String.format("%s from CSV error: %s", Reservation.class, data));
        }
    }
}