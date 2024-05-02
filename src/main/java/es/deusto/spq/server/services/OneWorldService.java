package es.deusto.spq.server.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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

    protected Map<String, Flight> flights = new ConcurrentHashMap<>();
    protected Map<String, Airline> airlines = new ConcurrentHashMap<>();
    protected Map<String, Airport> airports = new ConcurrentHashMap<>();
    protected Map<String, Plane> planes = new ConcurrentHashMap<>();

    public OneWorldService() {}

    public void loadAllData() {
        // Primero carga las aerolíneas y luego los aeropuertos, ya que son requeridos por otras entidades.
        airlines = loadAirlinesCSV();
        airports = loadAirportsCSV();
        
        // Carga los aviones que pueden ser referenciados por los vuelos.
        planes = loadPlanesCSV();
        
        // Luego carga los vuelos, ya que dependen de aerolíneas, aeropuertos y aviones.
        flights = loadFlights();
        
        // Por último, carga las reservas, ya que dependen de los vuelos.
        List<Reservation> reservations = loadReservationsCSV();
        
        // Asigna las reservas a los vuelos correspondientes.
        for (Reservation r : reservations) {
            Flight flight = flights.get(r.getFlight().getCode());
            if (flight != null) {
                flight.getReservations().add(r);
            }
        }
    
        // Guarda o actualiza los datos de los vuelos en la base de datos.
        FlightDAO.getInstance().saveOrUpdateFlights(flights);
    }
    

    public Map<String, Object> getAllData() {
        Map<String, Object> data = new HashMap<>();
        data.put("airlines", getAllAirlines());
        data.put("airports", getAllAirports());
        data.put("flights", FlightDAO.getInstance().getAllFlights());
        data.put("planes", PlaneDAO.getInstance().getAllPlanes());
        data.put("reservations", ReservationDAO.getInstance().getAllReservations());
        return data;
    }

    public List<Airline> getAllAirlines() {
        return AirlineDAO.getInstance().getAll();
    }

    public List<Airport> getAllAirports() {
        return AirportDAO.getInstance().getAll();
    }

    private Map<String, Flight> loadFlights() {
        Map<String, Flight> flights = new ConcurrentHashMap<>();
        if (!Files.exists(Paths.get(FLIGHTS_FILE))) {
            logger.error("Flight file not found: {}", FLIGHTS_FILE);
            return flights;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FLIGHTS_FILE))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Flight flight = parseFlight(line, airports, airlines, planes);
                flights.put(flight.getCode(), flight);
            }
        } catch (IOException | NumberFormatException ex) {
            logger.error("Error loading flights: ", ex);
        }
        logger.info("{} flights loaded successfully", flights.size());
        return flights;
    }

    private Map<String, Airline> loadAirlinesCSV() {
        Map<String, Airline> airlines = new ConcurrentHashMap<>();
        if (!Files.exists(Paths.get(AIRLINES_FILE))) {
            logger.error("Airlines file not found: {}", AIRLINES_FILE);
            return airlines;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(AIRLINES_FILE))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Airline airline = parseCSVAirline(line);
                airlines.put(airline.getIataCode(), airline);
            }
        } catch (IOException | IllegalArgumentException ex) {
            logger.error("Error loading airlines: ", ex);
        }
        return airlines;
    }

    private Map<String, Airport> loadAirportsCSV() {
        Map<String, Airport> airports = new ConcurrentHashMap<>();
        if (!Files.exists(Paths.get(AIRPORTS_FILE))) {
            logger.error("Airports file not found: {}", AIRPORTS_FILE);
            return airports;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(AIRPORTS_FILE))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Airport airport = parseCSVAirport(line);
                airports.put(airport.getIataCode(), airport);
            }
        } catch (IOException | IllegalArgumentException ex) {
            logger.error("Error loading airports: ", ex);
        }
        return airports;
    }

    private Map<String, Plane> loadPlanesCSV() {
        Map<String, Plane> planes = new ConcurrentHashMap<>();
        if (!Files.exists(Paths.get(PLANES_FILE))) {
            logger.error("Planes file not found: {}", PLANES_FILE);
            return planes;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(PLANES_FILE))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Plane plane = parseCSVPlane(line);
                planes.put(plane.getIataCode(), plane);
            }
        } catch (IOException | NumberFormatException ex) {
            logger.error("Error loading planes: ", ex);
        }
        return planes;
    }

    private List<Reservation> loadReservationsCSV() {
        List<Reservation> reservations = new ArrayList<>();
        if (!Files.exists(Paths.get(RESERVATIONS_FILE))) {
            logger.error("Reservations file not found: {}", RESERVATIONS_FILE);
            return reservations;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Reservation reservation = parseCSVReservation(line);
                reservations.add(reservation);
            }
        } catch (IOException | NumberFormatException ex) {
            logger.error("Error loading reservations: ", ex);
        }
        return reservations;
    }

    public Airline parseCSVAirline(String data) {
        String[] fields = data.split(",");
        if (fields.length < 4) {
            throw new IllegalArgumentException("Invalid airline data: " + data);
        }
        return new Airline(fields[0], fields[1], Country.valueOf(fields[2]), AirAlliance.valueOf(fields[3]));
    }

    public Airport parseCSVAirport(String data) {
        String[] fields = data.split(",");
        if (fields.length < 4) {
            throw new IllegalArgumentException("Invalid airport data: " + data);
        }
        return new Airport(fields[0], fields[1], fields[2], Country.valueOf(fields[3]));
    }

    public Plane parseCSVPlane(String data) {
        String[] fields = data.split(",");
        if (fields.length < 3) {
            throw new IllegalArgumentException("Invalid plane data: " + data);
        }
        return new Plane(fields[0], fields[1], Integer.parseInt(fields[2]));
    }

    private Flight parseFlight(String data, Map<String, Airport> airports, Map<String, Airline> airlines, Map<String, Plane> planes) {
        String[] fields = data.split(",");
        if (fields.length < 7) {
            throw new IllegalArgumentException("Invalid flight data: " + data);
        }
        return new Flight(fields[0], airports.get(fields[1]), airports.get(fields[2]), airlines.get(fields[3]), planes.get(fields[5]), Integer.parseInt(fields[4]), Float.parseFloat(fields[6]));
    }

    private Reservation parseCSVReservation(String data) {
        String[] fields = data.split("#");
        if (fields.length < 4) {
            throw new IllegalArgumentException("Invalid reservation data: " + data);
        }
        Flight flight = flights.get(fields[1]);
        if (flight == null) {
            logger.error("Flight not found for reservation: {}", data);
            return null;
        }
        return new Reservation(fields[0], flight, Long.parseLong(fields[2]), Arrays.asList(fields[3].split(";")));
    }
}
