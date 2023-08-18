package com.example.customerapp;

import static com.example.customerapp.FragmentManagerHelper.goBackToPreviousFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class OwnerInformationFragment extends Fragment {

    TextView informationTitle, generalsTextView, groupTextView, copyrightTextView;
    ImageButton backButtonToSetting;
    ImageView lieferlogoImageView;

    public OwnerInformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_inforamtion, container, false);

        informationTitle = view.findViewById(R.id.informationTitle);
        generalsTextView = view.findViewById(R.id.generalsTextView);
        groupTextView = view.findViewById(R.id.groupTextView);
        copyrightTextView = view.findViewById(R.id.copyrightTextView);
        backButtonToSetting = view.findViewById(R.id.backButtonToSetting);
        lieferlogoImageView = view.findViewById(R.id.lieferlogoImageView);

        backButtonToSetting.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            goBackToPreviousFragment(fragmentManager);
        });

        return view;
    }
}
