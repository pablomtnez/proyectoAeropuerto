package es.deusto.spq.pojo;

import java.io.Serializable;
import java.util.Objects;

public class Aeropuerto implements Comparable<Aeropuerto>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String city;
	private Pais pais;
	
	public Aeropuerto(String code, String name, String city, Pais pais) {
		this.code = code;
		this.name = name;
		this.city = city;
		this.pais = pais;
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

	public Pais getPais() {
		return pais;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			return ((Aeropuerto) obj).code.equals(this.code);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s, %s (%s)", code, name, city, pais);
	}

	@Override
	public int compareTo(Aeropuerto o) {
		return this.getCode().compareTo(o.getCode());
	}
}