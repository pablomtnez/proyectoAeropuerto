
package es.deusto.spq.server;

import es.deusto.spq.server.jdo.Usuario;
import es.deusto.spq.server.services.OneWorldService;
import es.deusto.spq.server.dao.UsuarioDAO;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceServer {
    protected static final Logger logger = LogManager.getLogger();
    private OneWorldService oneWorldService;
    private UsuarioDAO usuarioDAO;

    public ResourceServer() {
        this(new OneWorldService(), UsuarioDAO.getInstance());
    }

    public ResourceServer(OneWorldService oneWorldService, UsuarioDAO usuarioDAO) {
        this.oneWorldService = oneWorldService;
        this.usuarioDAO = usuarioDAO;
    }

    @POST
    @Path("/loadData")
    public Response loadData() {
        try {
            oneWorldService.loadAllData();
            logger.info("Data loading succeeded");
            return Response.ok("Data loaded successfully").build();
        } catch (Exception e) {
            logger.error("Data loading failed: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Data loading failed").build();
        }
    }
    
    @GET
    @Path("/allData")
    public Response getAllData() {
        logger.info("Handling request to get all data");
        try {
            Map<String, Object> allData = oneWorldService.getAllData();
            logger.info("Successfully retrieved all data");
            logger.debug("Data retrieved: {}", allData);
    
            return Response.ok(allData, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            logger.error("Failed to retrieve all data: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to retrieve all data").build();
        }
    }
    


    @Path("/login")
    @POST
    public Response login(Usuario user) {
        try {
            if (user == null || user.getEmail() == null || user.getPassword() == null) {
                logger.error("Login failed: Email or password is null");
                return Response.status(Response.Status.BAD_REQUEST).entity("Email or password cannot be null").build();
            }

            Usuario u = usuarioDAO.find(user.getEmail());
            if (u != null && u.getPassword() != null && u.getPassword().equals(user.getPassword())) {
                logger.info("Login succeeded");
                return Response.ok(u, MediaType.APPLICATION_JSON).build();
            } else {
                logger.error("Login failed: Invalid credentials");
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
            }
        } catch (Exception e) {
            logger.error("Login failed: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/register")
    public Response register(Usuario user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                logger.error("Registration failed: Email or password is null");
                return Response.status(Response.Status.BAD_REQUEST).entity("Email or password cannot be null").build();
            }

            if (!isEmailValid(user.getEmail())) {
                logger.error("Registration failed: Invalid email format");
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid email format").build();
            }

            Usuario existingUser = usuarioDAO.find(user.getEmail());
            if (existingUser != null) {
                logger.error("Registration failed: User already exists");
                return Response.status(Response.Status.CONFLICT).entity("User already exists").build();
            }

            usuarioDAO.save(user); // Guardar el nuevo usuario
            logger.info("Registration succeeded");
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Registration failed: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método auxiliar para validar el formato del correo electrónico
    private boolean isEmailValid(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }

}
