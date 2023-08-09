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


public class WelcomeFragmentOne extends Fragment {

    private TextView welcomeTextView, info1TextView;
    private ImageView intro1;
    Button nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View welcomeView = inflater.inflate(R.layout.fragment_welcome_one, container, false);

        welcomeTextView = welcomeView.findViewById(R.id.welcomeTextView);
        info1TextView = welcomeView.findViewById(R.id.info1TextView);
        intro1 = welcomeView.findViewById(R.id.intro1);
        nextButton = welcomeView.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).replaceFragment(new WelcomeFragmentTwo());
            }
        });


        return welcomeView;
    }

}