package com.doan.a7_tahc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileFragment extends Fragment {

    ImageView profilePic;
    TextView usernameView;
    TextView emailView;
    Button updateProfileBtn;
    TextView logoutBtn;

    public ProfileFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic = view.findViewById(R.id.profile_image_view);
        usernameView = view.findViewById(R.id.profile_username);
        emailView = view.findViewById(R.id.profile_email);
        updateProfileBtn = view.findViewById(R.id.profle_update_btn);
        logoutBtn = view.findViewById(R.id.logout_btn);

        updateProfileBtn.setOnClickListener((v -> {
            Intent intent = new Intent(getContext(),UpdateProfileActivity.class);
            startActivity(intent);
        }));

        logoutBtn.setOnClickListener((v)->{
            Intent intent = new Intent(getContext(),SplashScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

        return view;
    }


}