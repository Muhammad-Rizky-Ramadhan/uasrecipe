package com.example.myrecipe1.ui.home;

import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe1.R;
import com.example.myrecipe1.RecipeAdapter;
import com.example.myrecipe1.SessionManager;
import com.example.myrecipe1.model.recipes.DataItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeViewModel mViewModel;
    private RecipeAdapter adapter;
    private ArrayList<DataItem> dataItemList;
    private SessionManager sessionManager;
    private TextView greetingText;

    private Handler handler;
    private Runnable runnable;
    private static final long REFRESH_INTERVAL = 10000;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        sessionManager = new SessionManager(requireContext());

        HashMap<String, String> user = sessionManager.getUserDetail();
        String userName = user.get(SessionManager.NAME);

        greetingText = rootView.findViewById(R.id.greeting_text);
        if (userName != null) {
            greetingText.setText("Hi, " + userName + "!");
        }

        recyclerView = rootView.findViewById(R.id.Recipes_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataItemList = new ArrayList<>();
        adapter = new RecipeAdapter(getContext(), dataItemList);
        recyclerView.setAdapter(adapter);

        handler = new Handler();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mViewModel.getDataItemList().observe(getViewLifecycleOwner(), new Observer<List<DataItem>>() {
            @Override
            public void onChanged(List<DataItem> dataItems) {
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

        // Start the periodic data refresh
        runnable = new Runnable() {
            @Override
            public void run() {
                mViewModel.fetchRecipes();
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Stop the periodic data refresh
        handler.removeCallbacks(runnable);
    }
}
