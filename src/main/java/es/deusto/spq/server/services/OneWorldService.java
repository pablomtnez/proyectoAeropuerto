package es.deusto.spq.server.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import es.deusto.spq.server.dao.FlightDAO;
import es.deusto.spq.server.jdo.Flight;
import es.deusto.spq.server.jdo.Airport;
import es.deusto.spq.server.jdo.Airline;
import es.deusto.spq.server.jdo.Plane;

public class OneWorldService {

    private FlightDAO flightDao;
    private Flight flight;

    public OneWorldService() {
        this.flightDao = new FlightDAO();
    }

    public void loadFlightsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine();  // Skip header if present
            while ((line = reader.readLine()) != null) {
                Flight flight = parseFlight(line);
                if (flight != null) {
                    flightDao.saveOrUpdateFlight(flight);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filename);
            e.printStackTrace();
        }
    }

    private Flight parseFlight(String csvLine) {
        StringTokenizer st = new StringTokenizer(csvLine, ",");
        try {
            flight = new Flight();
            if (st.hasMoreTokens()) {
                flight.setCode(st.nextToken());
                flight.setOrigen(findOrCreateAirport(st.nextToken()));
                flight.setDestino(findOrCreateAirport(st.nextToken()));
                flight.setAerolinea(findOrCreateAirline(st.nextToken()));
                flight.setAvion(findOrCreatePlane(st.nextToken()));
                // Assumption: Reservation handling and list instantiation is done elsewhere
                flight.setDuracion(Integer.parseInt(st.nextToken()));
                flight.setSeats(Integer.parseInt(st.nextToken()));
                flight.setPrecio(Double.parseDouble(st.nextToken()));
            }
            return flight;
        } catch (Exception e) {
            System.err.println("Error parsing the flight from CSV line: " + csvLine);
            e.printStackTrace();
            return null;
        }
    }

    // Example method stubs for finding or creating related entities
    private Airport findOrCreateAirport(String airportName) {
        // Implement this method to find or create an Airport based on airportName
        return new Airport();  // Placeholder
    }

    private Airline findOrCreateAirline(String airlineName) {
        // Implement this method to find or create an Airline based on airlineName
        return new Airline();  // Placeholder
    }

    private Plane findOrCreatePlane(String planeId) {
        // Implement this method to find or create a Plane based on planeId
        return new Plane();  // Placeholder
    }
}
