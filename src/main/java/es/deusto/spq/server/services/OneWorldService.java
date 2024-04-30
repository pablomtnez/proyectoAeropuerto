package es.deusto.spq.server.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Consumer;

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

    public void loadAllData() {
        loadFromCSV("src/main/resources/data/airports.csv", this::parseAndSaveAirport);
        loadFromCSV("src/main/resources/data/airlines.csv", this::parseAndSaveAirline);
        loadFromCSV("src/main/resources/data/flights.csv", this::parseAndSaveFlight);
        loadFromCSV("src/main/resources/data/planes.csv", this::parseAndSavePlane);
        loadFromCSV("src/main/resources/data/reservations.csv", this::parseAndSaveReservation);
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
            Flight flight = new Flight();
            flight.setCode(st.nextToken());
            flight.setFrom(airportDao.find(st.nextToken())); // Find or create logic might be needed
            flight.setTo(airportDao.find(st.nextToken()));
            flight.setAirline(airlineDao.find(st.nextToken())); // This assumes the DAO has a find method by code
            flight.setPlane(planeDao.find(st.nextToken()));
            flight.setDuration(Integer.parseInt(st.nextToken()));
            flight.setPrice(Double.parseDouble(st.nextToken()));
            flightDao.saveOrUpdate(flight);
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