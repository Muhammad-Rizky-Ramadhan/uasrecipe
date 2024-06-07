package com.example.myrecipe1.api;

import com.example.myrecipe1.model.bookmark.Addbookmark;
import com.example.myrecipe1.model.category.Category;
import com.example.myrecipe1.model.createrecipes.Create;
import com.example.myrecipe1.model.deleterecipes.Delete;
import com.example.myrecipe1.model.findrecipebycategory.Response;
import com.example.myrecipe1.model.findrecipebyid.Findrecipebyid;
import com.example.myrecipe1.model.isbookmark.IsBookmarkedResponse;
import com.example.myrecipe1.model.login.Login;
import com.example.myrecipe1.model.recipes.Recipes;
import com.example.myrecipe1.model.register.Register;
import com.example.myrecipe1.model.updatedata.Update;
import com.example.myrecipe1.model.viewbookmark.Viewbookmark;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("findrecipebyid.php")
    Call<Findrecipebyid> findrecipebyid(@Query("id_recipe") int id_recipe);

    @Multipart
    @POST("create.php")
    Call<Create> createRecipe(
            @Part("name") RequestBody name,
            @Part("ingredients") RequestBody ingredients,
            @Part("steps") RequestBody steps,
            @Part("id_category") RequestBody id_category,
            @Part("time") RequestBody time,
            @Part MultipartBody.Part picture_recipe
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<Delete> deleteRecipe(
            @Field("id_recipe") int id_recipe
    );

    @Multipart
    @POST("update.php")
    Call<Update> updateRecipe(
            @Part("id_recipe") RequestBody id_recipe,
            @Part("name") RequestBody name,
            @Part("ingredients") RequestBody ingredients,
            @Part("steps") RequestBody steps,
            @Part("id_category") RequestBody id_category,
            @Part("time") RequestBody time,
            @Part MultipartBody.Part picture_recipe
    );

    @FormUrlEncoded
    @POST("addBookmark.php")
    Call<Addbookmark> bookmarkRecipe(
            @Field("id_recipe") int id_recipe
    );

    @POST("isbookmarked.php")
    @FormUrlEncoded
    Call<IsBookmarkedResponse> isBookmarked(@Field("id_recipe") int id);

    @GET("viewbookmark.php") // Add this line
    Call<Viewbookmark> ViewBookmarkResponse();



}