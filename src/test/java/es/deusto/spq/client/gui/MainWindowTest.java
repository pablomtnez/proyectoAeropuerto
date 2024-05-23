package es.deusto.spq.client.gui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Collections;

import org.junit.Test;

import es.deusto.spq.client.ResourceClient;
import es.deusto.spq.client.gui.MainWindow;

public class MainWindowTest {

    @Test
    public void testUpdateFlights() {
        // Creamos un mock de ResourceClient para evitar depender de su implementación real
        ResourceClient resourceClientMock = mock(ResourceClient.class);

        // Creamos una instancia de MainWindow con el mock de ResourceClient
        MainWindow mainWindow = new MainWindow(resourceClientMock);

        // Forzamos la actualización de vuelos
        mainWindow.updateFlights();

        // Esperamos que la tabla de vuelos esté vacía después de la actualización
        assertEquals(0, mainWindow.getJTableFlights().getRowCount());
    }
}
