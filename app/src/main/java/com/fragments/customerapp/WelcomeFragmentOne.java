package com.fragments.customerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.customerapp.R;

/**
 * First fragment of the introduction.
 */
public class WelcomeFragmentOne extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View welcomeView = inflater.inflate(R.layout.fragment_welcome_one, container, false);
        Button nextButton = welcomeView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {
            Fragment fragment = new WelcomeFragmentTwo();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.welcome_frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        return welcomeView;
    }
}