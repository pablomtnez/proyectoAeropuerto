package es.deusto.spq.server.jdo;

import java.util.List;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import es.deusto.spq.client.domain.AirAlliance;
import es.deusto.spq.client.domain.Pais;

@PersistenceCapable(detachable = "true")
public class Aerolinea {
    @PrimaryKey
    private String code;

    @Persistent
    private String name;

    @Persistent
    private Pais pais;

    @Persistent
    private AirAlliance alliance;

    @Persistent
    private List<Vuelo> vuelos;

    public Aerolinea() {

    }

    public Aerolinea(String code, String name, Pais pais, AirAlliance alliance, List<Vuelo> vuelos) {
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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public AirAlliance getAlliance() {
        return alliance;
    }

    public void setAlliance(AirAlliance alliance) {
        this.alliance = alliance;
    }

    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    public void setVuelo(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    public String toString() {
        return String.format("%s: %s [%s] (%s)", code, name, alliance, pais);
    }
}