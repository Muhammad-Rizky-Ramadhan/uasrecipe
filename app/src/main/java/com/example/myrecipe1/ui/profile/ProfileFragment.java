package com.example.myrecipe1.ui.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe1.BookmarkAdapter;
import com.example.myrecipe1.R;
import com.example.myrecipe1.SessionManager;
import com.example.myrecipe1.SplashActivity;
import com.example.myrecipe1.model.viewbookmark.DataItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ProfileFragment extends Fragment {
    TextView etUsername, etName;
    SessionManager sessionManager;

    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_PERMISSION = 100;
    private ImageView profilePicture;
    private ImageButton btnEditProfile, btnLogout;
    private RecyclerView bookmarkRecyclerView;
    private BookmarkAdapter bookmarkAdapter;
    private ProfileViewModel profileViewModel;

    private Handler handler;
    private Runnable runnable;
    private static final long REFRESH_INTERVAL = 10000;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePicture = root.findViewById(R.id.profilePicture);
        btnEditProfile = root.findViewById(R.id.btnEditProfile);
        btnLogout = root.findViewById(R.id.btnLogout);
        etName = root.findViewById(R.id.nameProfile);
        etUsername = root.findViewById(R.id.usernameProfile);
        bookmarkRecyclerView = root.findViewById(R.id.bookmark_recycleview);

        sessionManager = new SessionManager(getActivity());

        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getUserDetail();
            etName.setText(user.get(SessionManager.NAME));
            etUsername.setText(user.get(SessionManager.USERNAME));
        }

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                } else {
                    openGallery();
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutSession();
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Set up RecyclerView
        bookmarkRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookmarkAdapter = new BookmarkAdapter(null);
        bookmarkRecyclerView.setAdapter(bookmarkAdapter);

        // Set up ViewModel
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getBookmarks().observe(getViewLifecycleOwner(), new Observer<List<DataItem>>() {
            @Override
            public void onChanged(List<DataItem> dataItems) {
                if (dataItems != null) {
                    bookmarkAdapter.setBookmarkList(dataItems);
                }
            }
        });

        handler = new Handler();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        runnable = new Runnable() {
            @Override
            public void run() {
                profileViewModel.loadBookmarks();
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

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                // Permission denied, display a message to the user
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                profilePicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
