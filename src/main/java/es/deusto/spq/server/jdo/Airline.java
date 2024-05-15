package es.deusto.spq.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import es.deusto.spq.client.domain.Country;
import es.deusto.spq.client.domain.AirAlliance;

@PersistenceCapable(detachable = "true")
public class Airline {
    @PrimaryKey
    private String iataCode;

    @Persistent
    private String name;

    @Persistent
    private Country country;

    @Persistent
    private AirAlliance alliance;

    public Airline() {
    }

    public Airline(String iataCode, String name, Country country, AirAlliance alliance) {
        this.iataCode = iataCode;
        this.name = name;
        this.country = country;
        this.alliance = alliance;
    }

    @Override
    public String toString() {
        return String.format("%s: %s [%s] (%s)", iataCode, name, alliance, country);
    }

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public AirAlliance getAlliance() {
        return alliance;
    }

    public void setAlliance(AirAlliance alliance) {
        this.alliance = alliance;
    }
}
