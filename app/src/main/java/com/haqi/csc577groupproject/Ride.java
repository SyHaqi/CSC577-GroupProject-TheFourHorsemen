package com.haqi.csc577groupproject.model;

public class Ride {

    private int ride_id;
    private int driver_id;
    private String origin;
    private String destination;
    private String departure_Time;
    private int available_Seats;

    public Ride() {
    }

    public Ride(int ride_id, int driver_id, String origin, String destination, String departure_Time, int available_Seats) {
        this.ride_id = ride_id;
        this.driver_id = driver_id;
        this.origin = origin;
        this.destination = destination;
        this.departure_Time = departure_Time;
        this.available_Seats = available_Seats;
    }

    public int getRide_id() {
        return ride_id;
    }

    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture_Time() {
        return departure_Time;
    }

    public void setDeparture_Time(String departure_Time) {
        this.departure_Time = departure_Time;
    }

    public int getAvailable_Seats() {
        return available_Seats;
    }

    public void setAvailable_Seats(int available_Seats) {
        this.available_Seats = available_Seats;
    }
}