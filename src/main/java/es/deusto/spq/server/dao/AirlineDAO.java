package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import es.deusto.spq.server.jdo.Airline;

public class AirlineDAO extends DataAccessObjectBase implements IDataAccessObject<Airline> {

    private static AirlineDAO instance;

    public AirlineDAO() {
    }

    public static AirlineDAO getInstance() {
        if (instance == null) {
            instance = new AirlineDAO();
        }
        return instance;
    }

    @Override
    public void save(Airline object) {
        super.saveObject(object);
    }

    @Override
    public void delete(Airline object) {
        super.deleteObject(object);
    }

    @Override
    public List<Airline> getAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        List<Airline> airlines = new ArrayList<>();
        try {
            tx.begin();
            Extent<Airline> extent = pm.getExtent(Airline.class, true);
            for (Airline airline : extent) {
                airlines.add(airline);
            }
            tx.commit();
        } catch (Exception ex) {
            System.out.println("Error retrieving all airlines: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return airlines;
    }

    public Airline find(String iataCode) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        Airline result = null;
        try {
            tx.begin();
            Query query = pm.newQuery(Airline.class, "iataCode == iataCodeParam");
            query.declareParameters("String iataCodeParam");
            query.setUnique(true);
            result = (Airline) query.execute(iataCode);
            tx.commit();
        } catch (Exception ex) {
            System.out.println("Error querying an Airline: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return result;
    }

    public void saveOrUpdateAirlines(Map<String, Airline> airlinesMap) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            for (Map.Entry<String, Airline> entry : airlinesMap.entrySet()) {
                Airline airline = entry.getValue();
                Airline existing = find(airline.getIataCode());
                if (existing == null) {
                    pm.makePersistent(airline);
                    System.out.println("Airline saved: " + airline.getIataCode());
                } else {
                    existing.setName(airline.getName());
                    existing.setCountry(airline.getCountry());
                    existing.setAlliance(airline.getAlliance());
                    System.out.println("Airline updated: " + airline.getIataCode());
                }
            }
            tx.commit();
        } catch (Exception ex) {
            System.out.println("Error saving or updating Airlines: " + ex.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            pm.close();
        }
    }
}
