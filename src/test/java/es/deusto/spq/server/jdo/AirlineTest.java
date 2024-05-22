package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.client.domain.AirAlliance;
import es.deusto.spq.client.domain.Country;

public class AirlineTest {

    Airline airline;
    Country countryMock;
    AirAlliance allianceMock;
    List<Flight> flightsMock;

    @Before
    public void setUp() {
        countryMock = mock(Country.class);
        allianceMock = mock(AirAlliance.class);
        flightsMock = new ArrayList<>();
        airline = new Airline("code", "name", countryMock, allianceMock);
    }

    @Test
    public void testGetters() {
        assertEquals("code", airline.getIataCode());
        assertEquals("name", airline.getName());
        assertEquals(countryMock, airline.getCountry());
        assertEquals(allianceMock, airline.getAlliance());
    }

    @Test
    public void testSetters() {
        airline.setIataCode("newCode");
        assertEquals("newCode", airline.getIataCode());
        
        airline.setName("newName");
        assertEquals("newName", airline.getName());
        
        Country newCountry = mock(Country.class);
        airline.setCountry(newCountry);
        assertEquals(newCountry, airline.getCountry());
        
        AirAlliance newAlliance = mock(AirAlliance.class);
        airline.setAlliance(newAlliance);
        assertEquals(newAlliance, airline.getAlliance());
    }

    @Test
    public void testToString() {
    String expectedToString = String.format("%s: %s [%s] (%s)", "code", "name", allianceMock, countryMock);
    assertEquals(expectedToString, airline.toString());
    }

    @Test
    public void testParseCSV_ValidInput() {
        String csvData = "AA,American Airlines,US,STAR_ALLIANCE";
        try {
            Airline airline = Airline.parseCSV(csvData);
            assertNotNull(airline);
            assertEquals("AA", airline.getIataCode());
            assertEquals("American Airlines", airline.getName());
            assertEquals(Country.US, airline.getCountry());
            assertEquals(AirAlliance.STAR_ALLIANCE, airline.getAlliance());
        } catch (Exception e) {
            fail("Exception should not be thrown for valid input");
        }
    }

    @Test
    public void testParseCSV_InvalidInput() {
        String csvData = "AA,American Airlines,UNKNOWN_COUNTRY,ONEWORLD";
        Exception exception = assertThrows(Exception.class, () -> {
            Airline.parseCSV(csvData);
        });
        String expectedMessage = "class es.deusto.spq.server.jdo.Airline from CSV error: " + csvData;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    
}