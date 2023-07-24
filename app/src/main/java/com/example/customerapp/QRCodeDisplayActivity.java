package com.example.customerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.customerapp.databinding.ActivityQrcodeDisplayBinding;

public class QRCodeDisplayActivity extends AppCompatActivity {

    private ImageView qrCodeImageView;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_display);

        ImageView imageViewQrCodeDisplay = findViewById(R.id.imageViewQRCode);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("qrCodeFilePath")) {
            String filePath = intent.getStringExtra("qrCodeFilePath");
            Bitmap qrCodeBitmap = BitmapFactory.decodeFile(filePath);
            if (qrCodeBitmap != null) {
                imageViewQrCodeDisplay.setImageBitmap(qrCodeBitmap);
            } else {
                Toast.makeText(this, "Fehler beim Laden des QR-Codes.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Fehler beim Öffnen des QR-Codes.", Toast.LENGTH_SHORT).show();
            finish();
        }

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

}