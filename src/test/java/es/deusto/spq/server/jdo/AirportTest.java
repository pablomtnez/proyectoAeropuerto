package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.client.domain.Country;

public class AirportTest {

    Airport airport;
    Country countryMock;

    @Before
    public void setUp() {
        countryMock = mock(Country.class);
        airport = new Airport("code", "name", "city", countryMock);
    }

    @Test
    public void testGetters() {
        assertEquals("code", airport.getIataCode());
        assertEquals("name", airport.getName());
        assertEquals("city", airport.getCity());
        assertEquals(countryMock, airport.getCountry());
    }

    @Test
    public void testSetters() {
        airport.setIataCode("newCode");
        assertEquals("newCode", airport.getIataCode());

        airport.setName("newName");
        assertEquals("newName", airport.getName());
        
        airport.setCity("newCity");
        assertEquals("newCity", airport.getCity());

        Country newCountry = mock(Country.class);
        airport.setCountry(newCountry);
        assertEquals(newCountry, airport.getCountry());
    }
}