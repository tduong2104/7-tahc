package com.doan.a7_tahc;


import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    public ChatFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recyler_view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
