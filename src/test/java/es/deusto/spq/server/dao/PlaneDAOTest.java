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

import es.deusto.spq.server.dao.PlaneDAO;
import es.deusto.spq.server.jdo.Plane;

public class PlaneDAOTest {

    private PlaneDAO planeDAO;
    private PersistenceManager pmMock;
    private Transaction txMock;
    private Extent<Plane> extentMock;

    @Before
    public void setUp() {
        planeDAO = new PlaneDAO();
        pmMock = mock(PersistenceManager.class);
        txMock = mock(Transaction.class);
        extentMock = mock(Extent.class);

        planeDAO.pmf = mock(PersistenceManagerFactory.class);
        when(planeDAO.pmf.getPersistenceManager()).thenReturn(pmMock);
        when(pmMock.currentTransaction()).thenReturn(txMock);
    }

    @Test
    public void testGetInstance() {
        PlaneDAO instance1 = PlaneDAO.getInstance();
        PlaneDAO instance2 = PlaneDAO.getInstance();
        
        assertSame(instance1, instance2);
    }

    @Test
    public void testSave() {
        Plane plane = new Plane();

        planeDAO.save(plane);

        verify(pmMock).makePersistent(plane);
    }

    @Test
    public void testDelete() {
        Plane plane = new Plane();

        planeDAO.delete(plane);

        verify(pmMock).deletePersistent(plane);
    }

    @Test
    public void testGetAll() {
        List<Plane> planes = new ArrayList<>();
        planes.add(new Plane());

        when(pmMock.getExtent(Plane.class, true)).thenReturn(extentMock);
        when(extentMock.iterator()).thenReturn(planes.iterator());

        List<Plane> result = planeDAO.getAll();

        assertEquals(planes.size(), result.size());
    }

    @Test
    public void testFind() {
        String code = "PL123";
        Plane plane = new Plane();
        plane.setIataCode(code);

        Query queryMock = mock(Query.class);

        when(pmMock.newQuery(Plane.class, "iataCode == codeParam")).thenReturn(queryMock);
        when(queryMock.execute(code)).thenReturn(plane);

        Plane result = planeDAO.find(code);

        assertEquals(plane, result);
    }
}

