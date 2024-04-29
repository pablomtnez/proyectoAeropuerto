package es.deusto.spq.client.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class UsuarioTest {

    private Usuario usuario;

    @Before
    public void setUp() {
        usuario = new Usuario("John", "Doe", "john@example.com", "password");
    }

    @Test
    public void testGettersAndSetters() {
        // Verify initial state
        assertEquals("John", usuario.getNombre());
        assertEquals("Doe", usuario.getApellido());
        assertEquals("john@example.com", usuario.getEmail());
        assertEquals("password", usuario.getPassword());

        // Set new values
        usuario.setNombre("Jane");
        usuario.setApellido("Smith");
        usuario.setEmail("jane@example.com");
        usuario.setPassword("newPassword");

        // Verify updated values
        assertEquals("Jane", usuario.getNombre());
        assertEquals("Smith", usuario.getApellido());
        assertEquals("jane@example.com", usuario.getEmail());
        assertEquals("newPassword", usuario.getPassword());
    }

    @Test
    public void testToString() {
        String expectedToString = "Usuario{nombre='John', apellido='Doe', email='john@example.com', password='password'}";
        assertEquals(expectedToString, usuario.toString());
    }

    @Test
    public void testJsonIgnoreProperties() {
        // Create a mock object
        Usuario mockUsuario = mock(Usuario.class);

        // Verify that ignoreUnknown = true annotation is used
        assertEquals(true, mockUsuario.getClass().isAnnotationPresent(com.fasterxml.jackson.annotation.JsonIgnoreProperties.class));
    }
}