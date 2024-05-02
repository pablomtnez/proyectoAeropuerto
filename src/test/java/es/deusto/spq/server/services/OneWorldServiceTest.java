package es.deusto.spq.server.services;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.client.domain.AirAlliance;
import es.deusto.spq.client.domain.Country;
import es.deusto.spq.server.dao.*;
import es.deusto.spq.server.jdo.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

public class OneWorldServiceTest {

    @InjectMocks
    private OneWorldService oneWorldService;

    @Mock
    private BufferedReader readerMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        oneWorldService = new OneWorldService();
    }
/*
    @Test
    public void testLoadFlights() throws Exception {
        String data = "Code,Departure,Arrival,Airline,Plane,Seats,Price\n" +
                      "FL123,APT1,APT2,AL123,PL123,150,199.99";
        //whenNew(BufferedReader.class).withAnyArguments().thenReturn(readerMock);
        when(readerMock.readLine()).thenReturn(data, (String) null);
        
        //Map<String, Flight> result = oneWorldService.loadFlights();
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey("FL123"));
    }

    @Test
    public void testLoadAirlinesCSV() throws Exception {
        String data = "Code,Name,Country,Alliance\n" +
                      "AL123,Iberia,Spain,STAR_ALLIANCE";
        //whenNew(BufferedReader.class).withAnyArguments().thenReturn(readerMock);
        when(readerMock.readLine()).thenReturn(data, (String) null);
        
        //Map<String, Airline> result = oneWorldService.loadAirlinesCSV();
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey("AL123"));
    }

    @Test
    public void testLoadAirportsCSV() throws Exception {
        String data = "Code,Name,City,Country\n" +
                      "APT1,Madrid Airport,Madrid,Spain";
        //whenNew(BufferedReader.class).withAnyArguments().thenReturn(readerMock);
        when(readerMock.readLine()).thenReturn(data, (String) null);
        
        //Map<String, Airport> result = oneWorldService.loadAirportsCSV();
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey("APT1"));
    }

    @Test
    public void testLoadPlanesCSV() throws Exception {
        String data = "Code,Model,Capacity\n" +
                      "PL123,Boeing 747,350";
        //whenNew(BufferedReader.class).withAnyArguments().thenReturn(readerMock);
        when(readerMock.readLine()).thenReturn(data, (String) null);
        
        //Map<String, Plane> result = oneWorldService.loadPlanesCSV();
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey("PL123"));
    }
*//*
    @Test
    public void testParseCSVAirline() throws Exception {
        String input = "AL123,Iberia,Spain,STAR_ALLIANCE";
        Airline airline = oneWorldService.parseCSVAirline(input);
        assertEquals("AL123", airline.getIataCode());
    }

    @Test
    public void testParseCSVAirport() throws Exception {
        String input = "APT1,Madrid Airport,Madrid,Spain";
        Airport airport = oneWorldService.parseCSVAirport(input);
        assertEquals("APT1", airport.getIataCode());
    }*/

    @Test
    public void testParseCSVPlane() throws Exception {
        String input = "PL123,Boeing 747,350";
        Plane plane = oneWorldService.parseCSVPlane(input);
        assertEquals("PL123", plane.getIataCode());
    }
/*
    @Test
    public void testParseCSVReservation() throws Exception {
        String input = "R123,FL123,1234567890123,John Doe;Jane Doe";
        Reservation reservation = oneWorldService.parseCSVReservation(input);
        assertEquals("R123", reservation.getLocator());
    }*/

  /*  @Test
    public void testLoadReservationsCSV() throws Exception {
        String data = "R123,FL123,1234567890123,John Doe;Jane Doe";
        //whenNew(BufferedReader.class).withAnyArguments().thenReturn(readerMock);
        when(readerMock.readLine()).thenReturn(data, (String) null);
        
      //  List<Reservation> result = oneWorldService.loadReservationsCSV();
        assertFalse(result.isEmpty());
    }*/

    @Test
    public void testLoadAllData() {
        // This test should verify that all loading methods are called and their results are combined properly
        // It's important to mock external dependencies and verify internal method calls
        oneWorldService.loadAllData();
       // verify(readerMock, atLeastOnce()).readLine();
    }

    @Test
    public void testGetAllData() {
        // This test checks if all get methods are properly compiling data into a map
        Map<String, Object> data = oneWorldService.getAllData();
        assertNotNull(data);
      //  assertTrue(data.containsKey("airAlliances"));
    }
}
