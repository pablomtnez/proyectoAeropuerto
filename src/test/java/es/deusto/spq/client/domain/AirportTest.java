package es.deusto.spq.client.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class AirportTest {

    private Airport airport;
    private Country countryMock;

    @Before
    public void setUp() {
        // Create mock object for Country
        countryMock = mock(Country.class);
        when(countryMock.getName()).thenReturn("Spain");

        // Create instance of Airport with mock object
        airport = new Airport("JFK", "John F. Kennedy International Airport", "New York", countryMock);
    }

    @Test
    public void testGetters() {
        assertEquals("JFK", airport.getIataCode());
        assertEquals("John F. Kennedy International Airport", airport.getName());
        assertEquals("New York", airport.getCity());
        assertEquals(countryMock, airport.getCountry());
    }

    @Test
    public void testEquals() {
        Airport sameAirport = new Airport("JFK", "John F. Kennedy International Airport", "New York", countryMock);
        Airport differentAirport = new Airport("LAX", "Los Angeles International Airport", "Los Angeles", countryMock);

        assertTrue(airport.equals(sameAirport));
        assertFalse(airport.equals(differentAirport));

        // Test against null and different class
        assertFalse(airport.equals(null));
        assertFalse(airport.equals("string"));
    }

    @Test
    public void testHashCode() {
        Airport sameAirport = new Airport("JFK", "John F. Kennedy International Airport", "New York", countryMock);
        assertEquals(airport.hashCode(), sameAirport.hashCode());
    }

    @Test
    public void testToString() {
        when(countryMock.toString()).thenReturn("Spain");
        String expectedToString = "JFK: John F. Kennedy International Airport, New York (Spain)";
        assertEquals(expectedToString, airport.toString());
    }

    @Test
    public void testCompareTo() {
        Airport smallerAirport = new Airport("ATL", "Hartsfield-Jackson Atlanta International Airport", "Atlanta", countryMock);
        Airport largerAirport = new Airport("LAX", "Los Angeles International Airport", "Los Angeles", countryMock);

        assertTrue(airport.compareTo(smallerAirport) > 0);
        assertTrue(airport.compareTo(largerAirport) < 0);
        assertTrue(airport.compareTo(airport) == 0);
    }
}
