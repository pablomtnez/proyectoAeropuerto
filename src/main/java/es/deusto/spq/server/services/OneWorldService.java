package es.deusto.spq.server.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.client.domain.AirAlliance;
import es.deusto.spq.client.domain.Country;
import es.deusto.spq.server.dao.AirlineDAO;
import es.deusto.spq.server.dao.AirportDAO;
import es.deusto.spq.server.dao.FlightDAO;
import es.deusto.spq.server.dao.PlaneDAO;
import es.deusto.spq.server.dao.ReservationDAO;
import es.deusto.spq.server.jdo.Airline;
import es.deusto.spq.server.jdo.Airport;
import es.deusto.spq.server.jdo.Flight;
import es.deusto.spq.server.jdo.Plane;
import es.deusto.spq.server.jdo.Reservation;

public class OneWorldService {

    protected static final Logger logger = LogManager.getLogger();

    private static final String AIRLINES_FILE = "src/main/resources/data/airlines.csv";
    private static final String AIRPORTS_FILE = "src/main/resources/data/airports.csv";
    private static final String FLIGHTS_FILE = "src/main/resources/data/flights.csv";
    private static final String PLANES_FILE = "src/main/resources/data/planes.csv";
    private static final String RESERVATIONS_FILE = "src/main/resources/data/reservations.csv";

    protected Map<String, Flight> flights = new HashMap<>();

    public OneWorldService() {
    }

    public void loadAllData() {
        Map<String, Airline> airlinesMap = loadAirlinesCSV();
        AirlineDAO.getInstance().saveOrUpdateAirlines(airlinesMap);
        Map<String, Airport> airportsMap =loadAirportsCSV();
        AirportDAO.getInstance().saveOrUpdateAirports(airportsMap);
        Map<String, Plane> planesMap = loadPlanesCSV();
        PlaneDAO.getInstance().saveOrUpdatePlanes(planesMap);
        Map<String, Flight> flightsMap = loadFlights();
        FlightDAO.getInstance().saveOrUpdateFlights(flightsMap);
        Map<String, List<Reservation>> reservationsMap = loadReservationsCSV();
        ReservationDAO.getInstance().saveOrUpdate(reservationsMap);
        
        
        
        
    }

    public Map<String, Flight> loadFlights() {
        Map<String, Airline> airlines = loadAirlinesCSV();
        Map<String, Airport> airports = loadAirportsCSV();
        Map<String, Plane> planes = loadPlanesCSV();
        Map<String, List<Reservation>> reservationsMap = loadReservationsCSV();
        flights = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FLIGHTS_FILE))) {
            String line = reader.readLine();
            String[] fields;
            Flight flight;
            while ((line = reader.readLine()) != null) {
                fields = line.split(",");
                flight = new Flight(fields[0], airports.get(fields[1]), airports.get(fields[2]), airlines.get(fields[3]), planes.get(fields[5]), Integer.valueOf(fields[4]), Float.parseFloat(fields[6]));
                if (reservationsMap.containsKey(flight.getCode())) {
                    flight.setReservations(reservationsMap.get(flight.getCode()));
                }
                flights.put(flight.getCode(), flight);
            }
        } catch (Exception ex) {
            logger.error(String.format("%s - Error al cargar vuelos: %s", ex.getMessage()));
        }
        logger.info(String.format("%s vuelos cargados correctamente", flights.values().size()));
        return flights;
    }

    public void storeReservation(Reservation reservation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESERVATIONS_FILE, true))) {
            String line = String.format("%s#%s#%s#%s", reservation.getLocator(), reservation.getFlight().getCode(), String.valueOf(reservation.getDate()), String.join(";", reservation.getPassengers()));
            writer.write(line);
            writer.newLine();
        } catch (Exception ex) {
            logger.error(String.format("%s - Error guardando reserva: %s", ex.getMessage()));
        }
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

    private Map<String, List<Reservation>> loadReservationsCSV() {
        Map<String, List<Reservation>> reservations = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line = reader.readLine(); // Skip header
            Reservation reservation;
            while ((line = reader.readLine()) != null) {
                reservation = parseCSVReservation(line);
                reservations.putIfAbsent(reservation.getFlight().getCode(), new ArrayList<>());
                reservations.get(reservation.getFlight().getCode()).add(reservation);
            }
        } catch (Exception ex) {
            logger.error(String.format("%s - Error cargando reservas: %s", ex.getMessage()));
        }
        return reservations;
    }

    public static Airline parseCSVAirline(String data) throws Exception {
        try {
            String[] fields = data.split(",");
            return new Airline(fields[0], fields[1], Country.valueOf(fields[2]), AirAlliance.valueOf(fields[3]));
        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Airline.class, data));
        }
    }

    public static Airport parseCSVAirport(String data) throws Exception {
        try {
            String[] fields = data.split(",");
            return new Airport(fields[0], fields[1], fields[2], Country.valueOf(fields[3]));
        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Airport.class, data));
        }
    }

    public static Plane parseCSVPlane(String data) throws Exception {
        try {
            String[] fields = data.split(",");
            return new Plane(fields[0], fields[1], Integer.valueOf(fields[2]));
        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Plane.class, data));
        }
    }

    public static Reservation parseCSVReservation(String data) throws Exception {
        try {
            String[] fields = data.split("#");
            return new Reservation(fields[0], new Flight(fields[1], null, null, null, null, 0, 0f), Long.valueOf(fields[2]), Arrays.asList(fields[3].split(";")));
        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Reservation.class, data));
        }
    }
}
