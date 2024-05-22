package es.deusto.spq.client.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.server.jdo.Airline;
import es.deusto.spq.server.jdo.Airport;

public class FlightRendererTest {

    private FlightRenderer renderer;
    private JTable table;

    @Before
    public void setUp() {
        //renderer = new FlightRenderer();
        table = mock(JTable.class);
        when(table.getBackground()).thenReturn(java.awt.Color.WHITE);
        when(table.getSelectionBackground()).thenReturn(java.awt.Color.BLUE);
        when(table.getSelectionForeground()).thenReturn(java.awt.Color.WHITE);
    }

    @Test
    public void testAirlineCell() {
        Airline airline = mock(Airline.class);
        when(airline.getName()).thenReturn("Mock Airline");

        Component comp = renderer.getTableCellRendererComponent(table, airline, false, false, 0, 0);

        assertTrue(comp instanceof JLabel);
        JLabel label = (JLabel) comp;
        assertEquals("Mock Airline", label.getToolTipText());
    }

    @Test
    public void testFlightNumberCell() {
        Component comp = renderer.getTableCellRendererComponent(table, "FL123", false, false, 0, 1);

        assertTrue(comp instanceof JLabel);
        JLabel label = (JLabel) comp;
        assertEquals("FL123", label.getText());
    }

    @Test
    public void testAirportCell() {
        Airport airport = mock(Airport.class);
        when(airport.getIataCode()).thenReturn("JFK");
        when(airport.getName()).thenReturn("John F. Kennedy International Airport");

        Component comp = renderer.getTableCellRendererComponent(table, airport, false, false, 0, 2);

        assertTrue(comp instanceof JLabel);
        JLabel label = (JLabel) comp;
        assertEquals("JFK", label.getText());
        assertEquals("John F. Kennedy International Airport", label.getToolTipText());
    }

    @Test
    public void testDurationCell() {
        Component comp = renderer.getTableCellRendererComponent(table, 180, false, false, 0, 4);

        assertTrue(comp instanceof JLabel);
        JLabel label = (JLabel) comp;
        assertEquals("180 m.", label.getText());
        assertEquals(JLabel.RIGHT, label.getHorizontalAlignment());
    }

    @Test
    public void testPriceCell() {
        Component comp = renderer.getTableCellRendererComponent(table, 199.99, false, false, 0, 5);

        assertTrue(comp instanceof JLabel);
        JLabel label = (JLabel) comp;
        assertEquals("199,99 €", label.getText());
        assertEquals(JLabel.RIGHT, label.getHorizontalAlignment());
    }

    @Test
    public void testAvailabilityCell() {
        Component comp = renderer.getTableCellRendererComponent(table, 0.75f, false, false, 0, 8);

        assertTrue(comp instanceof JProgressBar);
        JProgressBar bar = (JProgressBar) comp;
        assertEquals(75, bar.getValue());
        assertEquals("75,0 %", bar.getString());
    }

    @Test
    public void testReserveButtonCell() {
        Component comp = renderer.getTableCellRendererComponent(table, "Reservar", false, false, 0, 9);

        assertTrue(comp instanceof JButton);
        JButton button = (JButton) comp;
        assertEquals("Reservar", button.getText());
    }
}
