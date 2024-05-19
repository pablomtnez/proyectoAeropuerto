package es.deusto.spq.client.domain;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport implements Comparable<Airport>, Serializable {

    private static final long serialVersionUID = 1L;
    private String code;
    private String name;
    private String city;
    private Country country;

    // Constructor por defecto
    public Airport() {
    }

    public Airport(String code, String name, String city, Country country) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Airport airport = (Airport) obj;
        return Objects.equals(code, airport.code);
    }

    @Override
    public String toString() {
        return String.format("%s: %s, %s (%s)", code, name, city, country);
    }

    /**
     * Crea un objeto Airport a partir de una cadena de texto separada por comas ",".
     * @param data String con la cadena de texto separada por comas ",".
     * @return Airport con el nuevo objeto creado.
     * @throws Exception Si se produce un error al generar el objeto Airport.
     */
    public static Airport parseCSV(String data) throws Exception {
        try {
            String[] fields = data.split(",");
            return new Airport(fields[0], fields[1], fields[2], Country.valueOf(fields[3]));
        } catch (Exception ex) {
            throw new Exception(String.format("%s from CSV error: %s", Airport.class, data));
        }
    }

    @Override
    public int compareTo(Airport o) {
        return this.getCode().compareTo(o.getCode());
    }
}
