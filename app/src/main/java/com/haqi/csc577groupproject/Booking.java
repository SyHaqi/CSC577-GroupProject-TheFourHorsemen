package com.haqi.csc577groupproject;

public class Booking {

    private int booking_id;
    private int user_id;
    private int ride_id;
    private int seats_booked;

    public Booking() {
    }

    public Booking(int booking_id, int user_id, int ride_id, int seats_booked) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.ride_id = ride_id;
        this.seats_booked = seats_booked;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRide_id() {
        return ride_id;
    }

    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }

    public int getSeats_booked() {
        return seats_booked;
    }

    public void setSeats_booked(int seats_booked) {
        this.seats_booked = seats_booked;
    }
}