package com.doan.a7_tahc;

import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Sign_up extends AppCompatActivity {
    Button Signup;
    TextView directToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        directToLogin = findViewById(R.id.directToLogin);
        Signup = findViewById(R.id.signup_btn);

        directToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_up.this, Login.class);
                startActivity(intent);
            }
        });


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check email correct
                // check password
                boolean flag = true;
                EditText etEmail = findViewById(R.id.signup_email);
                EditText etUsername = findViewById(R.id.signup_username);
                EditText etPassword = findViewById(R.id.signup_password);
                EditText etConfirmPW = findViewById(R.id.signup_confirm_password);
                String strEmail = etEmail.getText().toString();
                String strUsername = etUsername.getText().toString();
                String strPassword = etPassword.getText().toString();
                String strConfirmPW = etConfirmPW.getText().toString();
                // error user not type full information
                if(strEmail.isEmpty()) {
                    etEmail.setError("Enter an email");
                    flag = false;
                }
                if(!strEmail.isEmpty() && !emailValidator(etEmail)) {
                    etEmail.setError("Enter an valid email");
                    flag = false;
                }
                if (strUsername.isEmpty()) {
                    etUsername.setError("Enter a username");
                    flag = false;
                }
                if (strPassword.isEmpty()) {
                    etPassword.setError("Enter a password");
                    flag = false;
                }
                if (strConfirmPW.isEmpty()) {
                    etConfirmPW.setError("Please confirm your password");
                    flag = false;
                }
                if(flag && !strPassword.equals(strConfirmPW)) {
                    //New password does not match. Enter new password again here.
                    Toast.makeText(v.getContext(), "New password does not match. Enter new password again here",
                            Toast.LENGTH_SHORT).show();
                    flag = false;
                }
                if(flag) {
                    Intent intent = new Intent(Sign_up.this, Verify_email.class);
                    startActivity(intent);
                }
            }
        });
    }
    public boolean emailValidator(EditText etMail) {
        String emailToText = etMail.getText().toString();
        // Android offers the inbuilt patterns which the entered
        // data from the EditText field needs to be compared with
        // In this case the entered data needs to compared with
        // the EMAIL_ADDRESS, which is implemented same below
        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            return true;
        } else {
            return false;
        }
    }
}
