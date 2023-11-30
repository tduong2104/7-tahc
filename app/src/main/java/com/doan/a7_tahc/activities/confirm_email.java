package com.doan.a7_tahc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.doan.a7_tahc.R;

public class confirm_email extends AppCompatActivity {
    Button confirmBtn;
    TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);

        confirmBtn = findViewById(R.id.confirm_email_button);
        backToLogin = findViewById(R.id.back_to_login_tv);

        confirmBtn.setOnClickListener(v -> {
            Intent intent = new Intent(confirm_email.this, Verify_email_rstPass.class);
            startActivity(intent);
        });

        backToLogin.setOnClickListener(v -> onBackPressed());
    }
}