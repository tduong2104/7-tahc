package com.doan.a7_tahc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Verify_email_reset_password extends AppCompatActivity {
    Button verify;
    TextView resendOTP;
    TextView changeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email_reset_password);

        verify = findViewById(R.id.verify_btn);
        resendOTP = findViewById(R.id.resendOTP_tv);
        changeEmail = findViewById(R.id.change_email_tv);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Verify_email_reset_password.this, reset_password.class);
                startActivity(intent);
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}