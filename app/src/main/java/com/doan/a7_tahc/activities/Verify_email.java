package com.doan.a7_tahc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.doan.a7_tahc.R;

public class Verify_email extends AppCompatActivity {
    Button verify;
    TextView resendOTP;
    TextView backToSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        verify = findViewById(R.id.verify_btn);
        resendOTP = findViewById(R.id.resendOTP_tv);
        backToSignup = findViewById(R.id.back_to_signup);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Verify_email.this, MainActivity.class);
                startActivity(intent);
            }
        });

        backToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}