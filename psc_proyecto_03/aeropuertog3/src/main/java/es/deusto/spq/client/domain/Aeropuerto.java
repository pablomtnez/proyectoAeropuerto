package es.deusto.spq.client.domain;

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
	
	/**
	 * Crea un objeto Aeropuerto a partir de una cadena de texto separada por comas ",".
	 * @param data String con la cadena de texto separada por comas ",".
	 * @return Aeropuerto con el nuevo objeto creado.
	 * @throws Exception Si se produce un error al generar el obejto Aeropuerto.
	 */
	public static Aeropuerto parseCSV(String data) throws Exception {
		try {
			String[] fields = data.split(",");
			
			return new Aeropuerto(fields[0], fields[1], fields[2], Pais.valueOf(fields[3]));			
		} catch (Exception ex) {
			throw new Exception(String.format("%s from CSV error: %s", Aeropuerto.class, data));
		}
	}

	@Override
	public int compareTo(Aeropuerto o) {
		return this.getCode().compareTo(o.getCode());
	}
}