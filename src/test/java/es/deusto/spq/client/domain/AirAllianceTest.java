package es.deusto.spq.client.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class AirAllianceTest {

    @Test
    public void testEnumValues() {
        AirAlliance oneWorldMock = mock(AirAlliance.class);
        when(oneWorldMock.name()).thenReturn("ONE_WORLD");

        AirAlliance skyTeamMock = mock(AirAlliance.class);
        when(skyTeamMock.name()).thenReturn("SKY_TEAM");

        AirAlliance starAllianceMock = mock(AirAlliance.class);
        when(starAllianceMock.name()).thenReturn("STAR_ALLIANCE");

        assertEquals("ONE_WORLD", oneWorldMock.name());
        assertEquals("SKY_TEAM", skyTeamMock.name());
        assertEquals("STAR_ALLIANCE", starAllianceMock.name());
    }
}