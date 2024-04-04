package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Avion;

public class AvionDAO extends DataAccessObjectBase implements IDataAccessObject<Avion> {

    private static AvionDAO instance;

    private AvionDAO(){}

    public static AvionDAO getInstance(){
        if (instance == null){
            instance = new AvionDAO();
        }
        return instance;
    }

    @Override
    public void save(Avion object) {
        super.saveObject(object);
    }

    @Override
    public void delete(Avion object) {
        super.deleteObject(object);
    }

    @Override
    public List<Avion> getAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        List<Avion> Aviones = new ArrayList<>();
        try {
            tx.begin();
            Extent<Avion> extent = pm.getExtent(Avion.class, true);
            for (Avion category : extent){
                Aviones.add(category);
            }
            tx.commit();

        } catch (Exception ex) {
            System.out.println("  $ Error retrieving all the Aviones: " + ex.getMessage());
        }finally{
            if (tx != null && tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
        return Aviones;
    }

    @Override
    public Avion find(String param) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        Avion result = null;

        try {
            tx.begin();

            Query query = pm.newQuery("SELECT FROM " + Avion.class.getName() + " WHERE code == '" + param+"'");
            query.setUnique(true);
            result = (Avion) query.execute();
        } catch (Exception ex) {
            System.out.println("  $ Error querying an Avion: " + ex.getMessage());
        }finally {
            if (tx != null && tx.isActive()){
                tx.rollback();
            }
        }
        return result;
    }
    
}
