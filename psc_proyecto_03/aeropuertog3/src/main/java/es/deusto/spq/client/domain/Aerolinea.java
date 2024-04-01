package es.deusto.spq.client.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Aerolinea implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private Pais pais;
	private AirAlliance alliance;
	private List<Vuelo> vuelos;
	
	public Aerolinea(String code, String name, Pais pais, AirAlliance alliance) {
		this.code = code;
		this.name = name;
		this.pais = pais;
		this.alliance = alliance;
		this.vuelos = new ArrayList<>();
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Pais getPais() {
		return pais;
	}
	
	public AirAlliance getAlliance() {
		return alliance;
	}
	
	public List<Vuelo> getVuelos() {
		return vuelos;
	}

	public void addVuelo(Vuelo vuelo) {
		if (vuelo !=null && !vuelos.contains(vuelo)) {
			vuelos.add(vuelo);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			return ((Aerolinea) obj).code.equals(code);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s [%s] (%s)", code, name, alliance, pais);
	}
	
	/**
	 * Crea un objeto Aerolinea a partir de una cadena de texto separada por comas ",".
	 * @param data String con la cadena de texto separada por comas ",".
	 * @return Aerolinea con el nuevo objeto creado.
	 * @throws Exception Si se produce un error al generar el obejto Aerolinea.
	 */
	public static Aerolinea parseCSV(String data) throws Exception {
		try {
			String[] fields = data.split(",");
			
			return new Aerolinea(fields[0], fields[1], Pais.valueOf(fields[2]), AirAlliance.valueOf(fields[3]));			
		} catch (Exception ex) {
			throw new Exception(String.format("%s from CSV error: %s", Aerolinea.class, data));
		}
	}
}
