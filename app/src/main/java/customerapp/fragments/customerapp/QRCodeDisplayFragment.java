package customerapp.fragments.customerapp;

import static customerapp.models.customerapp.FragmentManagerHelper.goBackToPreviousFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.customerapp.R;

import customerapp.models.customerapp.Address;
import customerapp.models.customerapp.Recipient;

public class QRCodeDisplayFragment extends Fragment {

    private Recipient recipient;
    private ImageView qrImageView;
    ImageButton backButton;

    public QRCodeDisplayFragment() {}

    public static QRCodeDisplayFragment newInstance(Recipient recipient) {
        QRCodeDisplayFragment fragment = new QRCodeDisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipient", recipient);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initializes the fragment's essential data components.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipient = (Recipient) getArguments().getSerializable("recipient");
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qr_code_display, container, false);

        qrImageView = rootView.findViewById(R.id.qrCodeImageView);
        displayQRCode();
        displayRecipientDetails(rootView);

        backButton = rootView.findViewById(R.id.backButtonToList);
        backButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            goBackToPreviousFragment(fragmentManager);
        });

        return rootView;
    }


    /**
     * Display the generated QR code of the recipient in the dedicated ImageView.
     */
    private void displayQRCode() {
        if (recipient != null) {
            qrImageView.setImageBitmap(recipient.generateQRCode());
        }
    }


    /**
     * Display the detailed information of the recipient.
     *
     * @param rootView The root view of the fragment containing the UI elements.
     */
    private void displayRecipientDetails(View rootView) {
        if (recipient != null) {
            String recipientDetails = getFormattedRecipientDetails(recipient);
            TextView textViewRecipientDetails = rootView.findViewById(R.id.textViewRecipientDetails);
            textViewRecipientDetails.setText(recipientDetails);
        }
    }


    /**
     * Formats the recipient details to display in a user-friendly manner.
     *
     * @param recipient Recipient object whose details are to be formatted.
     * @return Formatted details string.
     */
    private String getFormattedRecipientDetails(Recipient recipient) {
        Address address = recipient.getAddress();
        String formattedDetails = recipient.getFirstName() + " " + recipient.getLastName() + "\n"
                + address.getFullAddress();
        return formattedDetails;
    }
}
