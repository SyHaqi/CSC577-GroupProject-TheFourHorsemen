package com.haqi.csc577groupproject.model;

public class Ride {

    private String route;
    private String departureTime;
    private int availableSeats;

    public Ride(String route, String departureTime, int availableSeats) {
        this.route = route;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}