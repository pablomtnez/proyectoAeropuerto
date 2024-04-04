package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Vuelo;

public class VueloDAO extends DataAccessObjectBase implements IDataAccessObject<Vuelo>{

    private static VueloDAO instance;

    private VueloDAO(){}

    public static VueloDAO getInstance(){
        if (instance == null){
            instance = new VueloDAO();
        }
        return instance;
    }

    @Override
    public void save(Vuelo object) {
        super.saveObject(object);
    }

    @Override
    public void delete(Vuelo object) {
        super.deleteObject(object);
    }

    @Override
    public List<Vuelo> getAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        List<Vuelo> Vuelos = new ArrayList<>();

        try {
            tx.begin();

            Extent<Vuelo> extent = pm.getExtent(Vuelo.class, true);
            for(Vuelo category : extent){
                Vuelos.add(category);
            }
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error retrieving all the Vuelos: " + ex.getMessage());
        }finally{
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
        return Vuelos;
    }

    @Override
    public Vuelo find(String param) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        Vuelo result = null;

        try {
            tx.begin();
            Query query = pm.newQuery("SELECT FROM " + Vuelo.class.getName() + " WHERE code == '" + param+"'");
            query.setUnique(true);
            result = (Vuelo)query.execute();
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error querying an Vuelo: " + ex.getMessage());
        }finally {
            if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
        }
        return result;
    }
    
}
