package com.haqi.csc577groupproject.remote;

public class ApiUtils {
    public static final String BASE_URL = "https://aptitude.my/thefourhorsemen/api/";

    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static RideService getRideService(){
        return RetrofitClient.getClient(BASE_URL).create(RideService.class);
    }

    public static BookingService getBookingService() {
        return RetrofitClient.getClient(BASE_URL).create(BookingService.class);
    }
}
