package es.deusto.spq.server.jdo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ReservationTest {

    private Reservation reservation;
    private Flight flightMock;
    private List<String> passengers;

    @Before
    public void setUp() {
        // Create a mock for the Flight class
        flightMock = mock(Flight.class);

        // Create a new Reservation instance
        reservation = new Reservation();

        // Initialize passengers list
        passengers = new ArrayList<>();
        passengers.add("John Doe");
        passengers.add("Jane Doe");

        // Set values for the reservation
        reservation.setLocator("ABC123");
        reservation.setFlight(flightMock);
        reservation.setDate(System.currentTimeMillis()); // Use current time
        reservation.setPassengers(passengers);
    }

    @Test
    public void testGetters() {
        assertEquals("ABC123", reservation.getLocator());
        assertEquals(flightMock, reservation.getFlight());
        assertNotNull(reservation.getDate());
        assertEquals(passengers, reservation.getPassengers());
    }

    @Test
    public void testSetters() {
        // Create a new flight instance
        Flight newFlightMock = mock(Flight.class);

        // Create a new list of passengers
        List<String> newPassengers = new ArrayList<>();
        newPassengers.add("Alice");
        newPassengers.add("Bob");

        // Set new values for the reservation
        reservation.setLocator("XYZ456");
        reservation.setFlight(newFlightMock);
        reservation.setDate(System.currentTimeMillis()); // Use current time
        reservation.setPassengers(newPassengers);

        // Check if values were updated
        assertEquals("XYZ456", reservation.getLocator());
        assertEquals(newFlightMock, reservation.getFlight());
        assertNotNull(reservation.getDate());
        assertEquals(newPassengers, reservation.getPassengers());
    }

    @Test
    public void testParseCSV_ValidInput() {
        String csvData = "ABC123#FL123#1622548800000#John Doe;Jane Doe";
        try {
            Reservation reservation = Reservation.parseCSV(csvData);
            assertNotNull(reservation);
            assertEquals("ABC123", reservation.getLocator());
            assertNotNull(reservation.getFlight());
            assertEquals("FL123", reservation.getFlight().getCode());
            assertEquals(1622548800000L, reservation.getDate());
            List<String> expectedPassengers = Arrays.asList("John Doe", "Jane Doe");
            assertEquals(expectedPassengers, reservation.getPassengers());
        } catch (Exception e) {
            fail("Exception should not be thrown for valid input");
        }
    }

    @Test
    public void testParseCSV_InvalidInput() {
        String csvData = "ABC123#FL123#invalid_date#John Doe;Jane Doe";
        Exception exception = assertThrows(Exception.class, () -> {
            Reservation.parseCSV(csvData);
        });
        String expectedMessage = "class es.deusto.spq.server.jdo.Reservation from CSV error: " + csvData;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}