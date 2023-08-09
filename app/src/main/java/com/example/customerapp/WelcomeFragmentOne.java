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

import java.util.Objects;


public class WelcomeFragmentOne extends Fragment {

    private Button nextButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View welcomeView = inflater.inflate(R.layout.fragment_welcome_one, container, false);

        nextButton = welcomeView.findViewById(R.id.nextButton);

        // Setze einen OnClickListener für den Button, der WelcomeFragmentTwo öffnet
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new WelcomeFragmentTwo();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.welcome_frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return welcomeView;
    }
}
