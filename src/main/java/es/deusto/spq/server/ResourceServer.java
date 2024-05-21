package es.deusto.spq.server;

import es.deusto.spq.server.jdo.*;
import es.deusto.spq.server.services.OneWorldService;
import es.deusto.spq.server.dao.UsuarioDAO;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;

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

    @POST
    @Path("/login")
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

    private boolean isEmailValid(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }

    @GET
    @Path("/airlines")
    public Response getAirlines() {
        try {
            List<Airline> airlines = new ArrayList<>(oneWorldService.loadAirlinesCSV().values());
            return Response.ok(airlines).build();
        } catch (Exception e) {
            logger.error("Failed to get airlines: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to get airlines").build();
        }
    }

    @GET
    @Path("/airports")
    public Response getAirports() {
        try {
            List<Airport> airports = new ArrayList<>(oneWorldService.loadAirportsCSV().values());
            return Response.ok(airports).build();
        } catch (Exception e) {
            logger.error("Failed to get airports: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to get airports").build();
        }
    }

    @GET
    @Path("/planes")
    public Response getPlanes() {
        try {
            List<Plane> planes = new ArrayList<>(oneWorldService.loadPlanesCSV().values());
            return Response.ok(planes).build();
        } catch (Exception e) {
            logger.error("Failed to get planes: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to get planes").build();
        }
    }

    @GET
    @Path("/flights")
    public Response getFlights(@QueryParam("origin") String origin, @QueryParam("destination") String destination) {
        try {
            List<Flight> flights;
            if (origin != null && destination != null) {
                flights = oneWorldService.getFlightsByOriginAndDestination(origin, destination);
            } else {
                flights = new ArrayList<>(oneWorldService.loadFlights().values());
            }
            return Response.ok(flights).build();
        } catch (Exception e) {
            logger.error("Failed to get flights: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to get flights").build();
        }
    }

    @GET
    @Path("/reservations")
    public Response getReservations() {
        try {
            List<Reservation> reservations = oneWorldService.loadReservationsCSV();
            return Response.ok(reservations).build();
        } catch (Exception e) {
            logger.error("Failed to get reservations: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to get reservations").build();
        }
    }
}
