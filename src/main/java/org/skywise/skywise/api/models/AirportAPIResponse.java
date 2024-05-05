package org.skywise.skywise.api.models;

public class AirportAPIResponse {
    private final String type;
    private final String ident;
    private final String name;
    private final float lat;
    private final float lon;
    private final int alt;
    private final String via;

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
