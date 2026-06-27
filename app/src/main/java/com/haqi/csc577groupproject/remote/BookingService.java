package com.haqi.csc577groupproject.remote;

import com.haqi.csc577groupproject.Booking;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BookingService {

    @FormUrlEncoded
    @POST("bookings")
    Call<Booking> createBooking(
            @Header("api-key") String token,
            @Field("user_id") int userId,
            @Field("ride_id") int rideId,
            @Field("seats_booked") int seatsBooked
    );
}