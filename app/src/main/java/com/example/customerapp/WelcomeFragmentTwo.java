package com.example.customerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class WelcomeFragmentTwo extends Fragment {

    private TextView addAddressTextView, info2TextView;
    private ImageView intro2;
    Button introAddAddressButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View welcomeView = inflater.inflate(R.layout.fragment_welcome_two, container, false);

        addAddressTextView = welcomeView.findViewById(R.id.addAddressTextView);
        info2TextView = welcomeView.findViewById(R.id.info2TextView);
        intro2 = welcomeView.findViewById(R.id.intro2);
        introAddAddressButton = welcomeView.findViewById(R.id.introAddAddressButton);




        return welcomeView;
    }
}