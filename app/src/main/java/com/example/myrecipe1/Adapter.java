package com.example.myrecipe1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe1.model.category.DataItem;

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
    }

    @Override
    public int getItemCount() {
        return arrayModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
