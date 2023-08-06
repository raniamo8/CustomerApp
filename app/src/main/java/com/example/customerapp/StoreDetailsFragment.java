package com.example.customerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * A fragment that displays detailed information about a specific store.
 * It takes a StoreDetails object as an argument and displays its data, including the store's logo, owner name, etc.
 */
public class StoreDetailsFragment extends Fragment {

    private static final String ARG_STORE_DETAILS = "storeDetails";
    private StoreDetails storeDetails;
    TextView ownerNameTextView, ownerAddressTextView, ownerPhoneTextView, ownerEmailTextView;
    ImageView shopLogoBig;
    private AppCompatImageButton backButton;

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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_details, container, false);

        shopLogoBig = view.findViewById(R.id.shopLogoBig);
        ownerNameTextView = view.findViewById(R.id.ownerNameTextView);
        ownerAddressTextView = view.findViewById(R.id.ownerAddressTextView);
        ownerPhoneTextView = view.findViewById(R.id.ownerPhoneTextView);
        ownerEmailTextView = view.findViewById(R.id.ownerEmailTextView);

        // submitting data in the View
        if (storeDetails != null) {
            ownerNameTextView.setText(storeDetails.getOwner());
            ownerAddressTextView.setText(storeDetails.getStreet() + " " + storeDetails.getHouseNumber());
            ownerPhoneTextView.setText(storeDetails.getTelephone());
            ownerEmailTextView.setText(storeDetails.getEmail());
            String imageUrl = storeDetails.getLogo();

            Picasso.get()
                    .load(imageUrl)
                    .into(shopLogoBig, new Callback() {
                        @Override
                        public void onSuccess() {
                            // Optional: Hier kannst du zusätzlichen Code ausführen, wenn das Bild erfolgreich geladen wurde.
                        }

                        @Override
                        public void onError(Exception e) {
                            // Optional: Hier kannst du zusätzlichen Code ausführen, wenn das Bild nicht geladen werden konnte.
                            // Zum Beispiel könntest du ein Standardbild anzeigen oder eine Fehlermeldung anzeigen.
                        }
                    });
        }

        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> goBackToPreviousFragment());

        return view;
    }

    private void goBackToPreviousFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}

