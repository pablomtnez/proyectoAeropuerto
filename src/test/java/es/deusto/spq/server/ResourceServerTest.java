package es.deusto.spq.server;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.server.ResourceServer;
import es.deusto.spq.server.dao.UsuarioDAO;
import es.deusto.spq.server.jdo.Usuario;
import es.deusto.spq.server.services.OneWorldService;

public class ResourceServerTest {

    private ResourceServer resourceServer;
    private OneWorldService oneWorldServiceMock;
    private UsuarioDAO usuarioDAOMock;

    @Before
    public void setUp() {
        oneWorldServiceMock = mock(OneWorldService.class);
        usuarioDAOMock = mock(UsuarioDAO.class);
        resourceServer = new ResourceServer(oneWorldServiceMock, usuarioDAOMock);
    }

    @Test
    public void testLoadData_Success() {
        doNothing().when(oneWorldServiceMock).loadAllData();

        Response response = resourceServer.loadData();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testLoadData_Failure() {
        doThrow(new RuntimeException()).when(oneWorldServiceMock).loadAllData();

        Response response = resourceServer.loadData();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testLogin_Success() {
        Usuario user = new Usuario("John", "Doe", "john.doe@example.com", "password");
        when(usuarioDAOMock.find(user.getEmail())).thenReturn(user);

        Response response = resourceServer.login(user);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testLogin_Failure() {
        Usuario user = new Usuario("John", "Doe", "john.doe@example.com", "wrongpassword");
        when(usuarioDAOMock.find(user.getEmail())).thenReturn(null);

        Response response = resourceServer.login(user);

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRegister_Success() {
        Usuario user = new Usuario("John", "Doe", "john.doe@example.com", "password");
        when(usuarioDAOMock.find(user.getEmail())).thenReturn(null);

        Response response = resourceServer.register(user);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRegister_UserExists() {
        Usuario user = new Usuario("John", "Doe", "john.doe@example.com", "password");
        when(usuarioDAOMock.find(user.getEmail())).thenReturn(user);

        Response response = resourceServer.register(user);

        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRegister_InvalidEmail() {
        Usuario user = new Usuario("John", "Doe", "invalid-email", "password");

        Response response = resourceServer.register(user);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
