package org.skywise.skywise.api.models;

public class AirportAPIResponse {
    private String type;
    private String ident;
    private String name;
    private float lat;
    private float lon;
    private int alt;
    private String via;

    public AirportAPIResponse(
            String type,
            String ident,
            String name,
            float lat,
            float lon,
            int alt,
            String via
    ) {
        this.type = type;
        this.ident = ident;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.via = via;
    }

    public String getType() {
        return type;
    }

    public String getIdent() {
        return ident;
    }

    public String getName() {
        return name;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public int getAlt() {
        return alt;
    }

    public String getVia() {
        return via;
    }
}
