package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PlaneTest {

    Plane plane;

    @Before
    public void setUp() {
        plane = new Plane("code", "name", 200);
    }

    @Test
    public void testGetters() {
        assertEquals("code", plane.getCode());
        assertEquals("name", plane.getName());
        assertEquals(200, plane.getSeats());
    }

    @Test
    public void testSetters() {
        plane.setCode("newCode");
        assertEquals("newCode", plane.getCode());

        plane.setName("newName");
        assertEquals("newName", plane.getName());

        plane.setSeats(250);
        assertEquals(250, plane.getSeats());
    }

    @Test
    public void testToString() {
        String expectedToString = "code: name (200 seats)";
        assertEquals(expectedToString, plane.toString());
    }
}