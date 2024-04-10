package es.deusto.spq.server;

import es.deusto.spq.server.jdo.Flight;
import es.deusto.spq.server.jdo.Usuario;
import es.deusto.spq.server.dao.FlightDAO;
import es.deusto.spq.server.dao.UsuarioDAO;
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

    public ResourceServer() {
    }

    @Path("/login")
    @POST
    public Response login(Usuario user) {
        try {
            if (user == null || user.getEmail() == null || user.getPassword() == null) {
                logger.error("Login failed: Email or password is null");
                return Response.status(Response.Status.BAD_REQUEST).entity("Email or password cannot be null").build();
            }

            UsuarioDAO dao = UsuarioDAO.getInstance();
            Usuario u = dao.find(user.getEmail());
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
        UsuarioDAO dao = UsuarioDAO.getInstance();
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                logger.error("Registration failed: Email or password is null");
                return Response.status(Response.Status.BAD_REQUEST).entity("Email or password cannot be null").build();
            }

            if (!isEmailValid(user.getEmail())) {
                logger.error("Registration failed: Invalid email format");
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid email format").build();
            }

            Usuario existingUser = dao.find(user.getEmail());
            if (existingUser != null) {
                logger.error("Registration failed: User already exists");
                return Response.status(Response.Status.CONFLICT).entity("User already exists").build();
            }

            dao.save(user); // Guardar el nuevo usuario
            logger.info("Registration succeeded");
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Registration failed: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/create")
    public Response create(Flight flight) {
        FlightDAO flightdao = FlightDAO.getInstance();
        try {
            if (flight.getCode() == null || flight.getOrigen() == null || flight.getDestino() == null) {
                logger.error("creation failed: code, origin or destination are null");
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("code, origin and destination cannot be null").build();
            }
            flightdao.save(flight);
            logger.info("creation succeeded");
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("creation failed: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método auxiliar para validar el formato del correo electrónico
    private boolean isEmailValid(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }
}
