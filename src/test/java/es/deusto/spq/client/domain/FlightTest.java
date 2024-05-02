package es.deusto.spq.client.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FlightTest {

    private Flight flight;
    private Airport originMock;
    private Airport destinationMock;
    private Airline airlineMock;
    private Plane planeMock;
    private List<Reservation> reservationsMock;

    @Before
    public void setUp() {
        // Create mock objects for dependencies
        originMock = mock(Airport.class);
        destinationMock = mock(Airport.class);
        airlineMock = mock(Airline.class);
        planeMock = mock(Plane.class);

        // Initialize reservations list
        reservationsMock = new ArrayList<>();

        // Create instance of Flight with mock objects
        flight = new Flight("FL123", originMock, destinationMock, airlineMock, planeMock, 120, 100.0f);
        flight.setReservations(reservationsMock);
    }

    @Test
    public void testGetters() {
        assertEquals("FL123", flight.getCode());
        assertEquals(originMock, flight.getFrom());
        assertEquals(destinationMock, flight.getTo());
        assertEquals(airlineMock, flight.getAirline());
        assertEquals(planeMock, flight.getPlane());
        assertEquals(120, flight.getDuration());
        assertEquals(100.0f, flight.getPrice(), 0.001);
        assertEquals(reservationsMock, flight.getReservations());
    }
    /* 
    @Test
    public void testRemainingSeats() {
        // Mocking Plane seats
        when(planeMock.getSeats()).thenReturn(200);

        // Add reservations to mock list
        Reservation reservationMock1 = mock(Reservation.class);
        Reservation reservationMock2 = mock(Reservation.class);
        reservationsMock.add(reservationMock1);
        reservationsMock.add(reservationMock2);

        // Ensure remaining seats are calculated correctly
        assertEquals(200 - 2, flight.getRemainingSeats());
    }
    */
    @Test
    public void testEquals() {
        Flight sameFlight = new Flight("FL123", originMock, destinationMock, airlineMock, planeMock, 120, 100.0f);
        Flight differentFlight = new Flight("FL456", originMock, destinationMock, airlineMock, planeMock, 120, 100.0f);

        assertTrue(flight.equals(sameFlight));
        assertFalse(flight.equals(differentFlight));

        // Test against null and different class
        assertFalse(flight.equals(null));
        assertFalse(flight.equals("string"));
    }

    @Test
    public void testHashCode() {
        Flight sameFlight = new Flight("FL123", originMock, destinationMock, airlineMock, planeMock, 120, 100.0f);
        assertEquals(flight.hashCode(), sameFlight.hashCode());
    }
    /* 
    @Test
    public void testToString() {
        // Mocking Airport codes
        when(originMock.getCode()).thenReturn("AAA");
        when(destinationMock.getCode()).thenReturn("BBB");

        // Ensure price is formatted correctly
        String expectedToString = "FL123: AAA -> BBB (0120 min., 198 seats, 100.00€)";
        assertEquals(expectedToString, flight.toString());
    }
    */
}