package com.example.myrecipe1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe1.api.ApiClient;
import com.example.myrecipe1.api.ApiInterface;
import com.example.myrecipe1.model.findrecipebycategory.DataItem;
import com.example.myrecipe1.model.findrecipebycategory.Response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RecipesActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_DETAIL = 1;
    private RecyclerView recyclerView;
    private FindRecipeByCategoryAdapter adapter;
    private ArrayList<DataItem> dataItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        recyclerView = findViewById(R.id.Recipess_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataItemList = new ArrayList<>();
        adapter = new FindRecipeByCategoryAdapter(this, dataItemList);
        recyclerView.setAdapter(adapter);

        String id_category = getIntent().getStringExtra("id_category");
        fetchRecipes(id_category);
    }

    private void fetchRecipes(String id_category) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Response> call = apiInterface.findrecipebycategoryResponse(id_category);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataItemList.clear();
                    dataItemList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                // Tangani kegagalan di sini
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DETAIL && resultCode == RESULT_OK) {
            // Refresh data di sini
            String id_category = getIntent().getStringExtra("id_category");
            fetchRecipes(id_category);
        }
    }
}

