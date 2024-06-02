package com.example.myrecipe1.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //    private static final String BASE_URL = "http://10.20.114.179/apploginregister/";
//    private static final String BASE_URL = "https://bibsky.my.id/data_user/";
    private static final String BASE_URL = "https://myrecipe.my.id/apploginregister/";
    //    private static final String BASE_URL = "http://192.168.1.20/apploginregister/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {

        if(retrofit == null){
            retrofit = new  Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}

