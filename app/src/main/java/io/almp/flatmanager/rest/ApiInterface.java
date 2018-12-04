package io.almp.flatmanager.rest;


import java.util.List;

import io.almp.flatmanager.model.Message;
import io.almp.flatmanager.model.RentHistoryItem;
import io.almp.flatmanager.model.ShoppingHistoryEntity;
import io.almp.flatmanager.model.User;
import io.almp.flatmanager.model.api.JoinFlatAnswer;
import io.almp.flatmanager.model.api.LoginAnswer;
import io.almp.flatmanager.model.api.MessagesAnswer;
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

    @POST("/v1/user/updatefbtoken")
    @FormUrlEncoded
    Call<SimpleErrorAnswer> updateFirebaseToken(@Field("uid") Long uid,
                                                @Field("firebasetoken") String firebasetoken);


    @POST("/v1/flat/rent")
    @FormUrlEncoded
    Call<SimpleErrorAnswer> sendRentData(@Field("uid") Long uid,
                                         @Field("flat") Integer flat,
                                         @Field("value") float value);

    @POST("/v1/flat/rents")
    @FormUrlEncoded
    Call<List<RentHistoryItem>> getRents(@Field("flat") Integer flat);

    @POST("/v1/flat/join")
    @FormUrlEncoded
    Call<JoinFlatAnswer> joinFlat(@Field("uid") long uid,
                                  @Field("code") String code);

    @POST("/v1/chat/add_message")
    @FormUrlEncoded
    Call<SimpleErrorAnswer> addMessage(@Field("id") Long id,
                                         @Field("token") String token,
                                         @Field("message") String message);

    @POST("v1/flat/add_flat")
    @FormUrlEncoded
    Call<SimpleErrorAnswer> addFlat(@Field("name") String name,
                                    @Field("invitation_code") String invitation_code);

    @POST("/v1/flat/add_shopping_item")
    @FormUrlEncoded
    Call<SimpleErrorAnswer> addShoppingItem(@Field("flat_id") int flat_id,
                                            @Field("user_id") long user_id,
                                            @Field("item_name") String item_name,
                                            @Field("price") String price,
                                            @Field("date") String date);

    @POST("/v1/chat/get_messages")
    @FormUrlEncoded
    Call<List<Message>> getMessages(@Field("id") Long id,
                                    @Field("token") String token);

    @POST("/v1/flat/get_users")
    @FormUrlEncoded
    Call<List<User>> getUserByFlatId(@Field("flat_id") int flatId);

    @POST("v1/flat/get_shoppings")
    @FormUrlEncoded
    Call<List<ShoppingHistoryEntity>> getShoppingHistoryByFlatId(@Field("flat_id") int flatId);

    @POST("v1/flat/update_balance")
    @FormUrlEncoded
    Call<SimpleErrorAnswer> updateUserBalance(@Field("user_id") long user_id,
                                              @Field("cost") double cost);
}