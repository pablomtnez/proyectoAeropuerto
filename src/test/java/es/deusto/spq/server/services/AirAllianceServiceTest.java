package es.deusto.spq.server.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import es.deusto.spq.server.jdo.Reservation;
import es.deusto.spq.server.jdo.Flight;
import es.deusto.spq.server.jdo.Airline;
import es.deusto.spq.server.jdo.Airport;
import es.deusto.spq.server.jdo.Plane;

import es.deusto.spq.server.dao.AirlineDAO;
import es.deusto.spq.server.dao.AirportDAO;
import es.deusto.spq.server.dao.FlightDAO;
import es.deusto.spq.server.dao.PlaneDAO;
import es.deusto.spq.server.dao.ReservationDAO;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import java.util.Random;

public class AirAllianceServiceTest {

    private Random randomMock;

    @Before
    public void setUp() {
        randomMock = mock(Random.class);
       // AirAllianceService.setRandom(randomMock);
    }

    @Test
    public void testGenerateLocator() {
        // Assuming the LOCATOR_ALPHABET string is "234679CDFGHJKMNPRTWXYZ" and the length is 6
        when(randomMock.nextInt(anyInt())).thenReturn(1, 2, 3, 4, 5, 6);  // Selecting indices sequentially for simplicity

      //  String locator = AirAllianceService.generateLocator();
        //assertNotNull(locator);
       // assertEquals(6, locator.length());  // The locator should have a length of 6 characters
        // This assert just checks length, you might want to check for valid characters or specific values
    }
}
