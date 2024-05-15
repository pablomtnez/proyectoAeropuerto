package es.deusto.spq.client.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PlaneTest {

    private Plane plane;

    @Before
    public void setUp() {
        plane = new Plane("PL123", "Boeing 737", 150);
    }

    @Test
    public void testGetters() {
        assertEquals("PL123", plane.getCode());
        assertEquals("Boeing 737", plane.getName());
        assertEquals(150, plane.getSeats());
    }

    @Test
    public void testEquals() {
        Plane samePlane = new Plane("PL123", "Boeing 737", 150);
        Plane differentPlane = new Plane("PL456", "Airbus A320", 180);

        assertTrue(plane.equals(samePlane));
        assertFalse(plane.equals(differentPlane));

        // Test against null and different class
        assertFalse(plane.equals(null));
        assertFalse(plane.equals("string"));
    }

    @Test
    public void testHashCode() {
        Plane samePlane = new Plane("PL123", "Boeing 737", 150);
        assertEquals(plane.hashCode(), samePlane.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "PL123: Boeing 737 (150 seats)";
        assertEquals(expectedToString, plane.toString());
    }
}
