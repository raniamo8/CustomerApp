package customerapp.fragments.customerapp;

import static customerapp.models.customerapp.FragmentManagerHelper.goBackToPreviousFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.customerapp.R;
import customerapp.models.customerapp.Address;
import customerapp.models.customerapp.AddressBook;
import customerapp.models.customerapp.Recipient;

public class QRCodeDisplayFragment extends Fragment {
    TextView showScanTextView, homeTextView;
    ImageView lingenliefertImageView, qrCodeTextImageView, homeImageView;
    ImageButton backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr_code_display, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lingenliefertImageView = view.findViewById(R.id.lingenliefertImageView);
        qrCodeTextImageView = view.findViewById(R.id.qrCodeTextImageView);
        showScanTextView = view.findViewById(R.id.showScanTextView);
        homeImageView = view.findViewById(R.id.homeImageView);
        homeTextView = view.findViewById(R.id.homeTextView);
        ImageView qrCodeImageView = view.findViewById(R.id.qrCodeImageView);
        loadQRCode(qrCodeImageView);
        displayRecipientDetails();

        backButton = view.findViewById(R.id.backButtonToList);
        backButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            goBackToPreviousFragment(fragmentManager);
        });
    }

    private void loadQRCode(ImageView imageView) {
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("qrCodeFilePath")) {
            String filePath = arguments.getString("qrCodeFilePath");
            Bitmap qrCodeBitmap = BitmapFactory.decodeFile(filePath);
            if (qrCodeBitmap != null) {
                imageView.setImageBitmap(qrCodeBitmap);
                displayRecipientDetails();
            } else {
                displayToastAndFinish("Fehler beim Laden des QR-Codes.");
            }
        } else {
            displayToastAndFinish("Fehler beim Öffnen des QR-Codes.");
        }
    }

    private void displayRecipientDetails() {
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("recipientIndex")) {
            int recipientIndex = arguments.getInt("recipientIndex", -1);
            if (recipientIndex >= 0 && recipientIndex < AddressBook.getInstance().getRecipients().size()) {
                Recipient recipient = AddressBook.getInstance().getRecipients().get(recipientIndex);
                String recipientDetails = getFormattedRecipientDetails(recipient);
                TextView textViewRecipientDetails = requireView().findViewById(R.id.textViewRecipientDetails);
                textViewRecipientDetails.setText(recipientDetails);
            }
        }
    }

    private String getFormattedRecipientDetails(Recipient recipient) {
        Address address = recipient.getAddresses().get(0);
        String formattedDetails = recipient.getFirstName() + " " + recipient.getLastName() + "\n"
                + address.getFullAddress();
        return formattedDetails;
    }

    private void displayToastAndFinish(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        }
    }
}
