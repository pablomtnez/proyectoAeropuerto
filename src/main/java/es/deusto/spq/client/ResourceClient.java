package es.deusto.spq.client;

import javax.swing.JOptionPane;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
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
import es.deusto.spq.client.gui.VentanaVacia;

public class ResourceClient {
    protected static final Logger logger = LogManager.getLogger();
    private static Client client = ClientBuilder.newClient();
    private static WebTarget webTarget;

    public ResourceClient(String hostname, String port){
        webTarget = client.target(String.format("http://%s:%s/rest/resource", hostname, port));
        logger.debug("ResourceClient initialized with hostname: {} and port: {}", hostname, port);
    }

    public static boolean login(String email, String password) {
        WebTarget loginUserWebTarget = webTarget.path("login");
        Usuario user = new Usuario(null, null, email, password);
        logger.debug("Attempting login with user: Email={}, Password=[PROTECTED]", email);
        try {
            Response response = loginUserWebTarget.request(MediaType.APPLICATION_JSON)
                                          .post(Entity.entity(user, MediaType.APPLICATION_JSON));
    
            switch (response.getStatus()) {
                case 200: // OK
                    Usuario u = response.readEntity(Usuario.class);
                    mostrarVentanaPrincipal(u);
                    return true;
                case 401: // Unauthorized
                    mostrarMensajeError("Credenciales inválidas. Por favor, intenta nuevamente.");
                    return false;
                case 403: // Forbidden
                    mostrarMensajeError("Acceso prohibido. No tienes permiso para acceder al recurso solicitado.");
                    return false;
                case 404: // Not Found
                    mostrarMensajeError("Recurso no encontrado. Verifica la URL del servidor.");
                    return false;
                case 500: // Internal Server Error
                    mostrarMensajeError("Error interno del servidor. Intenta de nuevo más tarde.");
                    return false;
                // Agrega otros códigos de estado específicos si es necesario
                default:
                    mostrarMensajeError("Error de conexión con el servidor. Código de estado: " + response.getStatus());
                    return false;
            }
        } catch (ProcessingException e) {
            mostrarMensajeError("Error de procesamiento: " + e.getMessage());
            logger.error("Error de procesamiento: ", e);
            return false;
        } catch (WebApplicationException e) {
            mostrarMensajeError("Error de aplicación web: " + e.getMessage());
            logger.error("Error de aplicación web: ", e);
            return false;
        } catch (Exception e) {
            mostrarMensajeError("Error desconocido: " + e.getMessage());
            logger.error("Error desconocido: ", e);
            return false;
        }
    }
    
    
    private static void mostrarVentanaPrincipal(Usuario usuario) {
        VentanaVacia vp = new VentanaVacia();
        vp.setVisible(true);
        logger.info("Mostrando la Ventana Principal para el usuario: {}", usuario.getEmail());
    }

    private static void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        logger.error(mensaje);
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
