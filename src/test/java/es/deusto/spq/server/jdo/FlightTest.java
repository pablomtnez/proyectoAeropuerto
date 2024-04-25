package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FlightTest {

    Flight flight;

    @Before
    public void setUp() {
        Airport originMock = mock(Airport.class);
        Airport destinationMock = mock(Airport.class);
        Airline airlineMock = mock(Airline.class);
        Plane planeMock = mock(Plane.class);
        List<Reservation> reservationsMock = new ArrayList<>();
        flight = new Flight("code", originMock, destinationMock, airlineMock, planeMock, reservationsMock, 60, 150, 100.0f);
    }

    @Test
    public void testGetters() {
        assertEquals("code", flight.getCode());
        assertEquals(originMock, flight.getOrigen());
        assertEquals(destinationMock, flight.getDestino());
        assertEquals(airlineMock, flight.getAerolinea());
        assertEquals(planeMock, flight.getAvion());
        assertEquals(reservationsMock, flight.getReservas());
        assertEquals(60, flight.getDuracion());
        assertEquals(150, flight.getSeats());
        assertEquals(100.0f, flight.getPrecio(), 0.001);
    }

    @Test
    public void testSetters() {
        Airport newOrigin = mock(Airport.class);
        flight.setOrigen(newOrigin);
        assertEquals(newOrigin, flight.getOrigen());

        Airport newDestination = mock(Airport.class);
        flight.setDestino(newDestination);
        assertEquals(newDestination, flight.getDestino());

        Airline newAirline = mock(Airline.class);
        flight.setAerolinea(newAirline);
        assertEquals(newAirline, flight.getAerolinea());

        Plane newPlane = mock(Plane.class);
        flight.setAvion(newPlane);
        assertEquals(newPlane, flight.getAvion());

        List<Reservation> newReservations = new ArrayList<>();
        flight.setReservas(newReservations);
        assertEquals(newReservations, flight.getReservas());

        flight.setDuracion(90);
        assertEquals(90, flight.getDuracion());

        flight.setSeats(200);
        assertEquals(200, flight.getSeats());

        flight.setPrecio(150.5f);
        assertEquals(150.5f, flight.getPrecio(), 0.001);
    }
}