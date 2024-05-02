package es.deusto.spq.client.domain;

import java.io.Serializable;
import java.util.Objects;

public class Airport implements Comparable<Airport>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private String iataCode;
	private String name;
	private String city;
	private Country country;
	
	public Airport(String iataCode, String name, String city, Country country) {
		this.iataCode = iataCode;
		this.name = name;
		this.city = city;
		this.country = country;
	}

	public String getIataCode() {
		return iataCode;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public Country getCountry() {
		return country;
	}

	@Override
	public int hashCode() {
		return Objects.hash(iataCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			return ((Airport) obj).iataCode.equals(this.iataCode);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s, %s (%s)", iataCode, name, city, country);
	}

	@Override
	public int compareTo(Airport o) {
		return this.getIataCode().compareTo(o.getIataCode());
	}
}