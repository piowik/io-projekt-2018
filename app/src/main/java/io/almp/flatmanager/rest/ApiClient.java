package io.almp.flatmanager.rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Class containing methods required to create API for clients.
 */

class ApiClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient(String BASE_URL) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}