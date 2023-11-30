package com.doan.a7_tahc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Verify_email_rstPass extends AppCompatActivity {
    Button verifyBtn;
    TextView resendOTP;
    TextView changeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email_rst_pass);

        verifyBtn = findViewById(R.id.verify_rstPass_mail_btn);
        resendOTP = findViewById(R.id.resendOTP_rstPass_tv);
        changeEmail = findViewById(R.id.change_email_tv);

        verifyBtn.setOnClickListener(v -> {
            Intent intent =
                    new Intent(Verify_email_rstPass.this,
                            reset_password.class);
            startActivity(intent);
        });

        changeEmail.setOnClickListener(v -> onBackPressed());
    }
}