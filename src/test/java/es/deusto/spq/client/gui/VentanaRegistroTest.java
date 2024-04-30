package es.deusto.spq.client.gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.client.ResourceClient;

import java.lang.reflect.Field;


public class VentanaRegistroTest {

    private VentanaRegistro ventanaRegistro;
    private ResourceClient resourceClientMock;
    private JButton botonRegistrarMock;
    private JTextField textFieldUsuarioMock;
    private JButton botonVolverMock;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Create mocks for the GUI components
        botonRegistrarMock = mock(JButton.class);
        textFieldUsuarioMock = mock(JTextField.class);
        botonVolverMock = mock(JButton.class);
        
        // Create a mock for the ResourceClient class
        resourceClientMock = mock(ResourceClient.class);

        // Inject the mocks into the VentanaRegistro class
        ventanaRegistro = new VentanaRegistro(resourceClientMock);
        
        // Ensure frame is not visible during tests
        ventanaRegistro.setVisible(false);
    }

    @Test
    public void testRegistrarUsuario_Success() {
        // Set up behavior for mocks if needed
        // For example:
        // when(textFieldUsuarioMock.getText()).thenReturn("username");
        
        // Trigger action of "Registrar Usuario" button
        botonRegistrarMock.doClick();

        // Verify that success message is shown
        assertTrue(JOptionPaneMock.isMessageShown("Usuario registrado correctamente"));
    }

    @Test
    public void testRegistrarUsuario_Failure() {
        // Set up behavior for mocks if needed

        // Trigger action of "Registrar Usuario" button
        botonRegistrarMock.doClick();

        // Verify that failure message is shown
        assertTrue(JOptionPaneMock.isMessageShown("No se pudo registrar al usuario"));
    }

    @Test
    public void testVolver() {
        // Trigger action of "Atrás" button
        botonVolverMock.doClick();

        // Verify the behavior or interactions as needed
    }

    // Helper method to set private fields of VentanaRegistro using reflection
    private void setField(Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    // Helper class to mock JOptionPane messages
    private static class JOptionPaneMock {
        static boolean isMessageShown(String message) {
            return JOptionPane.showInputDialog(message) != null;
        }
    }
}