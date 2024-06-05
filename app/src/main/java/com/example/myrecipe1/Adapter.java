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

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myrecipe1.model.category.DataItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<DataItem> arrayModel;
    private Context context;

    public Adapter(Context context, ArrayList<DataItem> arrayModel) {
        this.context = context;
        this.arrayModel = arrayModel;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        DataItem dataItem = arrayModel.get(position);
        holder.tvName.setText(dataItem.getNameCategory());
        byte[] imageBytes = Base64.decode(dataItem.getPictureCategory().substring(dataItem.getPictureCategory().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imgCategory.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecipesActivity.class);
            String id_category = dataItem.getIdCategory();
            intent.putExtra("id_category", id_category);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imgCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgCategory = itemView.findViewById((R.id.imgListCatalog));
        }
    }
}
