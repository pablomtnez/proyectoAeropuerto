package es.deusto.spq.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.swing.SwingUtilities;

import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import es.deusto.spq.client.domain.Airline;
//import es.deusto.spq.client.domain.Usuario;

public class ResourceClientPerformanceTest {

    private ResourceClient resourceClient;
    //private Usuario usuario;

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/resourceclient_performance_report.html"));

    @Before
    public void setUp() {
        // Initialize the ResourceClient with appropriate hostname and port for testing
        resourceClient = new ResourceClient("localhost", "8080");
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 5000)
    @JUnitPerfTestRequirement(meanLatency = 1000, maxLatency = 2000)
    public void testLoadDataPerformance() {
    boolean result = false;
    try {
        result = resourceClient.loadData();
    } catch (Exception e) {
        // Handle any exceptions that occur during data loading
        e.printStackTrace();
        fail("An exception occurred during data loading: " + e.getMessage());
    }
    assertTrue("Data loading should be successful", result);
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 5000)
    @JUnitPerfTestRequirement(meanLatency = 1000, maxLatency = 2000, allowedErrorPercentage = (float) 0.1)
    public void testRegisterPerformance() {
        for (int i = 0; i < 100; i++) {
            boolean result = resourceClient.register("John", "Doe", "john.doe" + i + "@example.com", "password123");
            assertTrue("Registration should be successful", result);
        }
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 5000)
    @JUnitPerfTestRequirement(meanLatency = 2000, maxLatency = 2000, allowedErrorPercentage = (float) 0.1)
    public void testGetAirlinesPerformance() {
        for (int i = 0; i < 100; i++) {
            List<Airline> airlines = (List<Airline>) resourceClient.getAirlines();
            assertNotNull("Airlines list should not be null", airlines);
        }
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 5000)
    @JUnitPerfTestRequirement(meanLatency = 1000, maxLatency = 2000, allowedErrorPercentage = (float) 0.1)
    public void testLoginPerformance() {
        for (int i = 0; i < 100; i++) {
        boolean result = resourceClient.login("john.doe"+ i +"@example.com", "password123");
        assertTrue("Login should be successful", result);
        }
    }
    /* 
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 3000)
    @JUnitPerfTestRequirement(meanLatency = 500, maxLatency = 1000, allowedErrorPercentage = (float) 0.1)
    public void testMostrarVentanaPrincipalPerformance() throws Exception {
        for (int i = 0; i < 100; i++) {
            SwingUtilities.invokeAndWait(() -> {
                resourceClient.mostrarVentanaPrincipal(usuario, resourceClient);
            });
        }
        assertTrue(true); // The performance test will handle the validation
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 3000)
    @JUnitPerfTestRequirement(meanLatency = 500, maxLatency = 1000, allowedErrorPercentage = (float) 0.1)
    public void testMostrarMensajeErrorPerformance() throws Exception {
        for (int i = 0; i < 100; i++) {
            SwingUtilities.invokeAndWait(() -> {
                resourceClient.mostrarMensajeError("Test error message");
            });
        }
        assertTrue(true); // The performance test will handle the validation
    }
    */

}
