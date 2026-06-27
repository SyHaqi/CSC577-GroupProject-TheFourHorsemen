package com.haqi.csc577groupproject.remote;

import com.haqi.csc577groupproject.Ride;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RideService {

    @GET("rides")
    Call<List<Ride>> getAllRides(@Header("api-key") String api_key);

    @GET("rides/{id}")
    Call<Ride> getRide(@Header("api-key") String api_key, @Path("id") int id);

}
