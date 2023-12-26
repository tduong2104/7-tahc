package com.doan.a7_tahc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.doan.a7_tahc.R;
import com.doan.a7_tahc.utilities.Constants;
import com.doan.a7_tahc.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Stack;


public class Login extends AppCompatActivity {

    private TextView directToSignup;
    private TextView forgetPassword;
    private Button login;
    private EditText edtEmail;
    private EditText edtPassword;

    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());

        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);

        directToSignup = findViewById(R.id.directToSignup);
        forgetPassword = findViewById(R.id.forget_password);
        login = findViewById(R.id.login_btn);
        directToSignup.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Sign_up.class);
            startActivity(intent);
            finish();
        });


        login.setOnClickListener( v ->{
            if(isValidSignInDetails()){
                signin();
            }
        });

    }

    private void signin() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL,edtEmail.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD,edtPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult()!=null && task.getResult().getDocumentChanges().size()>0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                        preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_EMAIL,documentSnapshot.getString(Constants.KEY_EMAIL));
                        preferenceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE));
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        showToast("unable to sign in");
                    }
                });
    }

    private Boolean isValidSignInDetails(){
        edtEmail = findViewById(R.id.login_email);
        edtPassword = findViewById(R.id.login_password);
        if(edtEmail.getText().toString().trim().isEmpty()){
            showToast("Enter Email");
            return false;
        }
        else if(!emailValidator(edtEmail)) {
            showToast("Enter valid email");
            return false;
        }
        else if(edtPassword.getText().toString().trim().isEmpty()){
            showToast("Enter Password");
            return false;
        }
        else{

        return true;
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
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