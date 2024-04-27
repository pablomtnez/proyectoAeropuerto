package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Flight;

public class FlightDAO extends DataAccessObjectBase implements IDataAccessObject<Flight> {

    private static FlightDAO instance;

    public FlightDAO() {
    }

    public static FlightDAO getInstance() {
        if (instance == null) {
            instance = new FlightDAO();
        }
        return instance;
    }

    @Override
    public void save(Flight flight) {
        super.saveObject(flight);
    }

    @Override
    public void delete(Flight object) {
        super.deleteObject(object);
    }

    @Override
    public List<Flight> getAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        List<Flight> Vuelos = new ArrayList<>();

        try {
            tx.begin();

            Extent<Flight> extent = pm.getExtent(Flight.class, true);
            for (Flight category : extent) {
                Vuelos.add(category);
            }
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error retrieving all the Vuelos: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return Vuelos;
    }

    @Override
    public Flight find(String param) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        Flight result = null;

        try {
            tx.begin();
            Query query = pm.newQuery("SELECT FROM " + Flight.class.getName() + " WHERE code == '" + param + "'");
            query.setUnique(true);
            result = (Flight) query.execute();
            tx.commit();
        } catch (Exception ex) {
            System.out.println("  $ Error querying an Vuelo: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            pm.close();
        }
        return result;
    }

    /**
 * Saves or updates a flight in the database.
 * @param flight The flight to be saved or updated.
 */
public void saveOrUpdateFlight(Flight flight) {
    PersistenceManager pm = pmf.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
        tx.begin();
        // Check if the flight already exists in the database
        Flight existingFlight = pm.getObjectById(Flight.class, flight.getCode());
        if (existingFlight == null) {
            // If not, make it persistent (save)
            pm.makePersistent(flight);
        } else {
            // If it exists, update the existing record
            existingFlight.setOrigen(flight.getOrigen());
            existingFlight.setDestino(flight.getDestino());
            existingFlight.setAerolinea(flight.getAerolinea());
            existingFlight.setAvion(flight.getAvion());
            existingFlight.setReservas(flight.getReservas());
            existingFlight.setDuracion(flight.getDuracion());
            existingFlight.setSeats(flight.getSeats());
            existingFlight.setPrecio(flight.getPrecio());
        }
        tx.commit();
    } catch (Exception e) {
        System.err.println("Error saving or updating the flight: " + flight.getCode());
        e.printStackTrace();
        if (tx.isActive()) {
            tx.rollback();
        }
    } finally {
        pm.close();
    }
}


}
