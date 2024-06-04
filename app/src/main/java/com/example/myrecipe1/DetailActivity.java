package com.example.myrecipe1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);



        // Retrieve data from intent
        String nama = getIntent().getStringExtra("nama");
        String waktu = getIntent().getStringExtra("waktu");
        String ingredients = getIntent().getStringExtra("ingredients");
        String steps = getIntent().getStringExtra("steps");

        // Find views
        ImageView imageView = findViewById(R.id.imageView);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView timeTextView = findViewById(R.id.timeTextView);
        TextView ingredientsTextView = findViewById(R.id.ingredientsTextView);
        TextView stepsTextView = findViewById(R.id.StepsTextView);

        // Set data to views
        imageView.setImageResource(R.drawable.bglogin); // Set a default image, this can be updated as needed
        titleTextView.setText(nama);
        timeTextView.setText(waktu);
        ingredientsTextView.setText(ingredients);
        stepsTextView.setText(steps);
    }
}
