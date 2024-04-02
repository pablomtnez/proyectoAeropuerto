package es.deusto.spq.client.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vuelo implements Comparable<Vuelo>, Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private Aeropuerto origen;
	private Aeropuerto destino;
	private Aerolinea aerolinea;
	private Avion avion;
	private List<Reserva> reservas;
	private int duracion;
	private int seats;
	private float precio;
	
	public Vuelo(String code, Aeropuerto origen, Aeropuerto destino, Aerolinea aerolinea, Avion avion, int duracion, int seats, float precio, List<Reserva> reservas) {
		this.code = code;
		this.origen = origen;
		this.destino = destino;
		this.aerolinea = aerolinea;
		this.avion = avion;
		this.duracion = duracion;		
		this.seats = (avion == null) ? 0 : avion.getSeats();
		this.precio = precio;
		this.reservas = new ArrayList<>();
	}

	public String getCode() {
		return code;
	}

	public Aeropuerto getOrigen() {
		return origen;
	}

	public Aeropuerto getDestino() {
		return destino;
	}

	public Aerolinea getAerolinea() {
		return aerolinea;
	}

	public Avion getAvion() {
		return avion;
	}

	public int getDuracion() {
		return duracion;
	}

	public int getSeats() {
		return seats;
	}
	
	public int getRemainingSeats() {
		int occupied = 0;
		
		for(Reserva r : reservas) {
			occupied += r.getPassengers().size();
		}
		
		return (seats - occupied);
	}
	
	public float getPrecio() {
		return precio;
	}
	
	public List<Reserva> getReservas() {
		return reservas;
	}
	
	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public void addReserva(Reserva Reserva) {
		if (Reserva != null && !reservas.contains(Reserva)) {
			reservas.add(Reserva);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			return ((Vuelo) obj).code.equals(this.code);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s -> %s (%04d min., %03d seats, %.2f€)", 
			code, origen.getCode(), destino.getCode(), duracion, (seats-reservas.size()), precio);
	}

	@Override
	public int compareTo(Vuelo o) {
		return code.compareTo(o.code);
	}
}
