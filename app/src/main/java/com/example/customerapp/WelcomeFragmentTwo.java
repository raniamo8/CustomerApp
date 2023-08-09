package com.example.customerapp;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;


public class WelcomeFragmentTwo extends Fragment {

    private Button introAddAddressButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View welcomeView = inflater.inflate(R.layout.fragment_welcome_two, container, false);

        introAddAddressButton = welcomeView.findViewById(R.id.introAddAddressButton);

        // Setze einen OnClickListener für den Button, der die nächste Activity oder das Fragment öffnet
        introAddAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

                getActivity().finish();
            }
        });

        return welcomeView;
    }
}
