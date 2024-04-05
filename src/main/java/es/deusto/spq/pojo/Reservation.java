package es.deusto.spq.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Reservation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String locator;
	private Flight flight;
	private long date;
	private List<String> passengers;
	
	public Reservation(String locator, Flight flight, long date, List<String> passengers) {
		this.locator = locator;
		this.flight = flight;
		this.date = date;
		this.passengers = passengers;
	}
	
	public String getLocator() {
		return locator;
	}

	public Flight getFlight() {
		return flight;
	}

	public long getDate() {
		return date;
	}

	public List<String> getPassengers() {
		return passengers;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(flight, locator);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			Reservation other = (Reservation) obj;
			return other.locator.equals(this.locator);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s - %d (%02d passengers)", 
							 locator, flight, date, passengers.size());		
	}

}