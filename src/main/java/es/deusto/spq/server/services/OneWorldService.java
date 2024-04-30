package es.deusto.spq.server.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import es.deusto.spq.server.dao.AirlineDAO;
import es.deusto.spq.server.dao.AirportDAO;
import es.deusto.spq.server.dao.FlightDAO;
import es.deusto.spq.server.dao.PlaneDAO;
import es.deusto.spq.server.dao.ReservationDAO;
import es.deusto.spq.server.jdo.Flight;
import es.deusto.spq.server.jdo.Airport;
import es.deusto.spq.server.jdo.Airline;
import es.deusto.spq.server.jdo.Plane;
import es.deusto.spq.server.jdo.Reservation;
import es.deusto.spq.server.jdo.Usuario;

public class OneWorldService {

    private FlightDAO flightDao;
    private AirportDAO airportDao;
    private AirlineDAO airlineDao;
    private PlaneDAO planeDao;
    private ReservationDAO reservationDao;

    public OneWorldService() {
        this.flightDao = new FlightDAO();
        this.airportDao = new AirportDAO();
        this.airlineDao = new AirlineDAO();
        this.planeDao = PlaneDAO.getInstance(); // Assumiendo que PlaneDAO usa Singleton
        this.reservationDao = new ReservationDAO();
    }

    public void loadAllData() {
        loadAirports("src/main/resources/data/airports.csv");
        loadAirlines("src/main/resources/data/airlines.csv");
        loadPlanes("src/main/resources/data/planes.csv");
        loadFlights("src/main/resources/data/flights.csv");
        loadReservations("src/main/resources/data/reservations.csv");
    }

    private void loadAirports(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(filePath).toFile()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Airport airport = parseAirport(line);
                if (airport != null) {
                    airportDao.saveOrUpdate(airport);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filePath);
            e.printStackTrace();
        }
    }

    private Airport parseAirport(String csvLine) {
        StringTokenizer st = new StringTokenizer(csvLine, ",");
        Airport airport = new Airport();
        if (st.hasMoreTokens()) {
            airport.setCode(st.nextToken());
            airport.setName(st.nextToken());
            airport.setCity(st.nextToken());
            airport.setCountry(st.nextToken()); // Assuming setCountry accepts a String directly
        }
        return airport;
    }

    private void loadAirlines(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(filePath).toFile()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Airline airline = parseAirline(line);
                if (airline != null) {
                    airlineDao.saveOrUpdate(airline);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filePath);
            e.printStackTrace();
        }
    }

    private Airline parseAirline(String csvLine) {
        StringTokenizer st = new StringTokenizer(csvLine, ",");
        Airline airline = new Airline();
        if (st.hasMoreTokens()) {
            airline.setCode(st.nextToken());
            airline.setName(st.nextToken());
            airline.setCountry(st.nextToken()); // Assuming setCountry accepts a String directly
        }
        return airline;
    }

    private void loadPlanes(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(filePath).toFile()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Plane plane = parsePlane(line);
                if (plane != null) {
                    planeDao.saveOrUpdate(plane);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filePath);
            e.printStackTrace();
        }
    }

    private Plane parsePlane(String csvLine) {
        StringTokenizer st = new StringTokenizer(csvLine, ",");
        Plane plane = new Plane();
        if (st.hasMoreTokens()) {
            plane.setCode(st.nextToken());
            plane.setModel(st.nextToken());
            plane.setCapacity(Integer.parseInt(st.nextToken()));
        }
        return plane;
    }

    private void loadFlights(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(filePath).toFile()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Flight flight = parseFlight(line);
                if (flight != null) {
                    flightDao.saveOrUpdateFlight(flight);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filePath);
            e.printStackTrace();
        }
    }

    private Flight parseFlight(String csvLine) {
        StringTokenizer st = new StringTokenizer(csvLine, ",");
        Flight flight = new Flight();
        if (st.hasMoreTokens()) {
            flight.setCode(st.nextToken());
            flight.setOrigen(findOrCreateAirport(st.nextToken()));
            flight.setDestino(findOrCreateAirport(st.nextToken()));
            flight.setAerolinea(findOrCreateAirline(st.nextToken()));
            flight.setAvion(findOrCreatePlane(st.nextToken()));
            flight.setDuracion(Integer.parseInt(st.nextToken()));
            flight.setSeats(Integer.parseInt(st.nextToken()));
            flight.setPrecio(Double.parseDouble(st.nextToken()));
        }
        return flight;
    }

    private void loadReservations(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(filePath).toFile()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Reservation reservation = parseReservation(line);
                if (reservation != null) {
                    reservationDao.saveOrUpdate(reservation);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filePath);
            e.printStackTrace();
        }
    }

    private Reservation parseReservation(String csvLine) {
        StringTokenizer st = new StringTokenizer(csvLine, ",");
        Reservation reservation = new Reservation();
        if (st.hasMoreTokens()) {
            reservation.setFlight(findOrCreateFlight(st.nextToken()));
            reservation.setUser(findOrCreateUser(st.nextToken())); // Assuming that this method exists and is correctly implemented
            reservation.setSeats(Integer.parseInt(st.nextToken()));
            reservation.setPrice(Double.parseDouble(st.nextToken()));
        }
        return reservation;
    }

    // Example method stubs for finding or creating related entities
    private Airport findOrCreateAirport(String airportName) {
        Airport airport = airportDao.findByName(airportName);
        if (airport == null) {
            airport = new Airport();
            airport.setName(airportName);
            // Additional attributes setup
            airportDao.saveOrUpdate(airport);
        }
        return airport;
    }

    private Airline findOrCreateAirline(String airlineName) {
        Airline airline = airlineDao.findByName(airlineName);
        if (airline == null) {
            airline = new Airline();
            airline.setName(airlineName);
            // Additional attributes setup
            airlineDao.saveOrUpdate(airline);
        }
        return airline;
    }

    private Plane findOrCreatePlane(String planeId) {
        Plane plane = planeDao.find(planeId);
        if (plane == null) {
            plane = new Plane();
            plane.setCode(planeId);
            // Additional attributes setup
            planeDao.saveOrUpdate(plane);
        }
        return plane;
    }
}
