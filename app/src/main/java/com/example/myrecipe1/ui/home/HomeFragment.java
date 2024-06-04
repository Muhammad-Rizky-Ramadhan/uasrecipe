package com.example.myrecipe1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe1.Adapter;
import com.example.myrecipe1.R;
import com.example.myrecipe1.RecipeAdapter;
import com.example.myrecipe1.databinding.FragmentHomeBinding;
import com.example.myrecipe1.model.recipes.DataItem;
import com.example.myrecipe1.ui.category.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private RecyclerView recyclerView;
    private HomeViewModel mViewModel;
    private RecipeAdapter adapter;
    private ArrayList<DataItem> dataItemList;

    public static HomeFragment newInstance() {return new HomeFragment();}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.Recipes_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataItemList = new ArrayList<>();
        adapter = new RecipeAdapter(getContext(), dataItemList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mViewModel.getDataItemList().observe(getViewLifecycleOwner(), new Observer<List<com.example.myrecipe1.model.recipes.DataItem>>() {
            @Override
            public void onChanged(List<com.example.myrecipe1.model.recipes.DataItem> dataItems) {
                dataItemList.clear();
                dataItemList.addAll(dataItems);
                adapter.notifyDataSetChanged();
            }
        });

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}