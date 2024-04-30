package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
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
}