package io.almp.flatmanager.rest;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://www.lamp.harryweb.pl/";

    public static ApiInterface getAPIService() {
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}