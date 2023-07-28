package com.example.customerapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StoreDetailsFragment extends Fragment {

    private static final String ARG_STORE_DETAILS = "storeDetails";

    private StoreDetails storeDetails;

    public StoreDetailsFragment() {
        // Required empty public constructor
    }

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_details, container, false);
    }
}
