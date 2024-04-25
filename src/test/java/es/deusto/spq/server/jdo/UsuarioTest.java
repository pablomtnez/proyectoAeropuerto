package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UsuarioTest {

    Usuario usuario;

    @Before
    public void setUp() {
        usuario = new Usuario("John", "Doe", "john@example.com", "password123");
    }

    @Test
    public void testGetters() {
        assertEquals("John", usuario.getNombre());
        assertEquals("Doe", usuario.getApellido());
        assertEquals("john@example.com", usuario.getEmail());
        assertEquals("password123", usuario.getPassword());
    }

    @Test
    public void testSetters() {
        usuario.setNombre("Jane");
        assertEquals("Jane", usuario.getNombre());

        usuario.setApellido("Smith");
        assertEquals("Smith", usuario.getApellido());

        usuario.setEmail("jane@example.com");
        assertEquals("jane@example.com", usuario.getEmail());

        usuario.setPassword("newpassword");
        assertEquals("newpassword", usuario.getPassword());
    }
}