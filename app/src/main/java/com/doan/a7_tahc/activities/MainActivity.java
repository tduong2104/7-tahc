package com.doan.a7_tahc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.ImageButton;
import android.widget.Toast;

import com.doan.a7_tahc.R;

import com.doan.a7_tahc.utilities.Constants;
import com.doan.a7_tahc.utilities.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private ImageButton selectUser;
    private PreferenceManager preferenceManager;

    ChatFragment chatFragment;
    ProfileFragment profileFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext());

        getToken();
        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();
        selectUser = findViewById(R.id.main_search_btn);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        selectUser.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),UsersActivity.class));
        });





        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_chat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,chatFragment).commit();
                }
                if(item.getItemId()==R.id.menu_profile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_chat);

    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }




    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }
    private void updateToken(String token){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
        documentReference.update(Constants.KEY_FCM_TOKEN,token)

                .addOnFailureListener(e -> showToast("unable update token"));
    }




}