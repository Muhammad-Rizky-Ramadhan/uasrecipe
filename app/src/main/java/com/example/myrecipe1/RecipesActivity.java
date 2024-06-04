package com.example.myrecipe1;

import android.os.Bundle;

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
        fetchrecipes(id_category);
    }

    private void fetchrecipes(String id_category) {
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
}
