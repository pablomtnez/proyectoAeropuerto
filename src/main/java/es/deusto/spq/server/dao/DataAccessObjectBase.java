package es.deusto.spq.server.dao;

import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

public class DataAccessObjectBase {

    protected static PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

    public void deleteObject(Object object) {
       PersistenceManager pm = pmf.getPersistenceManager();
       Transaction tx = pm.currentTransaction();

       try {
        tx.begin();
        pm.deletePersistent(object);
        tx.commit();
       } catch (Exception ex) {
        System.out.println(" $ Error deleting an object: " + ex.getMessage());
       }finally {
        if(tx != null && tx.isActive()) {
            tx.rollback();
        }
        pm.close();
       }
    }

    public void saveObject(Object object){
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.makePersistent(object);
            tx.commit();
        } catch (Exception ex) {
            System.out.println(" $ Error storing an object: " + ex.getMessage());
        }finally{
            if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
        }
    }
    
}
