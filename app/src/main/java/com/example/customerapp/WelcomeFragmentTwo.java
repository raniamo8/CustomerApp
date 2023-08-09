package com.example.customerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.Objects;


public class WelcomeFragmentTwo extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View welcomeView = inflater.inflate(R.layout.fragment_welcome_two, container, false);

        Button introAddAddressButton = welcomeView.findViewById(R.id.introAddAddressButton);

        introAddAddressButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

            Objects.requireNonNull(getActivity()).finish();
        });

        return welcomeView;
    }
}
