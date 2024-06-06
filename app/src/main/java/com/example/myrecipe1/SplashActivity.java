package com.example.myrecipe1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton getStart;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            navigateToMainActivity();
            return;
        }

        getStart = findViewById(R.id.btnStart);
        getStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        navigateToLoginActivity();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
