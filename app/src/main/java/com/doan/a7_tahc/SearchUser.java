package com.doan.a7_tahc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class SearchUser extends AppCompatActivity {
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        backButton = findViewById(R.id.back_btn);

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}