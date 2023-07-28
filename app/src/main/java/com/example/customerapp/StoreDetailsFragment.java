package com.example.customerapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreDetailsFragment extends Fragment {

    private static final String ARG_STORE_DETAILS = "storeDetails";

    private StoreDetails storeDetails;

    public StoreDetailsFragment() {}

    public static StoreDetailsFragment newInstance(StoreDetails storeDetails) {
        StoreDetailsFragment fragment = new StoreDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORE_DETAILS, storeDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeDetails = (StoreDetails) getArguments().getSerializable(ARG_STORE_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_details, container, false);

        ImageView shopLogoBig = view.findViewById(R.id.shopLogoBig);
        TextView ownerNameTextView = view.findViewById(R.id.ownerNameTextView);
        TextView ownerAddressTextView = view.findViewById(R.id.ownerAddressTextView);
        TextView ownerPhoneTextView = view.findViewById(R.id.ownerPhoneTextView);
        TextView ownerEmailTextView = view.findViewById(R.id.ownerEmailTextView);

        // Daten in die Views setzen
        if (storeDetails != null) {
            shopLogoBig.setImageResource(storeDetails.getLogoResourceId());
            ownerNameTextView.setText(storeDetails.getOwner());
            ownerAddressTextView.setText(storeDetails.getAddress().getFullAddress());
            ownerPhoneTextView.setText(storeDetails.getPhonenumber());
            ownerEmailTextView.setText(storeDetails.getEmail());
        }

        return view;
    }

    private void goBackToPreviousFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}

