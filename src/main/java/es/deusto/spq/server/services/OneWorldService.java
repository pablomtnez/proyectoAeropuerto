package es.deusto.spq.server.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import es.deusto.spq.server.dao.AirlineDAO;
import es.deusto.spq.server.dao.AirportDAO;
import es.deusto.spq.server.dao.FlightDAO;
import es.deusto.spq.server.dao.PlaneDAO;
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
        AirportDAO airportDao = new AirportDAO();  // Asegúrate de tener una instancia de AirportDAO
        Airport airport = airportDao.findByName(airportName);
        if (airport == null) {
            airport = new Airport();
            airport.setName(airportName);
            // Configura otros atributos necesarios
            airportDao.saveOrUpdate(airport);
        }
        return airport;
    }


    private Airline findOrCreateAirline(String airlineName) {
        AirlineDAO airlineDao = new AirlineDAO();  // Asegúrate de tener una instancia de AirlineDAO
        Airline airline = airlineDao.findByName(airlineName);
        if (airline == null) {
            airline = new Airline();
            airline.setName(airlineName);
            // Configura otros atributos necesarios para la aerolínea
            airlineDao.saveOrUpdate(airline);  // Guarda la nueva aerolínea en la base de datos
        }
        return airline;
    }


    private Plane findOrCreatePlane(String planeId) {
        PlaneDAO planeDao = PlaneDAO.getInstance();  // Usando el patrón Singleton para obtener la instancia
        Plane plane = planeDao.find(planeId);
        if (plane == null) {
            plane = new Plane();
            plane.setCode(planeId);  // Utiliza setCode para configurar el ID
            // Configura otros atributos necesarios para el avión
            planeDao.saveOrUpdate(plane);  // Guarda el nuevo avión en la base de datos
        }
        return plane;
    }
    

}
