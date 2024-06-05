package com.example.myrecipe1.ui.add;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrecipe1.R;
import com.example.myrecipe1.model.category.DataItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AddViewModel addViewModel;
    private EditText etNamaMakanan, etWaktuMemasak, etBahan, etCaraMemasak;
    private Spinner spinnerKategoriMakanan;
    private ImageView ivSelectedImage;
    private Button btnPickImage, btnSubmit;
    private Uri selectedImageUri;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        etNamaMakanan = root.findViewById(R.id.etNamaMakanan);
        etWaktuMemasak = root.findViewById(R.id.etWaktuMemasak);
        etBahan = root.findViewById(R.id.etBahan);
        etCaraMemasak = root.findViewById(R.id.etCaraMemasak);
        spinnerKategoriMakanan = root.findViewById(R.id.spinnerKategoriMakanan);
        ivSelectedImage = root.findViewById(R.id.ivSelectedImage);
        btnPickImage = root.findViewById(R.id.btnPickImage);
        btnSubmit = root.findViewById(R.id.btnSubmit);

        addViewModel = new ViewModelProvider(this).get(AddViewModel.class);

        addViewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<List<DataItem>>() {
            @Override
            public void onChanged(List<DataItem> categories) {
                ArrayAdapter<DataItem> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKategoriMakanan.setAdapter(adapter);
            }
        });

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etNamaMakanan.getText().toString();
                String ingredients = etBahan.getText().toString();
                String steps = etCaraMemasak.getText().toString();
                DataItem selectedCategory = (DataItem) spinnerKategoriMakanan.getSelectedItem();
                String category = selectedCategory != null ? selectedCategory.getIdCategory() : null;
                String time = etWaktuMemasak.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ingredients) || TextUtils.isEmpty(steps) || category == null || TextUtils.isEmpty(time)) {
                    Toast.makeText(getActivity(), "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                MultipartBody.Part imagePart = null;
                if (selectedImageUri != null) {
                    imagePart = prepareFilePart("picture_recipe", selectedImageUri);
                }

                addViewModel.addRecipe(name, ingredients, steps, category, time, imagePart);
            }
        });

        return root;
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                ivSelectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
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
