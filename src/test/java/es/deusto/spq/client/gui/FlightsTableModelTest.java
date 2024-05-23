package es.deusto.spq.client.gui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.client.domain.AirAlliance;
import es.deusto.spq.client.domain.Airline;
import es.deusto.spq.client.domain.Airport;
import es.deusto.spq.client.domain.Country;
import es.deusto.spq.client.domain.Flight;
import es.deusto.spq.client.domain.Plane;

public class FlightsTableModelTest {

    private FlightsTableModel model;
    private List<Flight> flights;

    @Before
    public void setUp() {
        // Mocking flights
        flights = new ArrayList<>();
        Country country1 = Country.ES;
        AirAlliance alliance1 = AirAlliance.STAR_ALLIANCE;
        Airline airline1 = new Airline("Airline1", "Airline 1", country1, alliance1);
        Plane plane1 = new Plane("Plane 1", "Plane1", 50);
        Airport origin1 = new Airport("Origin1", "Airport 1", "City 1", country1);
        Airport destination1 = new Airport("Destination1", "Airport 2", "City 2", country1);
        flights.add(new Flight("ABC123", origin1, destination1, airline1, plane1, 120, 100.0f));
        
        Country country2 = Country.US;
        AirAlliance alliance2 = AirAlliance.ONE_WORLD;
        Airline airline2 = new Airline("Airline2", "Airline 2", country2, alliance2);
        Plane plane2 = new Plane("Plane 2", "Plane2", 30);
        Airport origin2 = new Airport("Origin2", "Airport 3", "City 3", country2);
        Airport destination2 = new Airport("Destination2", "Airport 4", "City 4", country2);
        flights.add(new Flight("DEF456", origin2, destination2, airline2, plane2, 180, 150.0f));

        // Creating the model
        model = new FlightsTableModel(flights);
    }

    @Test
    public void testGetRowCount() {
        assertEquals(2, model.getRowCount());
    }

    @Test
    public void testGetColumnCount() {
        assertEquals(10, model.getColumnCount());
    }

    @Test
    public void testGetColumnName() {
        assertEquals("AEROLÍNEA", model.getColumnName(0));
        assertEquals("VUELO", model.getColumnName(1));
        assertEquals("ORIGEN", model.getColumnName(2));
        assertEquals("DESTINO", model.getColumnName(3));
        assertEquals("DURACIÓN", model.getColumnName(4));
        assertEquals("PRECIO", model.getColumnName(5));
        assertEquals("RESERVAS", model.getColumnName(6));
        assertEquals("ASIENTOS LIBRES", model.getColumnName(7));
        assertEquals("DISPONIBILIDAD", model.getColumnName(8));
        assertEquals("RESERVAR", model.getColumnName(9));
    }

    @Test
    public void testGetValueAt() {
        assertEquals("Airline 1", ((Airline)model.getValueAt(0, 0)).getName());
        assertEquals("ABC123", model.getValueAt(0, 1));
        assertEquals("Airport 1, City 1", ((Airport)model.getValueAt(0, 2)).getName() + ", " + ((Airport)model.getValueAt(0, 2)).getCity());
        assertEquals("Airport 2, City 2", ((Airport)model.getValueAt(0, 3)).getName() + ", " + ((Airport)model.getValueAt(0, 3)).getCity());
        assertEquals(120, model.getValueAt(0, 4));
        assertEquals(100.0f, model.getValueAt(0, 5));
        assertEquals(0, model.getValueAt(0, 6)); // No reservations initially
        assertEquals(50, model.getValueAt(0, 7));
        assertEquals(1.0f, model.getValueAt(0, 8));
        assertEquals(flights.get(0), model.getValueAt(0, 9));
    }
    

    @Test
    public void testIsCellEditable() {
        // The "RESERVAR" column should be editable
        assertEquals(true, model.isCellEditable(0, 9));
        // Other columns should not be editable
        for (int i = 0; i < 9; i++) {
            assertEquals(false, model.isCellEditable(0, i));
        }
    }
}

