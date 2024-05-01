package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Flight;

public class FlightDAO extends DataAccessObjectBase implements IDataAccessObject<Flight> {

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

    public void saveOrUpdate(Flight flight) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();
            pm.makePersistent(flight); // Save or update
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error saving or updating a flight: " + ex.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            pm.close();
        }
    }
}
