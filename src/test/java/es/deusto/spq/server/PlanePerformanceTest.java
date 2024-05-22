package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import es.deusto.spq.server.jdo.Plane;

import org.junit.Rule;

public class PlanePerformanceTest {

    private Plane plane;

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/plane_performance_report.html"));

    @Before
    public void setUp() {
        plane = new Plane("code", "name", 200);
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 2000)
    @JUnitPerfTestRequirement(meanLatency = 2000)
    public void testGetSeatsPerformance() {
        for (int i = 0; i < 10000; i++) {
            int seats = plane.getSeats();
            assertTrue(seats == 200); // Ensure that the number of seats is always 200
        }
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    @JUnitPerfTestRequirement(meanLatency = 2000)
    public void testToStringPerformance() {
        for (int i = 0; i < 10000; i++) {
            String expectedToString = "code: name (200 seats)";
            assertEquals(expectedToString, plane.toString());
        }
    }
}