package com.haqi.csc577groupproject.remote;

public class ApiUtils {
    public static final String BASE_URL = "http://178.128.220.20/yourStudentID/api/";

    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
