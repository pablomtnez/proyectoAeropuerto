package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Flight;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class FlightDAO extends DataAccessObjectBase implements IDataAccessObject<Flight> {
    private static final Logger logger = LogManager.getLogger(FlightDAO.class);
    private static FlightDAO instance;

    public FlightDAO() {}

    public static FlightDAO getInstance() {
        if (instance == null) {
            instance = new FlightDAO();
        }
        return instance;
    }

    @Override
    public void save(Flight flight) {
        super.saveObject(flight);
    }

    @Override
    public void delete(Flight flight) {
        super.deleteObject(flight);
    }

    @Override
    public List<Flight> getAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        List<Flight> vuelos = new ArrayList<>();

        try {
            tx.begin();
            Extent<Flight> extent = pm.getExtent(Flight.class, true);
            for (Flight category : extent) {
                vuelos.add(category);
            }
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error retrieving all the flights: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return vuelos;
    }

    @Override
    public Flight find(String param) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        Flight result = null;

        try {
            tx.begin();
            Query query = pm.newQuery("SELECT FROM " + Flight.class.getName() + " WHERE code == '" + param + "'");
            query.setUnique(true);
            result = (Flight) query.execute();
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error querying a flight: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return result;
    }

    // Método saveOrUpdate
    public void saveOrUpdateFlights(Map<String, Flight> flightsMap) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
           tx.begin();
           for (Map.Entry<String, Flight> entry : flightsMap.entrySet()) {
            Flight flight = entry.getValue();
            Flight existing = find(flight.getCode());
            if (existing == null) {
                pm.makePersistent(flight);
                System.out.println("Flight saved: " + flight.getCode());
            } else {
                existing.setAirline(flight.getAirline());
                existing.setDuration(flight.getDuration());
                existing.setFrom(flight.getFrom());
                existing.setPlane(flight.getPlane());
                existing.setPrice(flight.getPrice());
                existing.setReservations(flight.getReservations());
                existing.setSeats(flight.getSeats());
                existing.setTo(flight.getTo());
                System.out.println("Flight updated: " + flight.getCode());
            }
           }
           tx.commit();
        } catch (Exception ex) {
            System.out.println("Error saving or updating Flight: " + ex.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            pm.close();
        }
    }

    public List<Flight> getAllFlights() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
    
        List<Flight> flights = new ArrayList<>();
    
        try {
            tx.begin();
            Query query = pm.newQuery(Flight.class);
            List<Flight> result = (List<Flight>) query.execute();
            flights.addAll(result);
            tx.commit();
        } catch (Exception ex) {
            logger.error("$ Error retrieving all flights: " + ex.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            pm.close();
        }
        return flights;
    }
    
}
