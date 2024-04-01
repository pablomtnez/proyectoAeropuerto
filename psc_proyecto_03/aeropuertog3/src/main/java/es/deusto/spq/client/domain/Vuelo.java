package es.deusto.spq.client.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vuelo implements Comparable<Vuelo>, Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private Aeropuerto origin;
	private Aeropuerto destination;
	private Aerolinea aerolinea;
	private Avion avion;
	private List<Reserva> reservas;
	private int duration;
	private int seats;
	private float price;
	
	public Vuelo(String code, Aeropuerto origin, Aeropuerto destination, Aerolinea aerolinea, Avion avion, int duration, float price) {
		this.code = code;
		this.origin = origin;
		this.destination = destination;
		this.aerolinea = aerolinea;
		this.avion = avion;
		this.duration = duration;		
		this.seats = (avion == null) ? 0 : avion.getSeats();
		this.price = price;
		this.reservas = new ArrayList<>();
	}

	public String getCode() {
		return code;
	}

	public Aeropuerto getOrigin() {
		return origin;
	}

	public Aeropuerto getDestination() {
		return destination;
	}

	public Aerolinea getAerolinea() {
		return aerolinea;
	}

	public Avion getAvion() {
		return avion;
	}

	public int getDuration() {
		return duration;
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
	
	public float getPrice() {
		return price;
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
			code, origin.getCode(), destination.getCode(), duration, (seats-reservas.size()), price);
	}

	@Override
	public int compareTo(Vuelo o) {
		return code.compareTo(o.code);
	}
}
