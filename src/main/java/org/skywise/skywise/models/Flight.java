package org.skywise.skywise.models;

import jakarta.persistence.*;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "pilotID", nullable = false)
    private User pilot;
    @ManyToOne()
    @JoinColumn(name = "copilotID", nullable = false)
    private User copilot;
    @ManyToOne()
    @JoinColumn(name = "flightAttendantID", nullable = false)
    private User flightAttendant;
    @ManyToOne()
    @JoinColumn(name = "planeID", nullable = false)
    private Plane plane;
    private String flightNumber;
    private String fromICAO;
    private String toICAO;
    private String fromGate;
    private String toGate;
    private long fromTime;
    private long toTime;
    private String route;
/*@OneToOne()
    @JoinColumn(name = "startingWaypointID", nullable = false)
    private Waypoint startingWaypoint;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPilot() {
        return pilot;
    }

    public void setPilot(User pilot) {
        this.pilot = pilot;
    }

    public User getCopilot() {
        return copilot;
    }

    public void setCopilot(User copilot) {
        this.copilot = copilot;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public String getFromICAO() {
        return fromICAO;
    }

    public void setFromICAO(String fromICAO) {
        this.fromICAO = fromICAO;
    }

    public String getToICAO() {
        return toICAO;
    }

    public void setToICAO(String toICAO) {
        this.toICAO = toICAO;
    }

    public String getFromGate() {
        return fromGate;
    }

    public void setFromGate(String fromGate) {
        this.fromGate = fromGate;
    }

    public String getToGate() {
        return toGate;
    }

    public void setToGate(String toGate) {
        this.toGate = toGate;
    }

    public long getFromTime() {
        return fromTime;
    }

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public long getToTime() {
        return toTime;
    }

    public void setToTime(long toTime) {
        this.toTime = toTime;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public User getFlightAttendant() {
        return flightAttendant;
    }

    public void setFlightAttendant(User flightAttendant) {
        this.flightAttendant = flightAttendant;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
