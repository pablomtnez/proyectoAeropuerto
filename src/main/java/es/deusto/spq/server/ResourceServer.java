package es.deusto.spq.server;

import es.deusto.spq.server.jdo.Usuario;
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

    public ResourceServer() {}

    @POST
    @Path("/login")
    public static Response login(Usuario user){
        try {
            // Validación básica de la entrada
            if(user.getEmail() == null || user.getPassword() == null) {
                logger.error("Login failed: Email or password is null");
                return Response.status(Response.Status.BAD_REQUEST).entity("Email or password cannot be null").build();
            }

            Usuario u = UsuarioDAO.getInstance().find(user.getEmail());
            if(u != null && user.getPassword().equals(u.getPassword())) {
                logger.info("Login succeeded");
                return Response.ok(u, MediaType.APPLICATION_JSON).build();
            } else {
                logger.error("Login failed: Invalid credentials");
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid email or password").build();
            }
        } catch (Exception e) {
            logger.error("Login failed", e);
            return Response.serverError().entity("An unexpected error occurred").build();
        }
    }

    @POST
    @Path("/register")
    public static Response register(Usuario user){
        try {
            // Validación básica de la entrada
            if(user.getEmail() == null || user.getPassword() == null || user.getNombre() == null || user.getApellido() == null) {
                logger.error("Register failed: One or more fields are null");
                return Response.status(Response.Status.BAD_REQUEST).entity("All fields are required").build();
            }

            UsuarioDAO.getInstance().save(user);
            logger.info("Usuario successfully registered");
            return Response.ok().entity("Usuario successfully registered").build();
        } catch (Exception e) {
            logger.error("Register failed", e);
            return Response.serverError().entity("An unexpected error occurred").build();
        }
    }
}
