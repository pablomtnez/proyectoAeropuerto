package es.deusto.spq.client.gui;

import static org.mockito.Mockito.*;

import es.deusto.spq.client.ResourceClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.*;

public class VentanaLoginTest{

    private VentanaLogin ventanaLogin;
    private ResourceClient resourceClient;

    @Before
    public void setUp() {
        // Mock the ResourceClient
        resourceClient = Mockito.mock(ResourceClient.class);
        
        // Initialize the VentanaLogin with the mocked ResourceClient
        ventanaLogin = new VentanaLogin(resourceClient);
    }

    @Test
    public void testLoginSuccess() {
        // Set up the mock to return true for a successful login
        when(resourceClient.login("user@example.com", "password")).thenReturn(true);

        // Set the text fields to the test values
        ventanaLogin.getTextFieldUsuario().setText("user@example.com");
        ventanaLogin.getTextFieldPassword().setText("password");

        // Simulate the login button click
        ventanaLogin.getBotonLogin().doClick();

        // Verify that the login method was called with the correct parameters
        verify(resourceClient).login("user@example.com", "password");

        // Optionally, verify the GUI state changes, such as the window being disposed
        // This might require additional setup or a custom WindowListener in the actual class
    }

    @Test
    public void testLoginFailure() {
        // Set up the mock to return false for a failed login
        when(resourceClient.login("user@example.com", "wrongpassword")).thenReturn(false);

        // Set the text fields to the test values
        ventanaLogin.getTextFieldUsuario().setText("user@example.com");
        ventanaLogin.getTextFieldPassword().setText("wrongpassword");

        // Mock JOptionPane.showMessageDialog
        try (MockedStatic<JOptionPane> mockOptionPane = Mockito.mockStatic(JOptionPane.class)) {
            mockOptionPane.when(() -> JOptionPane.showMessageDialog(any(), anyString(), anyString(), eq(JOptionPane.ERROR_MESSAGE))).thenAnswer(invocation -> null);

            // Simulate the login button click
            ventanaLogin.getBotonLogin().doClick();

            // Verify that the login method was called with the correct parameters
            verify(resourceClient).login("user@example.com", "wrongpassword");

            // Verify that a JOptionPane was shown with the error message
            mockOptionPane.verify(() -> JOptionPane.showMessageDialog(any(), eq("Error al iniciar sesión."), eq("Error"), eq(JOptionPane.ERROR_MESSAGE)));
        }
    }
}