package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AirlineTest {

    Airline airline;

    @Before
    public void setUp() {
        Country countryMock = mock(Country.class);
        AirAlliance allianceMock = mock(AirAlliance.class);
        List<Flight> flightsMock = new ArrayList<>();
        airline = new Airline("code", "name", countryMock, allianceMock, flightsMock);
    }

    @Test
    public void testGetters() {
        assertEquals("code", airline.getCode());
        assertEquals("name", airline.getName());
        assertEquals(countryMock, airline.getPais());
        assertEquals(allianceMock, airline.getAlliance());
        assertEquals(flightsMock, airline.getVuelos());
    }

    @Test
    public void testSetters() {
        airline.setCode("newCode");
        assertEquals("newCode", airline.getCode());
        
        airline.setName("newName");
        assertEquals("newName", airline.getName());
        
        Country newCountry = mock(Country.class);
        airline.setPais(newCountry);
        assertEquals(newCountry, airline.getPais());
        
        AirAlliance newAlliance = mock(AirAlliance.class);
        airline.setAlliance(newAlliance);
        assertEquals(newAlliance, airline.getAlliance());
        
        List<Flight> newFlights = new ArrayList<>();
        airline.setVuelo(newFlights);
        assertEquals(newFlights, airline.getVuelos());
    }

    @Test
    public void testToString() {
        String expectedToString = "code: name [null] (null)";
        assertEquals(expectedToString, airline.toString());
    }
}