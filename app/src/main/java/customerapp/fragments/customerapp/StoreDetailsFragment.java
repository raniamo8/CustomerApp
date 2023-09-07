package customerapp.fragments.customerapp;

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
import customerapp.models.customerapp.StoreDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import customerapp.models.customerapp.FragmentManagerHelper;


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

    /**
     * Initializes the fragment's essential data components.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeDetails = (StoreDetails) getArguments().getSerializable(ARG_STORE_DETAILS);
        }
    }


    /**
     * Inflates the fragment layout and initializes UI components.
     *
     * @param inflater           Used to inflate the layout.
     * @param container          The parent view.
     * @param savedInstanceState State information.
     * @return A view representing the fragment.
     */
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
            FragmentManagerHelper.goBackToPreviousFragment(fragmentManager);
        });

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    /**
     * Lifecycle method called when the fragment resumes its operation.
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }


    /**
     * Initializes the fragment's essential data components.
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


    /**
     * Called when the fragment is no longer being used.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * Lifecycle method called when the fragment resumes its operation.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /**
     * Callback when the map is ready for use.
     * The map's camera position is set to the store's geographical location.
     *
     * @param map A non-null instance of a GoogleMap associated with the MapView defined in this fragment's layout.
     */
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


     /**
     * Fills the UI views with the details from the {@link StoreDetails} object.
     * If the store details are not null, populates text views and loads images using Picasso.
     */
    @SuppressLint("SetTextI18n")
    private void submittingData() {
        if (storeDetails != null) {
            ownerNameTextView.setText(storeDetails.getOwner());
            ownerAddressTextView.setText(storeDetails.getAddress().getStreet() + " " + storeDetails.getAddress().getStreetNr());
            ownerPhoneTextView.setText(storeDetails.getTelephone());
            ownerEmailTextView.setText(storeDetails.getEmail());

            String logoImageUrl = storeDetails.getLogo();

            int logoTargetWidth = (int) getResources().getDimension(R.dimen.shop_logo_big_width);
            int logoTargetHeight = (int) getResources().getDimension(R.dimen.shop_logo_big_height);

            Picasso.get()
                    .load(logoImageUrl)
                    .resize(logoTargetWidth, logoTargetHeight)
                    .centerCrop()
                    .placeholder(R.drawable.baseline_loading_animation)
                    .error(R.drawable.baseline_error_image)
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

            backgroundImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    backgroundImageView.getViewTreeObserver().removeOnPreDrawListener(this);

                    int width = backgroundImageView.getWidth();
                    int height = backgroundImageView.getHeight();

                    Picasso.get()
                            .load(storeDetails.getBackgroundImage())
                            .resize(width, height)
                            .centerCrop()
                            .placeholder(R.drawable.baseline_loading_animation)
                            .error(R.drawable.baseline_error_image)
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

                    return true;
                }
            });
        }
    }
}
