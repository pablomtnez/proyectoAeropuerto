package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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

    @Test
    public void testParseCSV_ValidInput() {
        String csvData = "JFK;John F Kennedy International;New York;US";
        try {
            Airport airport = Airport.parseCSV(csvData);
            assertNotNull(airport);
            assertEquals("JFK", airport.getIataCode());
            assertEquals("John F Kennedy International", airport.getName());
            assertEquals("New York", airport.getCity());
            assertEquals(Country.US, airport.getCountry());
        } catch (Exception e) {
            fail("Exception should not be thrown for valid input");
        }
    }

    @Test
    public void testParseCSV_InvalidInput() {
        String csvData = "JFK;John F Kennedy International;New York;UNKNOWN_COUNTRY";
        Exception exception = assertThrows(Exception.class, () -> {
            Airport.parseCSV(csvData);
        });
        String expectedMessage = "class es.deusto.spq.server.jdo.Airport from CSV error: " + csvData;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}