package es.deusto.spq.client.gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

public class VentanaLoginTest {

    private VentanaLogin ventanaLogin;
    private JTextField textFieldUsuario;
    private JTextField textFieldPassword;
    private JButton botonLogin;

    @Before
    public void setUp() {
        // Mock the JTextField and JButton objects
        textFieldUsuario = mock(JTextField.class);
        textFieldPassword = mock(JTextField.class);
        botonLogin = mock(JButton.class);
        
        // Create an instance of VentanaLogin with the mocked objects
        ventanaLogin = new VentanaLogin();
        ventanaLogin.setTextFieldUsuario(textFieldUsuario);
        ventanaLogin.setTextFieldPassword(textFieldPassword);
        ventanaLogin.setBotonLogin(botonLogin);
    }

    @Test
    public void testLoginButtonActionPerformed_Success() {
        // Set user email and password
        when(textFieldUsuario.getText()).thenReturn("test@example.com");
        when(textFieldPassword.getText()).thenReturn("password");

        // Simulate button click event
        //ventanaLogin.getBotonLogin().getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));

        // You can also verify other behaviors such as showing a message dialog
    }
}