package com.example.myrecipe1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myrecipe1.api.ApiClient;
import com.example.myrecipe1.api.ApiInterface;
import com.example.myrecipe1.model.category.Category;
import com.example.myrecipe1.model.category.DataItem;
import com.example.myrecipe1.model.updatedata.Update;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRecipeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextName, editTextTime, editTextIngredients, editTextInstructions;
    private Spinner spinnerKategoriMakanan;
    private ImageView imageViewRecipe;
    private Uri selectedImageUri;
    private int recipeId;
    private List<DataItem> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        // Retrieve the recipe ID from the intent
        recipeId = getIntent().getIntExtra("id_recipe", -1);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextTime = findViewById(R.id.editTextTime);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextInstructions = findViewById(R.id.editTextInstructions);
        spinnerKategoriMakanan = findViewById(R.id.spinnerKategoriMakanan);
        imageViewRecipe = findViewById(R.id.imageViewRecipe);
        Button buttonPickImage = findViewById(R.id.buttonPickImage);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Set click listener for image picker
        buttonPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        // Set click listener for save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });

        // Load categories into the spinner
        loadCategories();
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageViewRecipe.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCategories() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Category> call = apiInterface.CategoryResponse();
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body().getData();
                    ArrayAdapter<DataItem> adapter = new ArrayAdapter<>(EditRecipeActivity.this, android.R.layout.simple_spinner_item, categoryList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerKategoriMakanan.setAdapter(adapter);
                } else {
                    Toast.makeText(EditRecipeActivity.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(EditRecipeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveRecipe() {
        String name = editTextName.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String instructions = editTextInstructions.getText().toString().trim();
        DataItem selectedCategory = (DataItem) spinnerKategoriMakanan.getSelectedItem();
        String idCategory = selectedCategory != null ? selectedCategory.getIdCategory() : "1"; // Default category ID if none selected

        if (name.isEmpty() || time.isEmpty() || ingredients.isEmpty() || instructions.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        // Convert recipeId and idCategory to string
        RequestBody idRecipeBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(recipeId));
        RequestBody idCategoryBody = RequestBody.create(MediaType.parse("text/plain"), idCategory);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody timeBody = RequestBody.create(MediaType.parse("text/plain"), time);
        RequestBody ingredientsBody = RequestBody.create(MediaType.parse("text/plain"), ingredients);
        RequestBody instructionsBody = RequestBody.create(MediaType.parse("text/plain"), instructions);
        MultipartBody.Part imageBody = prepareFilePart("picture_recipe", selectedImageUri);

        Call<Update> call = apiInterface.updateRecipe(idRecipeBody, nameBody, ingredientsBody, instructionsBody, idCategoryBody, timeBody, imageBody);
        call.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(Call<Update> call, Response<Update> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(EditRecipeActivity.this, "Recipe updated successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity after update
                } else {
                    Toast.makeText(EditRecipeActivity.this, "Failed to update recipe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Update> call, Throwable t) {
                Toast.makeText(EditRecipeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] imageData = bos.toByteArray();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageData);
            return MultipartBody.Part.createFormData(partName, "image.jpg", requestFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
