package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Airport;

public class AirportDAO extends DataAccessObjectBase implements IDataAccessObject<Airport>{

    private static AirportDAO instance;

    public AirportDAO(){}

    public static AirportDAO getInstance(){
        if(instance==null){
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

        List<Airport> Aeropuertos = new ArrayList<>();

        try {
            tx.begin();
            Extent<Airport> extent = pm.getExtent(Airport.class, true);
            for(Airport category : extent){
                Aeropuertos.add(category);
            }
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error retrieving all the Aeropuertos: " + ex.getMessage());
        }finally {
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
        return Aeropuertos;
    }

    @Override
    public Airport find(String param) {
       PersistenceManager pm = pmf.getPersistenceManager();
       Transaction tx = pm.currentTransaction();

       Airport result = null;
       try {
            tx.begin();
            Query query = pm.newQuery("SELECT FROM " + Airport.class.getName() + " WHERE code == '" + param+"'");
            query.setUnique(true);
            result = (Airport) query.execute();
       } catch (Exception ex) {
            System.out.println("  $ Error querying an Aeropuerto: " + ex.getMessage());
       }finally{
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
            pm.close();
       }
       return result;
    }

    @Override
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
    
    public void saveOrUpdate(Airport airport) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            // Si el aeropuerto ya tiene un ID, se actualizará; si no, se guardará como nuevo
            pm.makePersistent(airport);
            tx.commit();
        } catch (Exception ex) {
            System.out.println("Error saving or updating an Airport: " + ex.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            pm.close();
        }
    }
    
}
