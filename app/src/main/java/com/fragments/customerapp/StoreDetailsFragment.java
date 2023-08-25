package com.fragments.customerapp;

import static com.models.customerapp.FragmentManagerHelper.goBackToPreviousFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customerapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.models.customerapp.StoreDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;


/**
 * A fragment that displays detailed information about a specific store.
 * It takes a StoreDetails object as an argument and displays its data, including the store's logo, owner name, etc.
 */
public class StoreDetailsFragment extends Fragment implements OnMapReadyCallback {
    private static final String ARG_STORE_DETAILS = "storeDetails";
    private StoreDetails storeDetails;
    TextView ownerNameTextView, ownerAddressTextView, ownerPhoneTextView, ownerEmailTextView;
    ImageView shopLogoBig, backgroundImageView;
    private AppCompatImageButton backButton;
    private MapView mapView;
    private GoogleMap googleMap;

    public StoreDetailsFragment() {
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_details, container, false);

        shopLogoBig = view.findViewById(R.id.shopLogoBig);
        backgroundImageView = view.findViewById(R.id.backgroundImageView);
        ownerNameTextView = view.findViewById(R.id.ownerNameTextView);
        ownerAddressTextView = view.findViewById(R.id.ownerAddressTextView);
        ownerPhoneTextView = view.findViewById(R.id.ownerPhoneTextView);
        ownerEmailTextView = view.findViewById(R.id.ownerEmailTextView);

        //submitting data into the view
        submittingData();

        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            goBackToPreviousFragment(fragmentManager);
        });

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        LatLng storeLocation = storeDetails.getCoordinates();
        googleMap.addMarker(new MarkerOptions().position(storeLocation).title("Store Location"));
        mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                boundsBuilder.include(storeLocation);

                LatLngBounds bounds = boundsBuilder.build();
                float zoomLevel = 14.0f;
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), zoomLevel));
                mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mapView.onResume();
    }

    private void submittingData() {
        if (storeDetails != null) {
            ownerNameTextView.setText(storeDetails.getOwner());
            ownerAddressTextView.setText(storeDetails.getAddress().getStreet() + " " + storeDetails.getAddress().getHouseNumber());
            ownerPhoneTextView.setText(storeDetails.getTelephone());
            ownerEmailTextView.setText(storeDetails.getEmail());
            String logoImageUrl = storeDetails.getLogo();
            String backgroundImageUrl = storeDetails.getBackgroundImage();

            Picasso.get()
                    .load(logoImageUrl)
                    .into(shopLogoBig, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("Picasso", "Logo-Bild erfolgreich geladen");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("Picasso", "Fehler beim Laden des Logo-Bildes", e);
                        }
                    });

            Picasso.get()
                    .load(backgroundImageUrl)
                    .into(backgroundImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("Picasso", "Hintergrundbild erfolgreich geladen");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("Picasso", "Fehler beim Laden des Hintergrundbildes", e);
                        }
                    });
        }
    }
}