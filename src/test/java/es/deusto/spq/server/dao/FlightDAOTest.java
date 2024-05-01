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

import es.deusto.spq.server.dao.FlightDAO;
import es.deusto.spq.server.jdo.Flight;

public class FlightDAOTest {

    private FlightDAO flightDAO;
    private PersistenceManager pmMock;
    private Transaction txMock;
    private Extent<Flight> extentMock;

    @Before
    public void setUp() {
        flightDAO = new FlightDAO();
        pmMock = mock(PersistenceManager.class);
        txMock = mock(Transaction.class);
        extentMock = mock(Extent.class);

        flightDAO.pmf = mock(PersistenceManagerFactory.class);
        when(flightDAO.pmf.getPersistenceManager()).thenReturn(pmMock);
        when(pmMock.currentTransaction()).thenReturn(txMock);
    }

    @Test
    public void testGetInstance() {
        FlightDAO instance1 = FlightDAO.getInstance();
        FlightDAO instance2 = FlightDAO.getInstance();
        
        assertSame(instance1, instance2);
    }

    @Test
    public void testSave() {
        Flight flight = new Flight();

        flightDAO.save(flight);

        verify(pmMock).makePersistent(flight);
    }

    @Test
    public void testDelete() {
        Flight flight = new Flight();

        flightDAO.delete(flight);

        verify(pmMock).deletePersistent(flight);
    }

    @Test
    public void testGetAll() {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight());

        when(pmMock.getExtent(Flight.class, true)).thenReturn(extentMock);
        when(extentMock.iterator()).thenReturn(flights.iterator());

        List<Flight> result = flightDAO.getAll();

        assertEquals(flights.size(), result.size());
    }

    @Test
    public void testFind() {
        String code = "FL123";
        Flight flight = new Flight();
        flight.setCode(code);

        Query queryMock = mock(Query.class);

        when(pmMock.newQuery("SELECT FROM " + Flight.class.getName() + " WHERE code == '" + code + "'")).thenReturn(queryMock);
        when(queryMock.execute()).thenReturn(flight);

        Flight result = flightDAO.find(code);

        assertEquals(flight, result);
    }
}

