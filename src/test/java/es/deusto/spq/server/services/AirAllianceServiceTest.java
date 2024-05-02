package es.deusto.spq.server.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class AirAllianceServiceTest {

    private static Random randomMock;

    @BeforeClass
    public static void setUpClass() {
        // Reemplazar la instancia RANDOM de la clase por un mock
        randomMock = mock(Random.class);
        // Aquí necesitaríamos acceder al campo RANDOM de AirAllianceService si es accesible
        // Por ahora, suponemos que se puede hacer vía reflection o modificando la visibilidad de RANDOM a package-private o protected
        //AirAllianceService.setRandom(randomMock);
    }

   /* @Test
    public void testGenerateLocator() {
        // Configurar el mock para devolver valores específicos que resultarán en un localizador predecible
        when(randomMock.nextInt(anyInt())).thenReturn(1, 0, 2, 5, 3, 4); // Correspondiente a "24679C" según LOCATOR_ALPHABET

        //String locator = AirAllianceService.generateLocator();
        assertNotNull(locator);
        assertEquals(6, locator.length()); // Asegurar que la longitud del localizador sea la correcta
        assertEquals("24679C", locator); // Verificar que el localizador generado es correcto
    }*/

    @Test
    public void testConstructor() {
        // Test para verificar la creación de una instancia de AirAllianceService
        AirAllianceService service = new AirAllianceService();
        assertNotNull(service);
    }
}
