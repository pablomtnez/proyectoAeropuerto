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

import es.deusto.spq.server.dao.AirportDAO;
import es.deusto.spq.server.jdo.Airport;

public class AirportDAOTest {

    private AirportDAO airportDAO;
    private PersistenceManager pmMock;
    private Transaction txMock;
    private Extent<Airport> extentMock;

    @Before
    public void setUp() {
        airportDAO = new AirportDAO();
        pmMock = mock(PersistenceManager.class);
        txMock = mock(Transaction.class);
        extentMock = mock(Extent.class);

        airportDAO.pmf = mock(PersistenceManagerFactory.class);
        when(airportDAO.pmf.getPersistenceManager()).thenReturn(pmMock);
        when(pmMock.currentTransaction()).thenReturn(txMock);
    }

    @Test
    public void testGetInstance() {
        AirportDAO instance1 = AirportDAO.getInstance();
        AirportDAO instance2 = AirportDAO.getInstance();
        
        assertSame(instance1, instance2);
    }

    @Test
    public void testSave() {
        Airport airport = new Airport();

        airportDAO.save(airport);

        verify(pmMock).makePersistent(airport);
    }

    @Test
    public void testDelete() {
        Airport airport = new Airport();

        airportDAO.delete(airport);

        verify(pmMock).deletePersistent(airport);
    }

    @Test
    public void testGetAll() {
        List<Airport> airports = new ArrayList<>();
        airports.add(new Airport());

        when(pmMock.getExtent(Airport.class, true)).thenReturn(extentMock);
        when(extentMock.iterator()).thenReturn(airports.iterator());

        List<Airport> result = airportDAO.getAll();

        assertEquals(airports.size(), result.size());
    }

    @Test
    public void testFind() {
        String code = "ABC";
        Airport airport = new Airport();
        airport.setIataCode(code);

        Query queryMock = mock(Query.class);

        when(pmMock.newQuery("SELECT FROM " + Airport.class.getName() + " WHERE iataCode == '" + code + "'")).thenReturn(queryMock);
        when(queryMock.execute()).thenReturn(airport);

        Airport result = airportDAO.find(code);

        assertEquals(airport, result);
    }

    @Test
    public void testFindByName() {
        String name = "John F. Kennedy International Airport";
        Airport airport = new Airport();
        airport.setName(name);

        Query queryMock = mock(Query.class);

        when(pmMock.newQuery("SELECT FROM " + Airport.class.getName() + " WHERE name == '" + name + "'")).thenReturn(queryMock);
        when(queryMock.execute()).thenReturn(airport);

        Airport result = airportDAO.findByName(name);

        assertEquals(airport, result);
    }
}
