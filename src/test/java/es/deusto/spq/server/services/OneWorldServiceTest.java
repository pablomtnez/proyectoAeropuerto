package es.deusto.spq.server.services;

import es.deusto.spq.server.dao.AirlineDAO;
import es.deusto.spq.server.dao.AirportDAO;
import es.deusto.spq.server.dao.FlightDAO;
import es.deusto.spq.server.dao.PlaneDAO;
import es.deusto.spq.server.dao.ReservationDAO;
import es.deusto.spq.server.jdo.Reservation;
import es.deusto.spq.server.jdo.Flight;
import es.deusto.spq.server.jdo.Airline;
import es.deusto.spq.server.jdo.Airport;
import es.deusto.spq.server.jdo.Plane;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneWorldServiceTest {

    @InjectMocks
    private OneWorldService oneWorldService;

    @Mock
    private AirlineDAO airlineDAOMock;
    @Mock
    private AirportDAO airportDAOMock;
    @Mock
    private FlightDAO flightDAOMock;
    @Mock
    private PlaneDAO planeDAOMock;
    @Mock
    private ReservationDAO reservationDAOMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        oneWorldService = new OneWorldService();
    }

    @Test
    public void testConstructor() {
        assertNotNull(oneWorldService);
    }

    @Test
    public void testLoadAllData() {
        // Implementa el test con la lógica específica requerida
    }

    //@Test
   /* public void testParseCSVReservation() throws Exception {
        String input = "R123,FL123,1234567890123,John Doe;Jane Doe";
        Reservation reservation = OneWorldService.parseCSVReservation(input);

        assertEquals("R123", reservation.getLocator());
        assertNotNull(reservation.getFlight());
        assertEquals(1234567890123L, reservation.getDate());
        assertFalse(reservation.getPassengers().isEmpty());
        assertEquals("John Doe", reservation.getPassengers().get(0));
    }
*/
    @Test
    public void testParseCSVPlane() throws Exception {
        String input = "PL123,Boeing 747,350";
        Plane plane = OneWorldService.parseCSVPlane(input);

        assertEquals("PL123", plane.getIataCode());
        //assertEquals("Boeing 747", plane.getModel());
        //assertEquals(350, plane.getCapacity());
    }

    @Test
    public void testParseCSVAirport() throws Exception {
        String input = "AP123,Madrid,Spain,ES";
        Airport airport = OneWorldService.parseCSVAirport(input);

        assertEquals("AP123", airport.getIataCode());
        //assertEquals("Madrid", airport.getCity());
    }

    @Test
    public void testParseCSVAirline() throws Exception {
        String input = "AL123,Iberia,Spain,STAR_ALLIANCE";
        //Airline airline = OneWorldService.parseCSVAirline(input);

        //assertEquals("AL123", airline.getIataCode());
        //assertEquals("Iberia", airline.getName());
    }
}
