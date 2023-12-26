package com.doan.a7_tahc.activities;

import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.doan.a7_tahc.R;
import com.doan.a7_tahc.utilities.Constants;
import com.doan.a7_tahc.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;

public class Sign_up extends AppCompatActivity {
    Button Signup;
    TextView directToLogin;
    private PreferenceManager preferenceManager;
    private String encodedImage = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        directToLogin = findViewById(R.id.directToLogin);
        Signup = findViewById(R.id.signup_btn);

        preferenceManager = new PreferenceManager(getApplicationContext());

        directToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_up.this, Login.class);
                startActivity(intent);
                finish();
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

                    FirebaseFirestore database = FirebaseFirestore.getInstance();
                    HashMap<String,String> user = new HashMap<>();
                    user.put(Constants.KEY_NAME,etUsername.getText().toString());
                    user.put(Constants.KEY_EMAIL,etEmail.getText().toString());
                    user.put(Constants.KEY_PASSWORD,etPassword.getText().toString());
                    user.put(Constants.KEY_IMAGE,encodedImage);
                     database.collection(Constants.KEY_COLLECTION_USERS)
                             .add(user)
                             .addOnSuccessListener(documentReference -> {
                                 preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                                 preferenceManager.putString(Constants.KEY_USER_ID,documentReference.getId());
                                 preferenceManager.putString(Constants.KEY_NAME,etUsername.getText().toString());
                                 preferenceManager.putString(Constants.KEY_IMAGE,encodedImage);
                                 preferenceManager.putString(Constants.KEY_EMAIL,etEmail.getText().toString());
                                 Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                 startActivity(intent);
                                 finish();
                             })
                             .addOnFailureListener(exception ->{
                                showToast(exception.getMessage());
                             });


                    
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

    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}
