package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Airport;

public class AirportDAO extends DataAccessObjectBase implements IDataAccessObject<Airport> {

    private static AirportDAO instance;

    public AirportDAO() {}

    public static AirportDAO getInstance() {
        if (instance == null) {
            instance = new AirportDAO();
        }
        return instance;
    }

    @Override
    public void save(Airport object) {
        super.saveObject(object);
    }

    @Override
    public void delete(Airport object) {
        super.deleteObject(object);
    }

    @Override
    public List<Airport> getAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        List<Airport> aeropuertos = new ArrayList<>();

        try {
            tx.begin();
            Extent<Airport> extent = pm.getExtent(Airport.class, true);
            for (Airport category : extent) {
                aeropuertos.add(category);
            }
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error retrieving all the Airports: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return aeropuertos;
    }

    @Override
    public Airport find(String param) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        Airport result = null;

        try {
            tx.begin();
            Query query = pm.newQuery("SELECT FROM " + Airport.class.getName() + " WHERE iataCode == '" + param + "'");
            query.setUnique(true);
            result = (Airport) query.execute();
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error querying an Airport: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return result;
    }

    public void saveOrUpdateAirports(Map<String, Airport> airportsMap) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin(); // Start transaction
            for (Map.Entry<String, Airport> entry : airportsMap.entrySet()) {
                Airport airport = entry.getValue();
                Airport existing = find(airport.getIataCode());
                if (existing == null) {
                    pm.makePersistent(airport);
                    System.out.println("Airport saved: " + airport.getIataCode());
                } else {
                    existing.setName(airport.getName());
                    existing.setCity(airport.getCity());
                    existing.setCountry(airport.getCountry());
                    System.out.println("Airport updated: " + airport.getIataCode());
                }
            }
            tx.commit(); // Commit transaction
        } catch (Exception ex) {
            System.out.println("Error saving or updating Airports: " + ex.getMessage());
            if (tx.isActive()) {
                tx.rollback(); // Rollback if there's an error
            }
        } finally {
            pm.close(); // Always close PersistenceManager
        }
    }
    

    public Airport findByName(String name) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        Airport result = null;
        try {
            tx.begin();
            Query query = pm.newQuery("SELECT FROM " + Airport.class.getName() + " WHERE name == '" + name + "'");
            query.setUnique(true);
            result = (Airport) query.execute();
        } catch (Exception ex) {
            System.out.println("  $ Error querying an Airport by name: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return result;
    }   
}
