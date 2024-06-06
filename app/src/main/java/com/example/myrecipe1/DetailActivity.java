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
        String imageBase64 = getIntent().getStringExtra("image");
        recipeId = getIntent().getIntExtra("id_recipe", -1);

        // Find views
        ImageView imageView = findViewById(R.id.imageView);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView timeTextView = findViewById(R.id.timeTextView);
        TextView ingredientsTextView = findViewById(R.id.ingredientsTextView);
        TextView stepsTextView = findViewById(R.id.StepsTextView);
        ImageView deleteIcon = findViewById(R.id.deleteIcon);

        // Set data to views
        if (imageBase64 != null) {
            byte[] imageBytes = Base64.decode(imageBase64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.bglogin);
        }
        titleTextView.setText(nama);
        timeTextView.setText(waktu);
        ingredientsTextView.setText(ingredients);
        stepsTextView.setText(steps);

        // Set delete icon click listener
        deleteIcon.setOnClickListener(v -> {
            if (recipeId != -1) {
                showDeleteConfirmationDialog(recipeId);
            } else {
                Toast.makeText(DetailActivity.this, "Invalid Recipe ID", Toast.LENGTH_SHORT).show();
            }
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
