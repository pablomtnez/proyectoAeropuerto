package es.deusto.spq.client.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Airline implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String iataCode;
	private String name;
	private Country country;
	private AirAlliance alliance;
	private List<Flight> flights;
	
	public Airline(String iataCode, String name, Country country, AirAlliance alliance) {
		this.iataCode = iataCode;
		this.name = name;
		this.country = country;
		this.alliance = alliance;
		this.flights = new ArrayList<>();
	}

	public String getIataCode() {
		return iataCode;
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
		return Objects.hash(iataCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			return ((Airline) obj).iataCode.equals(iataCode);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s [%s] (%s)", iataCode, name, alliance, country);
	}
	
}