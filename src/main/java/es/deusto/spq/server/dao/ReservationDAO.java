package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.jdo.Reservation;

public class ReservationDAO extends DataAccessObjectBase implements IDataAccessObject<Reservation>{

    private static final Logger logger = LogManager.getLogger(ReservationDAO.class);
    private static ReservationDAO instance;

    private ReservationDAO(){}

    public static ReservationDAO getInstance(){
        if(instance == null){
            instance = new ReservationDAO();
        }
        return instance;
    }

    @Override
    public void save(Reservation object) {
        super.saveObject(object);
    }

    @Override
    public void delete(Reservation object) {
        super.deleteObject(object);
    }

    @Override
    public List<Reservation> getAll() {
       PersistenceManager pm = pmf.getPersistenceManager();
       Transaction tx = pm.currentTransaction();
       List<Reservation> reservas = new ArrayList<Reservation>();
       try {
        tx.begin();
        Extent<Reservation> extent = pm.getExtent(Reservation.class, true);
        for (Reservation category : extent) {
            reservas.add(category);
        }
        tx.commit();
       } catch (Exception ex) {
        logger.error("$ Error retrieving all the Reservations: " + ex.getMessage());
       }finally {
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }
       }
       return reservas;
    }

    @Override
    public Reservation find(String param) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        Reservation result = null;
        try {
            tx.begin();
            Query<Reservation> query = pm.newQuery(Reservation.class);
            query.setFilter("code ==:param");
            query.setUnique(true);
            result = (Reservation) query.execute(param);
            tx.commit();
        } catch (Exception ex) {
            logger.error("$ Error querying a Reservation: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return result;
    }

    public void saveOrUpdate(Reservation reservation) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            // Verificar si la reserva ya existe en la base de datos
            Reservation existingReservation = pm.getObjectById(Reservation.class, reservation.getLocator());
            if (existingReservation != null) {
                // Actualizar datos existentes
                pm.makePersistent(reservation);  // Esto actualizará la reserva existente
            } else {
                // Crear una nueva reserva
                pm.makePersistent(reservation);
            }
            tx.commit();
        } catch (Exception e) {
            System.err.println("Error saving or updating a reservation: " + e.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            pm.close();
        }
    }
}

