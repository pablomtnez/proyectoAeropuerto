package es.deusto.spq.client.gui;

import es.deusto.spq.client.domain.Flight;
import es.deusto.spq.client.domain.Airport;
import es.deusto.spq.client.domain.Country;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class BookDialogTest {

    @Test
    public void testUpdatePassengers() {
        Airport origin = new Airport("ORI", "Origin", "City", Country.ES);
        Airport destination = new Airport("DES", "Destination", "City", Country.US);
        Flight flight = new Flight("ABC123", origin, destination, null, null, 120, 100.0f);

        SwingUtilities.invokeLater(() -> {
            BookDialog bookDialog = new BookDialog(flight);

            JComboBox<String> jComboPassengers = bookDialog.getJComboPassengers();

            // Agregar pasajeros
            jComboPassengers.addItem("1 - Nombre1");
            jComboPassengers.addItem("2 - Nombre2");
            jComboPassengers.addItem("3 - ¿?");

            // Modificar el primer pasajero
            jComboPassengers.setSelectedIndex(1);
            jComboPassengers.getActionListeners()[0].actionPerformed(null);

            assertEquals(3, bookDialog.getPassengers().size());
            assertEquals("Nombre1", bookDialog.getPassengers().get(0));

            // Modificar el segundo pasajero
            jComboPassengers.setSelectedIndex(2);
            jComboPassengers.getActionListeners()[0].actionPerformed(null);

            assertEquals("Nombre2", bookDialog.getPassengers().get(1));

            bookDialog.dispose();
        });
    }
}
