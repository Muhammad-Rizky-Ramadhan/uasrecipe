package com.example.myrecipe1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe1.R;
import com.example.myrecipe1.model.findrecipebycategory.DataItem;

import java.util.List;

public class FindRecipeByCategoryAdapter extends RecyclerView.Adapter<FindRecipeByCategoryAdapter.RecipeViewHolder> {

    private List<DataItem> recipeList;
    private Context context;

    public FindRecipeByCategoryAdapter(Context context, List<DataItem> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_recipes, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        DataItem recipe = recipeList.get(position);
        holder.recipeTitle.setText(recipe.getName());
        // Set the placeholder image
        byte[] imageBytes = Base64.decode(recipe.getPictureRecipe().substring(recipe.getPictureRecipe().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.recipeImage.setImageBitmap(bitmap);
        // Set default values for time and description
        holder.recipeTime.setText(recipe.getTime() +" Menit");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            int recipeId = Integer.parseInt(recipe.getIdRecipe());
            intent.putExtra("id_recipe", recipeId);
            intent.putExtra("nama", recipe.getName());
            intent.putExtra("waktu", recipe.getTime());
            intent.putExtra("ingredients", recipe.getIngredients());
            intent.putExtra("steps", recipe.getSteps());
            if (context instanceof RecipesActivity) {
                ((RecipesActivity) context).startActivityForResult(intent, RecipesActivity.REQUEST_CODE_DETAIL);
            } else {
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // Metode untuk memperbarui seluruh data dalam list
    public void setRecipeList(List<DataItem> newRecipeList) {
        this.recipeList = newRecipeList;
        notifyDataSetChanged();
    }

    // Metode untuk menambahkan satu item baru
    public void addRecipe(DataItem newRecipe) {
        recipeList.add(newRecipe);
        notifyItemInserted(recipeList.size() - 1);
    }

    // Metode untuk menghapus satu item
    public void removeRecipe(int position) {
        recipeList.remove(position);
        notifyItemRemoved(position);
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeTitle, recipeTime, recipemins;
        ImageView recipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
            recipeTime = itemView.findViewById(R.id.recipe_time);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipemins = itemView.findViewById(R.id.recipe_time);
        }
    }
}
