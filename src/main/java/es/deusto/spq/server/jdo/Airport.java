package es.deusto.spq.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import es.deusto.spq.client.domain.Country;

@PersistenceCapable(detachable = "true")
public class Airport {
    @PrimaryKey
    private String iataCode;

    @Persistent
    private String name;

    @Persistent
    private String city;

    @Persistent
    private Country country;

    // Constructor sin argumentos
    public Airport() {
    }

    // Constructor con todos los atributos
    public Airport(String iataCode, String name, String city, Country country) {
        this.iataCode = iataCode;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    // Getters y Setters

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public static Airport parseCSV(String data) throws Exception {
        try {
            String[] fields = data.split(";");

            return new Airport(fields[0], fields[1], fields[2], Country.valueOf(fields[3]));

        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Airport.class, data));
        }
    }
}
