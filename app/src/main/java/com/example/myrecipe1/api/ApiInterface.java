package com.example.myrecipe1.api;

import com.example.myrecipe1.model.category.Category;
import com.example.myrecipe1.model.login.Login;
import com.example.myrecipe1.model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<Login> LoginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> RegisterResponse(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name
    );

    @GET("category.php")
    Call<Category> CategoryResponse(
    );
}