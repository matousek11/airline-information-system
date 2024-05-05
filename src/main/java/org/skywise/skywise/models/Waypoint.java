package org.skywise.skywise.models;

import jakarta.persistence.*;

@Entity()
@Table(name = "waypoints")
public class Waypoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ident;
    private float latitude;
    private float longitude;
    private int altitude;
    private int nextWaypoint;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getNextWaypoint() {
        return nextWaypoint;
    }

    public void setNextWaypoint(int nextWaypoint) {
        this.nextWaypoint = nextWaypoint;
    }
}
