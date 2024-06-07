package com.example.myrecipe1.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myrecipe1.api.ApiClient;
import com.example.myrecipe1.api.ApiInterface;
import com.example.myrecipe1.model.viewbookmark.DataItem;
import com.example.myrecipe1.model.viewbookmark.Viewbookmark;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<List<DataItem>> bookmarks;

    public LiveData<List<DataItem>> getBookmarks() {
        if (bookmarks == null) {
            bookmarks = new MutableLiveData<>();
            loadBookmarks();
        }
        return bookmarks;
    }

    private void loadBookmarks() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Viewbookmark> call = apiService.ViewBookmarkResponse();
        call.enqueue(new Callback<Viewbookmark>() {
            @Override
            public void onResponse(Call<Viewbookmark> call, Response<Viewbookmark> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookmarks.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<Viewbookmark> call, Throwable t) {
                // Handle the error
            }
        });
    }
}
