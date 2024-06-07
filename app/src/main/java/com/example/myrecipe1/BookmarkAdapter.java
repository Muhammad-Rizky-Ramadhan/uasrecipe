package com.example.myrecipe1;


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
import com.example.myrecipe1.model.viewbookmark.DataItem;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private List<DataItem> bookmarkList;

    public BookmarkAdapter(List<DataItem> bookmarkList) {
        if (bookmarkList == null) {
            this.bookmarkList = new ArrayList<>();
        } else {
            this.bookmarkList = bookmarkList;
        }
    }


    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recipes, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        DataItem bookmark = bookmarkList.get(position);
        holder.recipeName.setText(bookmark.getName());
        holder.recipeTime.setText(bookmark.getTime() + " Menit");
        byte[] imageBytes = Base64.decode(bookmark.getPictureRecipe().substring(bookmark.getPictureRecipe().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.recipeImage.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            int recipeId = Integer.parseInt(bookmark.getIdRecipe());
            intent.putExtra("id_recipe", recipeId);
            intent.putExtra("nama", bookmark.getName());
            intent.putExtra("waktu", bookmark.getTime());
            intent.putExtra("ingredients", bookmark.getIngredients());
            intent.putExtra("steps", bookmark.getSteps());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public void setBookmarkList(List<DataItem> bookmarkList) {
        this.bookmarkList = bookmarkList;
        notifyDataSetChanged();
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName, recipeTime;
        ImageView recipeImage;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_title);
            recipeTime = itemView.findViewById(R.id.recipe_time);
            recipeImage = itemView.findViewById(R.id.recipe_image);
        }
    }
}
