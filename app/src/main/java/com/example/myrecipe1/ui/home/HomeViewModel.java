package com.example.myrecipe1.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myrecipe1.api.ApiClient;
import com.example.myrecipe1.api.ApiInterface;
import com.example.myrecipe1.model.recipes.DataItem;
import com.example.myrecipe1.model.recipes.Recipes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<DataItem>> dataItemList = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public HomeViewModel(){fetchRecipes();}

    public LiveData<List<DataItem>> getDataItemList() {
        return dataItemList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }


    public void fetchRecipes() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Recipes> call = apiInterface.RecipesResponse();

        call.enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(Call<Recipes> call, Response<Recipes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataItemList.setValue(response.body().getData());
                } else {
                    errorMessage.setValue("Failed to retrieve data");
                }
            }

            @Override
            public void onFailure(Call<Recipes> call, Throwable t) {
                errorMessage.setValue("Failed to connect to the server");
            }
        });
    }


}