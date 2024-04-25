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
        assertEquals("code", airport.getCode());
        assertEquals("name", airport.getName());
        assertEquals("city", airport.getCity());
        assertEquals(countryMock, airport.getPais());
    }

    @Test
    public void testSetters() {
        airport.setCode("newCode");
        assertEquals("newCode", airport.getCode());

        airport.setName("newName");
        assertEquals("newName", airport.getName());
        
        airport.setCity("newCity");
        assertEquals("newCity", airport.getCity());

        Country newCountry = mock(Country.class);
        airport.setPais(newCountry);
        assertEquals(newCountry, airport.getPais());
    }
}