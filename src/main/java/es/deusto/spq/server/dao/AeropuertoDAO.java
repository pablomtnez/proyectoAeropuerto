package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Aeropuerto;

public class AeropuertoDAO extends DataAccessObjectBase implements IDataAccessObject<Aeropuerto>{

    private static AeropuertoDAO instance;

    private AeropuertoDAO(){}

    public static AeropuertoDAO getInstance(){
        if(instance==null){
            instance = new AeropuertoDAO();
        }
        return instance;
    }

    @Override
    public void save(Aeropuerto object) {
        super.saveObject(object);
    }

    @Override
    public void delete(Aeropuerto object) {
        super.deleteObject(object);
    }

    @Override
    public List<Aeropuerto> getAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        List<Aeropuerto> Aeropuertos = new ArrayList<>();

        try {
            tx.begin();
            Extent<Aeropuerto> extent = pm.getExtent(Aeropuerto.class, true);
            for(Aeropuerto category : extent){
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
    public Aeropuerto find(String param) {
       PersistenceManager pm = pmf.getPersistenceManager();
       Transaction tx = pm.currentTransaction();

       Aeropuerto result = null;
       try {
            tx.begin();
            Query query = pm.newQuery("SELECT FROM " + Aeropuerto.class.getName() + " WHERE code == '" + param+"'");
            query.setUnique(true);
            result = (Aeropuerto) query.execute();
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

}
