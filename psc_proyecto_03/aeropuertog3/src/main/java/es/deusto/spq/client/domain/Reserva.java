package es.deusto.spq.client.domain;

import java.io.Serializable;
import java.util.Arrays;
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
	
	/**
	 * Crea un objeto Reserva a partir de una cadena de texto separada por "#".
	 * @param data String con la cadena de texto separada por "#".
	 * @return Reserva con el nuevo objeto creado.
	 * @throws Exception Si se produce un error al generar el obejto Reserva.
	 */
	public static Reserva parseCSV(String data) throws Exception {
		try {
			String[] fields = data.split("#");			
			//El vuelo sólo tiene el código porque el resto de datos son desconocidos.
			return new Reserva(fields[0], 
								   new Vuelo(fields[1], null, null, null, null, null, 0, 0, 0f), 
								   Long.valueOf(fields[2]), 
								   Arrays.asList(fields[3].split(";")));			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(String.format("%s from CSV error: %s", Reserva.class, data));
		}
	}
}
