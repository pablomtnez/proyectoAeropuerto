package es.deusto.spq.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.client.Invocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.client.domain.Usuario;
import es.deusto.spq.client.gui.VentanaPrincipal;

import java.lang.constant.ConstantDesc;;

public class ResourceClient {
    protected static final Logger logger = LogManager.getLogger();
    private Client client;
    private static WebTarget webTarget;

    public ResourceClient(String hostname, String port){
        client = ClientBuilder.newClient();
        webTarget = client.target(String.format("http://%s:%s/rest/resource", hostname, port));
    }

    public static boolean loginUser(String email, String password){
        WebTarget loginUserWebTarget = webTarget.path("login");
        Invocation.Builder invocationBuilder = loginUserWebTarget.request(MediaType.APPLICATION_JSON);
        Usuario user = new Usuario(null, null, email, password);
        Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));
        Usuario u = response.readEntity(Usuario.class);
        VentanaPrincipal.logged = u;
        System.out.println(u.getEmail());
        if(response.getStatus() != Status.OK.getStatusCode()){
            logger.error("Error connecting with the server. Code: {}", response.getStatus());
            System.out.println("Error connecting with the server");
            return false;
        }else{
            logger.info("User correctly logged in");
            System.out.println("User correctly logged in");
            return true;
        }
    }

    public static boolean registerUser(String nombre, String apellido, String email, String password){
        WebTarget registerUserWebTarget = webTarget.path("register");
        Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);
        Usuario user = new Usuario(nombre, apellido, email, password);
        Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));
        if(response.getStatus() != Status.OK.getStatusCode()){
            logger.error("Error connecting with the server. Code: {}", response.getStatus());
            System.out.println("Error connecting with the server");
            return false;
        }else{
            logger.info("User correctly registered");
            System.out.println("User correctly registered");
            return true;
        }
    }
    
}
