package es.deusto.spq.client.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ReservationTest {

    private Reservation reservation;
    private Flight flightMock;
    private FlightClass flightClassMock;
    private List<String> passengers;

    @Before
    public void setUp() {
        // Create a mock flight object
        flightMock = Mockito.mock(Flight.class);
        flightClassMock = Mockito.mock(FlightClass.class);

        // Initialize passengers list
        passengers = new ArrayList<>();
        passengers.add("John Doe");
        passengers.add("Jane Doe");

        // Create a reservation object
        reservation = new Reservation("ABC123", flightMock, 1632530400L, passengers, flightClassMock);
    }

    @Test
    public void testGetters() {
        assertEquals("ABC123", reservation.getLocator());
        assertEquals(flightMock, reservation.getFlight());
        assertEquals(1632530400L, reservation.getDate());
        assertEquals(passengers, reservation.getPassengers());
        assertEquals(flightClassMock, reservation.getflightclass());
    }

    @Test
    public void testEquals() {
        Reservation sameReservation = new Reservation("ABC123", flightMock, 1632530400L, passengers, flightClassMock);
        Reservation differentReservation = new Reservation("XYZ789", flightMock, 1632530400L, passengers, flightClassMock);

        assertTrue(reservation.equals(sameReservation));
        assertFalse(reservation.equals(differentReservation));

        // Test against null and different class
        assertFalse(reservation.equals(null));
        assertFalse(reservation.equals("string"));
    }

    @Test
    public void testHashCode() {
        Reservation sameReservation = new Reservation("ABC123", flightMock, 1632530400L, passengers, flightClassMock);
        assertEquals(reservation.hashCode(), sameReservation.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "ABC123: " + flightMock.toString() + " - 1632530400 (02 passengers)";
        assertEquals(expectedToString, reservation.toString());
    }
}
