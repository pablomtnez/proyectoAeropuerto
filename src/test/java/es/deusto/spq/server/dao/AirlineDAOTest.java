package es.deusto.spq.server.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import es.deusto.spq.server.dao.AirlineDAO;
import es.deusto.spq.server.jdo.Airline;

public class AirlineDAOTest {

    private AirlineDAO airlineDAO;
    private PersistenceManager pmMock;
    private Transaction txMock;

    @Before
    public void setUp() {
        airlineDAO = new AirlineDAO();
        pmMock = mock(PersistenceManager.class);
        txMock = mock(Transaction.class);

        airlineDAO.pmf = mock(PersistenceManagerFactory.class);
        when(airlineDAO.pmf.getPersistenceManager()).thenReturn(pmMock);
        when(pmMock.currentTransaction()).thenReturn(txMock);
    }

    @Test
    public void testGetInstance() {
        AirlineDAO instance1 = AirlineDAO.getInstance();
        AirlineDAO instance2 = AirlineDAO.getInstance();
        
        assertSame(instance1, instance2);
    }

    @Test
    public void testSave() {
        Airline airline = new Airline();

        airlineDAO.save(airline);

        verify(pmMock).makePersistent(airline);
    }

    @Test
    public void testDelete() {
        Airline airline = new Airline();

        airlineDAO.delete(airline);

        verify(pmMock).deletePersistent(airline);
    }

    @Test
    public void testGetAll() {
        Query queryMock = mock(Query.class);
        List<Airline> airlines = new ArrayList<>();

        when(pmMock.newQuery(Airline.class)).thenReturn(queryMock);
        when(queryMock.execute()).thenReturn(airlines);

        List<Airline> result = airlineDAO.getAll();

        assertEquals(airlines, result);
    }
}
