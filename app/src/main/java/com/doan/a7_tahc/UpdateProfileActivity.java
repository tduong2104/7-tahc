package com.doan.a7_tahc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editPassword;
    private EditText editConfirmPassword;
    private Button updateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        editUsername = findViewById(R.id.updateprofile_username);
        editPassword = findViewById(R.id.updateprofile_password);
        editConfirmPassword = findViewById(R.id.updateprofile_confirm_password);
        updateBtn = findViewById(R.id.update_btn);

    }
}