package com.doan.a7_tahc.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doan.a7_tahc.R;
import com.doan.a7_tahc.adapters.usersAdapter;
import com.doan.a7_tahc.models.User;
import com.doan.a7_tahc.utilities.Constants;
import com.doan.a7_tahc.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class SearchUser extends AppCompatActivity {
    private ImageButton backButton;
    private TextView tvErrorMessage;
    private PreferenceManager preferenceManager;
    private RecyclerView RVUser;
    private ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        preferenceManager = new PreferenceManager(getApplicationContext());
        backButton = findViewById(R.id.back_btn);
        tvErrorMessage = findViewById(R.id.textErrorMessage);
        RVUser = findViewById(R.id.userRecyclerView);
        progressBar1 = findViewById(R.id.progressBar1);

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });
        getUser();
    }

    private void getUser(){

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    progressBar1.setVisibility(View.INVISIBLE);
                    String currenUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                   if(task.isSuccessful() ){
                        List<User> users = new ArrayList<>();
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if(currenUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }
                            User user = new User();
                            user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            users.add(user);
                        }
                        if(users.size()>0){
                            usersAdapter UsersAdapter = new usersAdapter(users);

                            RVUser.setAdapter(UsersAdapter);
                            UsersAdapter.setUserList(users);
                            UsersAdapter.notifyDataSetChanged();
                            RVUser.setVisibility(View.VISIBLE);

                        }
                        else{
                            showErrorMessage();

                        }
                   }
                });
    }

    private void showErrorMessage(){
        tvErrorMessage.setText(String.format("%s","No user available"));
        tvErrorMessage.setVisibility(View.VISIBLE);
    }

}