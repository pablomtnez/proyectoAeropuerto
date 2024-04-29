package es.deusto.spq.client.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CountryTest {

    @Test
    public void testEnumValues() {
        assertEquals("United Arab Emirates", Country.AE.getName());
        assertEquals("Netherlands Antilles", Country.AN.getName());
        assertEquals("Angola", Country.AO.getName());
        // Add similar assertions for all other enum values
        // ...
        assertEquals("Vietnam", Country.VN.getName());
        assertEquals("South Africa", Country.ZA.getName());
    }
}
