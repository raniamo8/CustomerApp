package com.example.customerapp;

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

public class QRCodeDisplayFragment extends Fragment {
    ImageButton backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qrcode_display, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageViewQrCodeDisplay = view.findViewById(R.id.imageViewQRCode);
        loadQRCode(imageViewQrCodeDisplay);
        displayRecipientDetails();
        backButton = view.findViewById(R.id.backButtonToList);
        backButton.setOnClickListener(v -> getFragmentManager().popBackStack());
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
                TextView textViewRecipientDetails = getView().findViewById(R.id.textViewRecipientDetails);
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
