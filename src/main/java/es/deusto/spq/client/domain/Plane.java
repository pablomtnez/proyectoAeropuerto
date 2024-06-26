package es.deusto.spq.client.domain;

import java.io.Serializable;
import java.util.Objects;

public class Plane implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private int seats;
	
	public Plane(String code, String name, int seats) {
		this.code = code;
		this.name = name;
		this.seats = seats;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getSeats() {
		return seats;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			return ((Plane) obj).code.equals(this.code);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s (%d seats)", code, name, seats);
	}

}