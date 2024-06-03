package com.example.myrecipe1.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myrecipe1.api.ApiClient;
import com.example.myrecipe1.api.ApiInterface;
import com.example.myrecipe1.model.category.Category;
import com.example.myrecipe1.model.category.DataItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends ViewModel {

    private final MutableLiveData<List<DataItem>> dataItemList = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public CategoryViewModel() {
        fetchCategories();
    }

    public LiveData<List<DataItem>> getDataItemList() {
        return dataItemList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private void fetchCategories() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Category> call = apiInterface.CategoryResponse();

        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataItemList.setValue(response.body().getData());
                } else {
                    errorMessage.setValue("Failed to retrieve data");
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                errorMessage.setValue("Failed to connect to the server");
            }
        });
    }
}
