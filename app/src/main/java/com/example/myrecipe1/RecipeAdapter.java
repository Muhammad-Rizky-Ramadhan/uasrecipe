package com.example.myrecipe1;

import android.content.Context;
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
        holder.recipeImage.setImageResource(R.drawable.bglogin);
        // Set default values for time and description
        holder.recipeTime.setText("25 MIN");
        holder.recipeDescription.setText("Default description");
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeTitle, recipeTime, recipeDescription;
        ImageView recipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
            recipeTime = itemView.findViewById(R.id.recipe_time);
            recipeDescription = itemView.findViewById(R.id.recipe_description);
            recipeImage = itemView.findViewById(R.id.recipe_image);
        }
    }
}
