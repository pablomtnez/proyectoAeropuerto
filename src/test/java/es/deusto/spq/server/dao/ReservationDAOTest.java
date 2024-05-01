package es.deusto.spq.server.dao;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import es.deusto.spq.server.dao.ReservationDAO;
import es.deusto.spq.server.jdo.Reservation;

public class ReservationDAOTest {

    private ReservationDAO reservationDAO;
    private PersistenceManager pmMock;
    private Transaction txMock;
    private Extent<Reservation> extentMock;

    @Before
    public void setUp() {
        reservationDAO = new ReservationDAO();
        pmMock = mock(PersistenceManager.class);
        txMock = mock(Transaction.class);
        extentMock = mock(Extent.class);

        reservationDAO.pmf = mock(PersistenceManagerFactory.class);
        when(reservationDAO.pmf.getPersistenceManager()).thenReturn(pmMock);
        when(pmMock.currentTransaction()).thenReturn(txMock);
    }

    @Test
    public void testGetInstance() {
        ReservationDAO instance1 = ReservationDAO.getInstance();
        ReservationDAO instance2 = ReservationDAO.getInstance();
        
        assertSame(instance1, instance2);
    }

    @Test
    public void testSave() {
        Reservation reservation = new Reservation();

        reservationDAO.save(reservation);

        verify(pmMock).makePersistent(reservation);
    }

    @Test
    public void testDelete() {
        Reservation reservation = new Reservation();

        reservationDAO.delete(reservation);

        verify(pmMock).deletePersistent(reservation);
    }

    @Test
    public void testGetAll() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());

        when(pmMock.getExtent(Reservation.class, true)).thenReturn(extentMock);
        when(extentMock.iterator()).thenReturn(reservations.iterator());

        List<Reservation> result = reservationDAO.getAll();

        assertEquals(reservations.size(), result.size());
    }

    @Test
    public void testFind() {
        String locator = "LOC123";
        Reservation reservation = new Reservation();
        reservation.setLocator(locator);

        Query queryMock = mock(Query.class);

        when(pmMock.newQuery(Reservation.class, "locator == locatorParam")).thenReturn(queryMock);
        when(queryMock.execute(locator)).thenReturn(reservation);

        Reservation result = reservationDAO.find(locator);

        assertEquals(reservation, result);
    }

    @Test
    public void testSaveOrUpdate() {
        Map<String, List<Reservation>> reservationsMap = new HashMap<>();
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservation.setLocator("LOC123");
        reservationList.add(reservation);
        reservationsMap.put("LOC123", reservationList);

        when(pmMock.currentTransaction()).thenReturn(txMock);
        when(txMock.isActive()).thenReturn(true);

        reservationDAO.saveOrUpdate(reservationsMap);

        verify(pmMock).makePersistent(reservation);
    }
}
