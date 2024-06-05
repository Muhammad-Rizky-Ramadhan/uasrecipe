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
import com.example.myrecipe1.model.recipes.DataItem;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<DataItem> recipeList;
    private Context context;

    public RecipeAdapter(Context context, List<DataItem> recipeList) {
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
        holder.recipeTime.setText(recipe.getTime());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("nama", recipe.getName());
            intent.putExtra("waktu", recipe.getTime());
            intent.putExtra("ingredients", recipe.getIngredients());
            intent.putExtra("steps", recipe.getSteps());
            intent.putExtra("image", recipe.getPictureRecipe());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
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
