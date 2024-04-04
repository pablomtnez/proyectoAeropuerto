package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Aerolinea;

public class AerolineaDAO extends DataAccessObjectBase implements IDataAccessObject<Aerolinea> {

    private static AerolineaDAO instance;

    private AerolineaDAO(){
        
    }

    public static AerolineaDAO getInstance() {
        if(instance == null) {
            instance = new AerolineaDAO();
        }
        return instance;
    }

    @Override
    public void save(Aerolinea object) {
       super.saveObject(object);
    }

    @Override
    public void delete(Aerolinea object) {
        super.deleteObject(object);
    }

    @Override
    public List<Aerolinea> getAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        List<Aerolinea> Aerolineas = new ArrayList<>();

        try {
            tx.begin();
            Extent<Aerolinea> extent = pm.getExtent(Aerolinea.class, true);
            for(Aerolinea category : extent){
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
    public Aerolinea find(String param) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        Aerolinea result = null;

        try {
            tx.begin();

            Query query = pm.newQuery("SELECT FROM " + Aerolinea.class.getName() + " WHERE code == '" + param+"'");
            query.setUnique(true);
            result = (Aerolinea) query.execute();

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
