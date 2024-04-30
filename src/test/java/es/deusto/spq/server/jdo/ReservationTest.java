package es.deusto.spq.server.jdo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
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
}