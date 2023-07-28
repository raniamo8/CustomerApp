package com.example.customerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QRCodeDisplayActivity extends AppCompatActivity {

    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_display);
        ImageView imageViewQrCodeDisplay = findViewById(R.id.imageViewQRCode);
        loadQRCode(imageViewQrCodeDisplay);
        displayRecipientDetails();
        backButton = findViewById(R.id.backButtonToList);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void loadQRCode(ImageView imageView) {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("qrCodeFilePath")) {
            String filePath = intent.getStringExtra("qrCodeFilePath");
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
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("recipientIndex")) {
            int recipientIndex = intent.getIntExtra("recipientIndex", -1);
            if (recipientIndex >= 0 && recipientIndex < AddressBook.getInstance().getRecipients().size()) {
                Recipient recipient = AddressBook.getInstance().getRecipients().get(recipientIndex);
                String recipientDetails = getFormattedRecipientDetails(recipient);
                TextView textViewRecipientDetails = findViewById(R.id.textViewRecipientDetails);
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

}