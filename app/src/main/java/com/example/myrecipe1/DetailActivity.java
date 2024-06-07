package com.example.myrecipe1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myrecipe1.api.ApiClient;
import com.example.myrecipe1.api.ApiInterface;
import com.example.myrecipe1.model.deleterecipes.Delete;
import com.example.myrecipe1.model.findrecipebyid.Data;
import com.example.myrecipe1.model.findrecipebyid.Findrecipebyid;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve data from intent
        String nama = getIntent().getStringExtra("nama");
        String waktu = getIntent().getStringExtra("waktu");
        String ingredients = getIntent().getStringExtra("ingredients");
        String steps = getIntent().getStringExtra("steps");
        recipeId = getIntent().getIntExtra("id_recipe", -1);

        // Find views
        ImageView imageView = findViewById(R.id.imageView);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView timeTextView = findViewById(R.id.timeTextView);
        TextView ingredientsTextView = findViewById(R.id.ingredientsTextView);
        TextView stepsTextView = findViewById(R.id.StepsTextView);
        ImageView deleteIcon = findViewById(R.id.deleteIcon);
        ImageView editIcon = findViewById(R.id.editIcon);

        titleTextView.setText(nama);
        timeTextView.setText(waktu + " Menit");
        ingredientsTextView.setText(ingredients);
        stepsTextView.setText(steps);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Findrecipebyid> call = apiInterface.findrecipebyid(recipeId);
        call.enqueue(new Callback<Findrecipebyid>() {
            @Override
            public void onResponse(Call<Findrecipebyid> call, Response<Findrecipebyid> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Data recipe = response.body().getData();

                    // Set data to views
                    String imageBase64 = recipe.getPictureRecipe();
                    if (imageBase64 != null) {
                        byte[] imageBytes = Base64.decode(imageBase64, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageResource(R.drawable.bglogin);
                    }

                } else {
                    Toast.makeText(DetailActivity.this, "Failed to load recipe details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Findrecipebyid> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        // Set delete icon click listener
        deleteIcon.setOnClickListener(v -> {
            if (recipeId != -1) {
                showDeleteConfirmationDialog(recipeId);
            } else {
                Toast.makeText(DetailActivity.this, "Invalid Recipe ID", Toast.LENGTH_SHORT).show();
            }
        });

        editIcon.setOnClickListener(v -> {
            // Di sini Anda dapat membuka aktivitas edit dengan menyertakan ID resep
            Intent intent = new Intent(DetailActivity.this, EditRecipeActivity.class);
            intent.putExtra("id_recipe", recipeId);
            startActivity(intent);
        });



    }

    private void showDeleteConfirmationDialog(final int recipeId) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRecipe(recipeId);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteRecipe(int id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Delete> call = apiInterface.deleteRecipe(id);

        call.enqueue(new Callback<Delete>() {
            @Override
            public void onResponse(Call<Delete> call, Response<Delete> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        Toast.makeText(DetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);  // Inform HomeFragment about the change
                        finish(); // Close the activity after deletion
                    } else {
                        Toast.makeText(DetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Delete> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}