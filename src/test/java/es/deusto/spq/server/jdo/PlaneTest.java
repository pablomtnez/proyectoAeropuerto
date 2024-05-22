package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
        assertEquals("code", plane.getIataCode());
        assertEquals("name", plane.getName());
        assertEquals(200, plane.getSeats());
    }

    @Test
    public void testSetters() {
        plane.setIataCode("newCode");
        assertEquals("newCode", plane.getIataCode());

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

    @Test
    public void testParseCSV_ValidInput() {
        String csvData = "737,Boeing 737,189";
        try {
            Plane plane = Plane.parseCSV(csvData);
            assertNotNull(plane);
            assertEquals("737", plane.getIataCode());
            assertEquals("Boeing 737", plane.getName());
            assertEquals(189, plane.getSeats());
        } catch (Exception e) {
            fail("Exception should not be thrown for valid input");
        }
    }

    @Test
    public void testParseCSV_InvalidInput() {
        String csvData = "737,Boeing 737,invalid_seats";
        Exception exception = assertThrows(Exception.class, () -> {
            Plane.parseCSV(csvData);
        });
        String expectedMessage = "class es.deusto.spq.server.jdo.Plane from CSV error: " + csvData;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}