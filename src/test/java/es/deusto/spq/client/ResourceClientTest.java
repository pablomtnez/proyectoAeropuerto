package es.deusto.spq.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ResourceClientTest {

    @Mock
    private Client client;

    @Mock
    private WebTarget webTarget;

    @Mock
    private WebTarget loadDataTarget;

    @Mock
    private WebTarget loginUserWebTarget;

    @Mock
    private WebTarget registerUserWebTarget;

    @Mock
    private Invocation.Builder invocationBuilder;

    @Mock
    private Response response;

    @InjectMocks
    private ResourceClient resourceClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(client.target(anyString())).thenReturn(webTarget);
    }

    @Test
    public void testLoadData_Success() {
        when(webTarget.path("loadData")).thenReturn(loadDataTarget);
        when(loadDataTarget.request()).thenReturn(invocationBuilder);
        when(invocationBuilder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());

        //assert(resourceClient.loadData());
    }

    @Test
    public void testLoadData_Failure() {
        when(webTarget.path("loadData")).thenReturn(loadDataTarget);
        when(loadDataTarget.request()).thenReturn(invocationBuilder);
        when(invocationBuilder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        assert(!resourceClient.loadData());
    }

    @Test
    public void testLogin_Success() {
        when(webTarget.path("login")).thenReturn(loginUserWebTarget);
        when(loginUserWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        when(invocationBuilder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());

        //assert(resourceClient.login("test@example.com", "password"));
    }

    @Test
    public void testLogin_Failure() {
        when(webTarget.path("login")).thenReturn(loginUserWebTarget);
        when(loginUserWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        when(invocationBuilder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.UNAUTHORIZED.getStatusCode());

        assert(!resourceClient.login("test@example.com", "password"));
    }

    @Test
    public void testRegister_Success() {
        when(webTarget.path("register")).thenReturn(registerUserWebTarget);
        when(registerUserWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        when(invocationBuilder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());

        //assert(resourceClient.register("John", "Doe", "john@example.com", "password"));
    }

    @Test
    public void testRegister_Failure() {
        when(webTarget.path("register")).thenReturn(registerUserWebTarget);
        when(registerUserWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        when(invocationBuilder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        //assert(!resourceClient.register("John", "Doe", "john@example.com", "password"));
    }
}