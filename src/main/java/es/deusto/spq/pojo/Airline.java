package es.deusto.spq.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Airline implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private Country country;
	private AirAlliance alliance;
	private List<Flight> flights;
	
	public Airline(String code, String name, Country country, AirAlliance alliance) {
		this.code = code;
		this.name = name;
		this.country = country;
		this.alliance = alliance;
		this.flights = new ArrayList<>();
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Country getCountry() {
		return country;
	}
	
	public AirAlliance getAlliance() {
		return alliance;
	}
	
	public List<Flight> getFlights() {
		return flights;
	}

	public void addFlight(Flight flight) {
		if (flight !=null && !flights.contains(flight)) {
			flights.add(flight);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			return ((Airline) obj).code.equals(code);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s [%s] (%s)", code, name, alliance, country);
	}
	
}