package com.example.customerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//TODO: display the QRCode information (Strings)

public class QRCodeDisplayActivity extends AppCompatActivity {

    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_display);
        ImageView imageViewQrCodeDisplay = findViewById(R.id.imageViewQRCode);
        loadQRCode(imageViewQrCodeDisplay);

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
            } else {
                displayToastAndFinish("Fehler beim Laden des QR-Codes.");
            }
        } else {
            displayToastAndFinish("Fehler beim Öffnen des QR-Codes.");
        }
    }

    private void displayToastAndFinish(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

}