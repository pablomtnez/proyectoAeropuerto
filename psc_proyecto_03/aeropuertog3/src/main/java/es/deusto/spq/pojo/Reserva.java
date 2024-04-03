package es.deusto.spq.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Reserva implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String locator;
	private Vuelo vuelo;
	private long date;
	private List<String> passengers;
	
	public Reserva(String locator, Vuelo vuelo, long date, List<String> passengers) {
		this.locator = locator;
		this.vuelo = vuelo;
		this.date = date;
		this.passengers = passengers;
	}
	
	public String getLocator() {
		return locator;
	}

	public Vuelo getVuelo() {
		return vuelo;
	}

	public long getDate() {
		return date;
	}

	public List<String> getPassengers() {
		return passengers;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(vuelo, locator);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			Reserva other = (Reserva) obj;
			return other.locator.equals(this.locator);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s - %d (%02d passengers)", locator, vuelo, date, passengers.size());		
	}
	
}
