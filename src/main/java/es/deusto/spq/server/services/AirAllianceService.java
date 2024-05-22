package es.deusto.spq.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import es.deusto.spq.client.domain.AirAlliance;
import es.deusto.spq.server.jdo.Airport;
import es.deusto.spq.server.jdo.Flight;
import es.deusto.spq.server.jdo.Reservation;

public abstract class AirAllianceService {
    //Atributos utilizadas para generar el localizador de las reservas
	private static final String LOCATOR_ALPHABET = "234679CDFGHJKMNPRTWXYZ";
	private static final Short LOCATOR_LENGTH = 6;
	private static final Random RANDOM = new Random();
	private static List<String> locators = new ArrayList<>();

    protected Logger logger = Logger.getLogger(OneWorldService.class.getName());

	protected Map<String, Flight> flights = new HashMap<>();
	protected Map<String, Airport> airports = new HashMap<>();

	public AirAllianceService(AirAlliance airAlliance) {
		flights = loadFlights();
		airports = initAirports();
	}


	public abstract Map<String, Flight> loadFlights();

	public abstract void storeReservation(Reservation reservation);

	public String book(String flight, List<String> passengers){
		Flight flightObject = flights.get(flight);
		String locator = generateLocator();
		Reservation reservation = new Reservation(locator, flightObject, System.currentTimeMillis(), passengers);
		storeReservation(reservation);
		logger.info(String.format("%s - Nueva reserva: %s", reservation));

		return reservation.getLocator();
	}

	protected Map<String, Airport> initAirports(){
		Map<String, Airport> airports = new HashMap<>();

		for(Flight flight : flights.values()){
			airports.putIfAbsent(flight.getTo().getIataCode(), flight.getTo());
			airports.putIfAbsent(flight.getFrom().getIataCode(), flight.getFrom());
		}
		return airports;
	}
	
	private static final String generateLocator() {
		StringBuffer buffer;
				
		do {
			buffer = new StringBuffer();
			//Se realizar una selección aleatoria de 6 caracteres	
			for (int i=0; i<LOCATOR_LENGTH; i++) {
				buffer.append(LOCATOR_ALPHABET.charAt(RANDOM.nextInt(LOCATOR_ALPHABET.length())));
			}
		//Si no está repetido el localizador, el proceso se detiene
		} while (locators.contains(buffer.toString()));
		
		return buffer.toString();		
	}
    
    public List<Airport> getOrigins() {
		Set<Airport> airports = new HashSet<Airport>();

		flights.values().forEach(f -> airports.add(f.getFrom()));

		return new ArrayList<>(airports);
	}

	public List<Airport> getDestinations(String origin){
		Airport dAirport = airports.get(origin);
		Set<Airport> airports = new HashSet<Airport>();

		flights.values().forEach(f ->{
			if(f.getFrom().equals(dAirport)) {
				airports.add(f.getTo());
			}
		});

		return new ArrayList<>(airports);
	}

	public List<Flight> search(String origin, String destination) {
		List<Flight> result = new ArrayList<>();
		
		Airport dAirport = airports.get(origin);
		Airport aAirport = airports.get(destination);
		
		flights.values().forEach(f -> {
			if (f.getFrom().equals(dAirport) && f.getTo().equals(aAirport)) {
				result.add(f);
			}
		});
		
		return result;
	}

	/**
	 * Devuelve una lista con todos los vuelos.
	 * @return List<Flight> con la lista vuelos.
	 */
	public List<Flight> getAllFlights() {
		return new ArrayList<>(flights.values());
	}
}