package es.deusto.spq.client.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AirlineTest {

    private Airline airline;
    private Country countryMock;
    private AirAlliance allianceMock;
    private Flight flight1Mock;
    private Flight flight2Mock;

    @Before
    public void setUp() {
        // Create mock objects for dependencies
        countryMock = mock(Country.class);
        allianceMock = mock(AirAlliance.class);

        // Create instance of Airline with mock dependencies
        airline = new Airline("AA", "American Airlines", countryMock, allianceMock);

        // Create mock flights
        flight1Mock = mock(Flight.class);
        when(flight1Mock.getCode()).thenReturn("FL1");
        flight2Mock = mock(Flight.class);
        when(flight2Mock.getCode()).thenReturn("FL2");
    }

    @Test
    public void testGetters() {
        assertEquals("AA", airline.getCode());
        assertEquals("American Airlines", airline.getName());
        assertEquals(countryMock, airline.getCountry());
        assertEquals(allianceMock, airline.getAlliance());
        assertTrue(airline.getFlights().isEmpty());
    }

    @Test
    public void testAddFlight() {
        airline.addFlight(flight1Mock);
        assertTrue(airline.getFlights().contains(flight1Mock));
        assertFalse(airline.getFlights().contains(flight2Mock));

        // Adding the same flight again shouldn't change anything
        airline.addFlight(flight1Mock);
        assertTrue(airline.getFlights().contains(flight1Mock));

        // Adding a different flight should add it to the list
        airline.addFlight(flight2Mock);
        assertTrue(airline.getFlights().contains(flight2Mock));
    }

    @Test
    public void testEquals() {
        Airline sameAirline = new Airline("AA", "American Airlines", countryMock, allianceMock);
        Airline differentAirline = new Airline("BA", "British Airways", countryMock, allianceMock);
        assertTrue(airline.equals(sameAirline));
        assertFalse(airline.equals(differentAirline));
    }

    @Test
    public void testHashCode() {
        Airline sameAirline = new Airline("AA", "American Airlines", countryMock, allianceMock);
        assertEquals(airline.hashCode(), sameAirline.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "AA: American Airlines [Mock for AirAlliance, hashCode: " + allianceMock.hashCode() + "] (Mock for Country, hashCode: " + countryMock.hashCode() + ")";
        assertEquals(expectedToString, airline.toString());
    }
}