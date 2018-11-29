package io.almp.flatmanager.rest;


import java.util.List;

import io.almp.flatmanager.model.RentHistoryItem;
import io.almp.flatmanager.model.api.LoginAnswer;
import io.almp.flatmanager.model.api.SimpleErrorAnswer;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/v1/user/login")
    @FormUrlEncoded
    Call<LoginAnswer> loginData(@Field("login") String login,
                                @Field("password") String password,
                                @Field("firebasetoken") String firebasetoken);

    @POST("/v1/flat/rent")
    @FormUrlEncoded
    Call<SimpleErrorAnswer> sendRentData(@Field("uid") Long uid,
                                         @Field("flat") Integer flat,
                                         @Field("value") float value);

    @POST("/v1/flat/rents")
    @FormUrlEncoded
    Call<List<RentHistoryItem>> getRents(@Field("flat") Integer flat);
}