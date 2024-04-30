package es.deusto.spq.server.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Consumer;

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

    private AirlineDAO airlineDao = AirlineDAO.getInstance();
    private AirportDAO airportDao = AirportDAO.getInstance();
    private FlightDAO flightDao = FlightDAO.getInstance();
    private PlaneDAO planeDao = PlaneDAO.getInstance();
    private ReservationDAO reservationDao = ReservationDAO.getInstance();
    protected static final Logger logger = LogManager.getLogger();

    public void loadAllData() {
        try {
            loadFromCSV("src/main/resources/data/airports.csv", this::parseAndSaveAirport);
        } catch (Exception e) {
            logger.error("Failed to load airports: " + e.getMessage(), e);
        }
        try {
            loadFromCSV("src/main/resources/data/airlines.csv", this::parseAndSaveAirline);
        } catch (Exception e) {
            logger.error("Failed to load airlines: " + e.getMessage(), e);
        }
        try {
            loadFromCSV("src/main/resources/data/flights.csv", this::parseAndSaveFlight);
        } catch (Exception e) {
            logger.error("Failed to load flights: " + e.getMessage(), e);
        }
        try {
            loadFromCSV("src/main/resources/data/planes.csv", this::parseAndSavePlane);
        } catch (Exception e) {
            logger.error("Failed to load planes: " + e.getMessage(), e);
        }
        try {
            loadFromCSV("src/main/resources/data/reservations.csv", this::parseAndSaveReservation);
        } catch (Exception e) {
            logger.error("Failed to load reservations: " + e.getMessage(), e);
        }
    }
    

    private void loadFromCSV(String filePath, Consumer<String> parseFunction) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                parseFunction.accept(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filePath);
            e.printStackTrace();
        }
    }

    private void parseAndSaveAirport(String line) {
        StringTokenizer st = new StringTokenizer(line, ",");
        if (st.hasMoreTokens()) {
            Airport airport = new Airport();
            airport.setIataCode(st.nextToken());
            airport.setName(st.nextToken());
            airport.setCity(st.nextToken());
            airport.setCountry(Country.valueOf(st.nextToken().toUpperCase())); // Convert string to enum
            airportDao.saveOrUpdate(airport);
        }
    }


    private void parseAndSaveAirline(String line) {
        StringTokenizer st = new StringTokenizer(line, ",");
        if (st.hasMoreTokens()) {
            Airline airline = new Airline();
            airline.setIataCode(st.nextToken());
            airline.setName(st.nextToken());
            airline.setCountry(Country.valueOf(st.nextToken().toUpperCase())); // Convierte String a Enum
            airline.setAlliance(AirAlliance.valueOf(st.nextToken().toUpperCase())); // Convierte String a Enum
            airlineDao.saveOrUpdate(airline);
        }
    }   


    private void parseAndSaveFlight(String line) {
        StringTokenizer st = new StringTokenizer(line, ",");
        if (st.hasMoreTokens()) {
            try {
                Flight flight = new Flight();
                flight.setCode(st.nextToken());
                flight.setFrom(airportDao.find(st.nextToken()));
                flight.setTo(airportDao.find(st.nextToken()));
                flight.setAirline(airlineDao.find(st.nextToken()));
                flight.setPlane(planeDao.find(st.nextToken()));
                // Asegurándonos que el siguiente token es un número entero válido
                String durationStr = st.nextToken();
                if (durationStr.matches("\\d+")) { // Solo números
                    flight.setDuration(Integer.parseInt(durationStr));
                } else {
                    throw new NumberFormatException("Duración no es numérica: " + durationStr);
                }
                flight.setPrice(Double.parseDouble(st.nextToken()));
                flightDao.saveOrUpdate(flight);
            } catch (NumberFormatException e) {
                logger.error("Error de formato numérico: " + e.getMessage(), e);
            } catch (Exception e) {
                logger.error("Error general al procesar la línea del vuelo: " + line, e);
            }
        }
    }
    

    private void parseAndSavePlane(String line) {
        StringTokenizer st = new StringTokenizer(line, ",");
        if (st.hasMoreTokens()) {
            Plane plane = new Plane();
            plane.setIataCode(st.nextToken());
            plane.setName(st.nextToken());
            plane.setSeats(Integer.parseInt(st.nextToken()));
            planeDao.saveOrUpdate(plane);
        }
    }

    private void parseAndSaveReservation(String line) {
        // Utilizamos un delimitador más robusto, como comillas, si los nombres pueden contener comas.
        String[] parts = line.split(",", -1);  // -1 para incluir campos vacíos al final si existen

        if (parts.length >= 4) {
            Reservation reservation = new Reservation();
            reservation.setLocator(parts[0]);  // Asumimos que la primera columna es el Locator
            reservation.setFlight(flightDao.find(parts[1]));  // Asumimos que la segunda columna es el Flight ID y lo buscamos
            reservation.setDate(Long.parseLong(parts[2]));  // Asumimos que la tercera columna es la fecha en formato timestamp

            // La cuarta columna contiene la lista de pasajeros, separados por punto y coma
            List<String> passengers = Arrays.asList(parts[3].split(";"));
            reservation.setPassengers(passengers);  // Set the list of passengers to the reservation

            reservationDao.saveOrUpdate(reservation);  // Guardar o actualizar la reserva en la base de datos
        }
    }
}