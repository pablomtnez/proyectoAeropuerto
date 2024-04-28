package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Plane;

public class PlaneDAO extends DataAccessObjectBase implements IDataAccessObject<Plane> {

    private static PlaneDAO instance;

    public PlaneDAO(){}

    public static PlaneDAO getInstance(){
        if (instance == null){
            instance = new PlaneDAO();
        }
        return instance;
    }

    @Override
    public void save(Plane object) {
        super.saveObject(object);
    }

    @Override
    public void delete(Plane object) {
        super.deleteObject(object);
    }

    @Override
    public List<Plane> getAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        List<Plane> Aviones = new ArrayList<>();
        try {
            tx.begin();
            Extent<Plane> extent = pm.getExtent(Plane.class, true);
            for (Plane category : extent){
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
    public Plane find(String param) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        Plane result = null;

        try {
            tx.begin();

            Query query = pm.newQuery("SELECT FROM " + Plane.class.getName() + " WHERE code == '" + param+"'");
            query.setUnique(true);
            result = (Plane) query.execute();
        } catch (Exception ex) {
            System.out.println("  $ Error querying an Avion: " + ex.getMessage());
        }finally {
            if (tx != null && tx.isActive()){
                tx.rollback();
            }
        }
        return result;
    }

    public void saveOrUpdate(Plane plane) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.makePersistent(plane);
            tx.commit();
        } catch (Exception ex) {
            System.out.println("Error saving or updating a Plane: " + ex.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            pm.close();
        }
    }    
    
}
