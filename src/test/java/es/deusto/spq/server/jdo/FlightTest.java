package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class FlightTest {

    Flight flight;
    Airport originMock;
    Airport destinationMock;
    Airline airlineMock;
    Plane planeMock;
    List<Reservation> reservationsMock;

    @Before
    public void setUp() {
        originMock = mock(Airport.class);
        destinationMock = mock(Airport.class);
        airlineMock = mock(Airline.class);
        planeMock = mock(Plane.class);
        reservationsMock = new ArrayList<>();
        flight = new Flight("code", originMock, destinationMock, airlineMock, planeMock, 60, 100);
    }

    @Test
    public void testGetters() {
        assertEquals("code", flight.getCode());
        assertEquals(originMock, flight.getFrom());
        assertEquals(destinationMock, flight.getTo());
        assertEquals(airlineMock, flight.getAirline());
        assertEquals(planeMock, flight.getPlane());
        assertEquals(60, flight.getDuration());
        assertEquals(100.0f, flight.getPrice(), 0.001);
    }

    @Test
    public void testSetters() {
        Airport newOrigin = mock(Airport.class);
        flight.setFrom(newOrigin);
        assertEquals(newOrigin, flight.getFrom());

        Airport newDestination = mock(Airport.class);
        flight.setTo(newDestination);
        assertEquals(newDestination, flight.getTo());

        Airline newAirline = mock(Airline.class);
        flight.setAirline(newAirline);
        assertEquals(newAirline, flight.getAirline());

        Plane newPlane = mock(Plane.class);
        flight.setPlane(newPlane);
        assertEquals(newPlane, flight.getPlane());

        flight.setDuration(90);
        assertEquals(90, flight.getDuration());


        flight.setPrice(150.5f);
        assertEquals(150.5f, flight.getPrice(), 0.001);
    }
}