package es.deusto.spq.client.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Flight implements Serializable{

	private static final long serialVersionUID = 1L;
	private String code;
	private Airport from;
	private Airport to;
	private Airline airline;
	private Plane plane;
	private List<Reservation> reservations;
	private int duration;
	private int seats;
	private float price;
	
	public Flight(String code, Airport from, Airport to,
				  Airline airline, Plane plane, int duration, float price) {
		this.code = code;
		this.from = from;
		this.to = to;
		this.airline = airline;
		this.plane = plane;
		this.duration = duration;		
		this.seats = (plane == null) ? 0 : plane.getSeats();
		this.price = price;
		this.reservations = new ArrayList<>();
	}

	public String getCode() {
		return code;
	}

	public Airport getFrom() {
		return from;
	}

	public Airport getTo() {
		return to;
	}

	public Airline getAirline() {
		return airline;
	}

	public Plane getPlane() {
		return plane;
	}

	public int getDuration() {
		return duration;
	}

	public int getSeats() {
		return seats;
	}
	
	public int getRemainingSeats() {
		int occupied = 0;
		
		for(Reservation r : reservations) {
			occupied += r.getPassengers().size();
		}
		
		return (seats - occupied);
	}
	
	public float getPrice() {
		return price;
	}
	
	public List<Reservation> getReservations() {
		return reservations;
	}
	
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public void addReservation(Reservation reservation) {
		if (reservation != null && !reservations.contains(reservation)) {
			reservations.add(reservation);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			return ((Flight) obj).code.equals(this.code);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s -> %s (%04d min., %03d seats, %.2f€)", 
			code, from.getIataCode(), to.getIataCode(),
			duration, (seats-reservations.size()), price);
	}
}