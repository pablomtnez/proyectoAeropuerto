package es.deusto.spq.server.services;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.spq.client.domain.AirAlliance;
import es.deusto.spq.server.jdo.Airport;
import es.deusto.spq.server.jdo.Flight;
import es.deusto.spq.server.jdo.Reservation;
import es.deusto.spq.client.domain.Country;

public class AirAllianceServiceTest {

    private AirAllianceServiceTest airAllianceService;

    @Mock
    private Flight mockFlight;

    @Mock
    private Reservation mockReservation;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
       // airAllianceService = new AirAllianceServiceTest(AirAlliance.OW);

        // Mock data for airports and flights
        Airport airport1 = new Airport("JFK", "John F. Kennedy International Airport", "New York", Country.US);
        Airport airport2 = new Airport("LAX", "Los Angeles International Airport", "Los Angeles", Country.US);

        // Create and add mock flights
        Flight mockFlight1 = new Flight("AA100", airport1, airport2, null, null, 6, 300.0f);
        Flight mockFlight2 = new Flight("AA101", airport1, airport2, null, null, 5, 350.0f);
/*
        airAllianceService.flights.put(mockFlight1.getCode(), mockFlight1);
        airAllianceService.flights.put(mockFlight2.getCode(), mockFlight2);
        airAllianceService.airports.put(airport1.getIataCode(), airport1);
        airAllianceService.airports.put(airport2.getIataCode(), airport2);
    }*/

 


    

}
}
