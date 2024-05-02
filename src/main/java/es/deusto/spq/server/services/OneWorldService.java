package es.deusto.spq.server.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.client.domain.AirAlliance;
import es.deusto.spq.client.domain.Country;
import es.deusto.spq.server.dao.*;
import es.deusto.spq.server.jdo.*;

public class OneWorldService {
    protected static final Logger logger = LogManager.getLogger();

    private static final String AIRLINES_FILE = "src/main/resources/data/airlines.csv";
    private static final String AIRPORTS_FILE = "src/main/resources/data/airports.csv";
    private static final String FLIGHTS_FILE = "src/main/resources/data/flights.csv";
    private static final String PLANES_FILE = "src/main/resources/data/planes.csv";
    private static final String RESERVATIONS_FILE = "src/main/resources/data/reservations.csv";

    protected Map<String, Flight> flights = new HashMap<>();
    protected Map<String, Airline> airlines = new HashMap<>();
    protected Map<String, Airport> airports = new HashMap<>();
    protected Map<String, Plane> planes = new HashMap<>();

    public OneWorldService() {}

    public void loadAllData() {
        airlines = loadAirlinesCSV();
        airports = loadAirportsCSV();
        planes = loadPlanesCSV();
        Map<String, Flight> flightsMap = loadFlights();
        List<Reservation> reservations = loadReservationsCSV();
        for(Reservation r : reservations) flightsMap.get(r.getFlight().getCode()).getReservations().add(r);
        FlightDAO.getInstance().saveOrUpdateFlights(flightsMap); 
    }

    public Map<String, Object> getAllData() {
        Map<String, Object> data = new HashMap<>();
        try {
            logger.info("Loading all data...");
    
            List<AirAlliance> airAlliances = getAllAirAlliances();
            List<Airline> airlinesList = getAllAirlines();
            List<Airport> airportsList = getAllAirports();
            List<Flight> flightsList = FlightDAO.getInstance().getAllFlights();
            List<Plane> planesList = PlaneDAO.getInstance().getAllPlanes();
            List<Reservation> reservationsList = ReservationDAO.getInstance().getAllReservations();
    
            logger.info("Data loading successful.");
            data.put("airAlliances", airAlliances);
            data.put("airlines", airlinesList);
            data.put("airports", airportsList);
            data.put("flights", flightsList);
            data.put("planes", planesList);
            data.put("reservations", reservationsList);
    
        } catch (Exception e) {
            logger.error("Error loading data: " + e.getMessage(), e);
            throw new RuntimeException("Error loading data", e);
        }
        return data;
    }
    

    private List<AirAlliance> getAllAirAlliances() {
        // Implementar el código para obtener todas las alianzas aéreas
        return new ArrayList<>();
    }

    private List<Airline> getAllAirlines() {
        return new ArrayList<>(airlines.values());
    }

    private List<Airport> getAllAirports() {
        return new ArrayList<>(airports.values());
    }

    private Map<String, Flight> loadFlights() {
        flights = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FLIGHTS_FILE))) {
            String line = reader.readLine();
            String[] fields;
            Flight flight;
            while ((line = reader.readLine()) != null) {
                fields = line.split(",");
                flight = new Flight(fields[0], airports.get(fields[1]), airports.get(fields[2]), airlines.get(fields[3]), planes.get(fields[5]), Integer.valueOf(fields[4]), Float.parseFloat(fields[6]));
                flights.put(flight.getCode(), flight);
            }
        } catch (Exception ex) {
            logger.error(String.format("%s - Error al cargar vuelos: %s", ex.getMessage()));
        }
        logger.info(String.format("%s vuelos cargados correctamente", flights.values().size()));
        return flights;
    }

    private Map<String, Airline> loadAirlinesCSV() {
        Map<String, Airline> airlines = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(AIRLINES_FILE))) {
            String line = reader.readLine(); // Skip header
            Airline airline;
            while ((line = reader.readLine()) != null) {
                airline = parseCSVAirline(line);
                airlines.put(airline.getIataCode(), airline);
            }
        } catch (Exception ex) {
            logger.error(String.format("%s - Error cargando aerolineas: %s", ex.getMessage()));
        }
        return airlines;
    }

    private Map<String, Airport> loadAirportsCSV() {
        Map<String, Airport> airports = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(AIRPORTS_FILE))) {
            String line = reader.readLine(); // Skip header
            Airport airport;
            while ((line = reader.readLine()) != null) {
                airport = parseCSVAirport(line);
                airports.put(airport.getIataCode(), airport);
            }
        } catch (Exception ex) {
            logger.error(String.format("%s - Error cargando aeropuertos: %s", ex.getMessage()));
        }
        return airports;
    }

    private Map<String, Plane> loadPlanesCSV() {
        Map<String, Plane> planes = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PLANES_FILE))) {
            String line = reader.readLine(); // Skip header
            Plane plane;
            while ((line = reader.readLine()) != null) {
                plane = parseCSVPlane(line);
                planes.put(plane.getIataCode(), plane);
            }
        } catch (Exception ex) {
            logger.error(String.format("%s - Error cargando aviones: %s", ex.getMessage()));
        }
        return planes;
    }

    private List<Reservation> loadReservationsCSV() {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line = reader.readLine(); // Skip header
            Reservation reservation;
            while ((line = reader.readLine()) != null) {
                reservation = parseCSVReservation(line);
                reservations.add(reservation);
            }
        } catch (Exception ex) {
            logger.error(String.format("%s - Error cargando reservas: %s", ex.getMessage()));
        }
        return reservations;
    }

    public Airline parseCSVAirline(String data) throws Exception {
        try {
            String[] fields = data.split(",");
            return new Airline(fields[0], fields[1], Country.valueOf(fields[2]), AirAlliance.valueOf(fields[3]));
        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Airline.class, data));
        }
    }

    public Airport parseCSVAirport(String data) throws Exception {
        try {
            String[] fields = data.split(",");
            return new Airport(fields[0], fields[1], fields[2], Country.valueOf(fields[3]));
        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Airport.class, data));
        }
    }

    public Plane parseCSVPlane(String data) throws Exception {
        try {
            String[] fields = data.split(",");
            return new Plane(fields[0], fields[1], Integer.valueOf(fields[2]));
        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Plane.class, data));
        }
    }

    public Reservation parseCSVReservation(String data) throws Exception {
        try {
            String[] fields = data.split("#");
            return new Reservation(fields[0], flights.get(fields[1]), Long.valueOf(fields[2]), Arrays.asList(fields[3].split(";")));
        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Reservation.class, data));
        }
    }
}
