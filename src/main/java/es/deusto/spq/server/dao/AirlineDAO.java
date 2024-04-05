package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Airline;

public class AirlineDAO extends DataAccessObjectBase implements IDataAccessObject<Airline> {

    private static AirlineDAO instance;

    private AirlineDAO(){
        
    }

    public static AirlineDAO getInstance() {
        if(instance == null) {
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

        List<Airline> Aerolineas = new ArrayList<>();

        try {
            tx.begin();
            Extent<Airline> extent = pm.getExtent(Airline.class, true);
            for(Airline category : extent){
                Aerolineas.add(category);
            }
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error retrieving all the Aerolineas: " + ex.getMessage());
        }finally {
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
        return Aerolineas; 
    }

    @Override
    public Airline find(String param) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        Airline result = null;

        try {
            tx.begin();

            Query query = pm.newQuery("SELECT FROM " + Airline.class.getName() + " WHERE code == '" + param +"'");
            query.setUnique(true);
            result = (Airline) query.execute();

            tx.commit();
        } catch (Exception ex) {
           System.out.println("  $ Error querying an Aerolinea: " + ex.getMessage());
        }finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return result;
    }
    
}