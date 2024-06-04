package com.example.myrecipe1.api;

import com.example.myrecipe1.model.category.Category;
import com.example.myrecipe1.model.findrecipebycategory.Response;
import com.example.myrecipe1.model.login.Login;
import com.example.myrecipe1.model.recipes.Recipes;
import com.example.myrecipe1.model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @GET("recipes.php")
    Call<Recipes> RecipesResponse(
    );

    @GET("findrecipebycategory.php")
    Call<Response> findrecipebycategoryResponse(@Query("id_category") String id_category);


}