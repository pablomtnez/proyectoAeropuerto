package es.deusto.spq.server.jdo;

import java.util.List;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import es.deusto.spq.client.domain.AirAlliance;
import es.deusto.spq.client.domain.Country;

@PersistenceCapable(detachable = "true")
public class Airline {
    @PrimaryKey
    private String code;

    @Persistent
    private String name;

    @Persistent
    private Country pais;

    @Persistent
    private AirAlliance alliance;

    @Persistent
    private List<Flight> vuelos;

    public Airline() {

    }

    public Airline(String code, String name, Country pais, AirAlliance alliance, List<Flight> vuelos) {
        this.code = code;
        this.name = name;
        this.pais = pais;
        this.alliance = alliance;
        this.vuelos = vuelos;
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

    public Country getPais() {
        return pais;
    }

    public void setPais(Country pais) {
        this.pais = pais;
    }

    public AirAlliance getAlliance() {
        return alliance;
    }

    public void setAlliance(AirAlliance alliance) {
        this.alliance = alliance;
    }

    public List<Flight> getVuelos() {
        return vuelos;
    }

    public void setVuelo(List<Flight> vuelos) {
        this.vuelos = vuelos;
    }

    public String toString() {
        return String.format("%s: %s [%s] (%s)", code, name, alliance, pais);
    }
}