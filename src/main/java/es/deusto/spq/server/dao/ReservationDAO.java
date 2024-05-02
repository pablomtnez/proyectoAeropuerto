package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.jdo.Reservation;

public class ReservationDAO extends DataAccessObjectBase implements IDataAccessObject<Reservation> {

    private static final Logger logger = LogManager.getLogger(ReservationDAO.class);
    private static ReservationDAO instance;

    ReservationDAO() {}

    public static ReservationDAO getInstance() {
        if (instance == null) {
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
        List<Reservation> reservas = new ArrayList<>();
        try {
            tx.begin();
            Extent<Reservation> extent = pm.getExtent(Reservation.class, true);
            for (Reservation category : extent) {
                reservas.add(category);
            }
            tx.commit();
        } catch (Exception ex) {
            logger.error("Error retrieving all Reservations: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return reservas;
    }

    @Override
    public Reservation find(String locator) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        Reservation result = null;
        try {
            tx.begin();
            Query query = pm.newQuery(Reservation.class, "locator == locatorParam");
            query.declareParameters("String locatorParam");
            query.setUnique(true);
            result = (Reservation) query.execute(locator);
            tx.commit();
        } catch (Exception ex) {
            logger.error("Error consultando una Reserva: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return result;
    }
    


    public void saveOrUpdate(Map<String, List<Reservation>> reservationsMap) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            for (List<Reservation> reservationList : reservationsMap.values()) {
                for (Reservation reservation : reservationList) {
                    Reservation existingReservation = find(reservation.getLocator());
                    if (existingReservation == null) {
                        logger.debug("Inserting new reservation: " + reservation.getLocator());
                        pm.makePersistent(reservation);
                        logger.info("Reserva guardada: " + reservation.getLocator());
                    } else {
                        logger.debug("Updating existing reservation: " + reservation.getLocator());
                        existingReservation.setFlight(reservation.getFlight());
                        existingReservation.setDate(reservation.getDate());
                        existingReservation.setPassengers(reservation.getPassengers());
                        pm.makePersistent(existingReservation); // Asegura el estado persistente
                        logger.info("Reserva actualizada: " + reservation.getLocator());
                    }
                }
            }
            tx.commit();
        } catch (Exception e) {
            logger.error("Error guardando o actualizando una reserva: " + e.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            pm.close();
        }
    }
    public List<Reservation> getAllReservations() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        List<Reservation> reservations = new ArrayList<>();
        try {
            tx.begin();
            Query query = pm.newQuery(Reservation.class);
            List<Reservation> result = (List<Reservation>) query.execute();
            reservations.addAll(result);
            tx.commit();
        } catch (Exception ex) {
            logger.error("Error retrieving all reservations: " + ex.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            pm.close();
        }
        return reservations;
    }

}
