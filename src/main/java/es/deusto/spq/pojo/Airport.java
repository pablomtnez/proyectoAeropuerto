package es.deusto.spq.pojo;

import java.io.Serializable;
import java.util.Objects;

public class Airport implements Comparable<Airport>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String city;
	private Country country;
	
	public Airport(String code, String name, String city, Country country) {
		this.code = code;
		this.name = name;
		this.city = city;
		this.country = country;
	}

	public String getCode() {
		return code;
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
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			return ((Airport) obj).code.equals(this.code);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s, %s (%s)", code, name, city, country);
	}

	@Override
	public int compareTo(Airport o) {
		return this.getCode().compareTo(o.getCode());
	}
}