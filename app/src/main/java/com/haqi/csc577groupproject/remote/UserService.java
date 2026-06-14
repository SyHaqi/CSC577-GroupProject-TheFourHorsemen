package com.haqi.csc577groupproject.remote;

import com.haqi.csc577groupproject.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {

    @FormUrlEncoded
    @POST("users/login")
    Call<User> login(@Field("username") String username,
                     @Field("password") String password);

    @FormUrlEncoded
    @POST("users/register")
    Call<User> register(@Field("username") String username,
                        @Field("email") String email,
                        @Field("password") String password,
                        @Field("phone") String phone);

    @FormUrlEncoded
    @PUT("users/update")
    Call<User> updateProfile(@Header("Authorization") String token,
                             @Field("email") String email,
                             @Field("phone") String phone);
}
