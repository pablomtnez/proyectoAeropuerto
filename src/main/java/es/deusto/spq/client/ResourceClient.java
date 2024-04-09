package es.deusto.spq.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.client.domain.Usuario;
import es.deusto.spq.client.gui.VentanaPrincipal;

public class ResourceClient {
    protected static final Logger logger = LogManager.getLogger();
    private Client client;
    private static WebTarget webTarget;

    public ResourceClient(String hostname, String port){
        client = ClientBuilder.newClient();
        webTarget = client.target(String.format("http://%s:%s/rest/resource", hostname, port));
        logger.debug("ResourceClient initialized with hostname: {} and port: {}", hostname, port);
    }

    public static boolean login(String email, String password){
        WebTarget loginUserWebTarget = webTarget.path("login");
        Usuario user = new Usuario(null, null, email, password);
        logger.debug("Attempting login with user: {}", user);
        Response response = loginUserWebTarget.request(MediaType.APPLICATION_JSON)
                                  .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            Usuario u = response.readEntity(Usuario.class);
            logger.info("User correctly logged in: {}", u);
            VentanaPrincipal vp = new VentanaPrincipal();
            vp.setVisible(true);
            return true;
        } else {
            logger.error("Error connecting with the server. Code: {}", response.getStatus());
            return false;
        }
    }

    public static boolean register(String nombre, String apellido, String email, String password){
        WebTarget registerUserWebTarget = webTarget.path("register");
        Usuario user = new Usuario(nombre, apellido, email, password);
        logger.debug("Registering new user: {}", user);
        Response response = registerUserWebTarget.request(MediaType.APPLICATION_JSON)
                                    .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            logger.info("User correctly registered: {}", user);
            return true;
        } else {
            logger.error("Error connecting with the server. Code: {}", response.getStatus());
            return false;
        }
    }
}
