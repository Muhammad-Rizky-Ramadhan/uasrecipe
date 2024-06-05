package com.example.myrecipe1.ui.add;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myrecipe1.api.ApiClient;
import com.example.myrecipe1.api.ApiInterface;
import com.example.myrecipe1.model.category.Category;
import com.example.myrecipe1.model.category.DataItem;
import com.example.myrecipe1.model.createrecipes.Create;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddViewModel extends AndroidViewModel {

    private ApiInterface apiInterface;
    private MutableLiveData<List<DataItem>> categories;

    public AddViewModel(@NonNull Application application) {
        super(application);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        categories = new MutableLiveData<>();
        fetchCategories();
    }

    public LiveData<List<DataItem>> getCategories() {
        return categories;
    }

    private void fetchCategories() {
        Call<Category> call = apiInterface.CategoryResponse();
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void addRecipe(String name, String ingredients, String steps, String idCategory, String time, MultipartBody.Part image) {
        RequestBody requestBodyName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestBodyIngredients = RequestBody.create(MediaType.parse("text/plain"), ingredients);
        RequestBody requestBodySteps = RequestBody.create(MediaType.parse("text/plain"), steps);
        RequestBody requestBodyCategory = RequestBody.create(MediaType.parse("text/plain"), idCategory);
        RequestBody requestBodyTime = RequestBody.create(MediaType.parse("text/plain"), time);

        Call<Create> call = apiInterface.createRecipe(requestBodyName, requestBodyIngredients, requestBodySteps, requestBodyCategory, requestBodyTime, image);
        call.enqueue(new Callback<Create>() {
            @Override
            public void onResponse(Call<Create> call, Response<Create> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Resep berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "Gagal menambahkan resep", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Create> call, Throwable t) {
                Toast.makeText(getApplication(), "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
