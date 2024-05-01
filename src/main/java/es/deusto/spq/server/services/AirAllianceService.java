package es.deusto.spq.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class AirAllianceService {
    //Atributos utilizadas para generar el localizador de las reservas
	private static final String LOCATOR_ALPHABET = "234679CDFGHJKMNPRTWXYZ";
	private static final Short LOCATOR_LENGTH = 6;
	private static final Random RANDOM = new Random();
	private static List<String> locators = new ArrayList<>();

    protected Logger logger = Logger.getLogger(OneWorldService.class.getName());

    
    
}
