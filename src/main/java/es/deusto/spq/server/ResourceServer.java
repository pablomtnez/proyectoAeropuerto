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
	public Response loginUser(Usuario user){
		try {
			Usuario u = UsuarioDAO.getInstance().find(user.getEmail());
			if(user.getPassword().equals(u.getPassword())) {
				logger.info("Login succeded");
				System.out.println("Login succeded");
				return Response.ok(u, MediaType.APPLICATION_JSON).build();
			} else {
				logger.error("Login failed");
				System.out.println("Login failed");
				return Response.serverError().build();
			}
		} catch (Exception e) {
			logger.error("Login failed");
			System.out.println("Login failed");
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/register")
	public Response registerUser(Usuario user){
		try {
			UsuarioDAO.getInstance().save(user);
			logger.info("Usuario successfully registered");
			System.out.println("Usuario successfully registered");
			return Response.ok().build();
		} catch (Exception e) {
			logger.info("Register failed");
			System.out.println("Register failed");
			return Response.serverError().build();
		}
	}

}
