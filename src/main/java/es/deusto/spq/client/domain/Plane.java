package es.deusto.spq.client.domain;

import java.io.Serializable;
import java.util.Objects;

public class Plane implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String iataCode;
	private String name;
	private int seats;
	
	public Plane(String iataCode, String name, int seats) {
		this.iataCode = iataCode;
		this.name = name;
		this.seats = seats;
	}

	public String getIataCode() {
		return iataCode;
	}

	public String getName() {
		return name;
	}

	public int getSeats() {
		return seats;
	}

	@Override
	public int hashCode() {
		return Objects.hash(iataCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			return ((Plane) obj).iataCode.equals(this.iataCode);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s (%d seats)", iataCode, name, seats);
	}

}