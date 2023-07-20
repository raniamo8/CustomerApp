package com.example.customerapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class CodeFragment extends Fragment {

    private EditText lastNameEditText, firstNameEditText, streetEditText, streetNrEditText;
    private TextView lastNameErrorTextView, firstNameErrorTextView, streetErrorTextView, streetNrErrorTextView;
    private ImageView qrCodeImageView;

    private static final int WIDTH_HEIGHT_NR = 400;

    private AddressBook addressBook; // AddressBook-Instanz hinzufügen

    @SuppressLint("StaticFieldLeak")
    public static CodeFragment instance;

    public static CodeFragment getInstance() {
        if (instance == null) {
            instance = new CodeFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_code, container, false);

        lastNameEditText = rootView.findViewById(R.id.lastNameEditText);
        firstNameEditText = rootView.findViewById(R.id.firstNameEditText);
        streetEditText = rootView.findViewById(R.id.streetEditText);
        streetNrEditText = rootView.findViewById(R.id.streetNrEditText);

        lastNameErrorTextView = rootView.findViewById(R.id.lastNameErrorTextView);
        firstNameErrorTextView = rootView.findViewById(R.id.firstNameErrorTextView);
        streetErrorTextView = rootView.findViewById(R.id.streetErrorTextView);
        streetNrErrorTextView = rootView.findViewById(R.id.streetNrErrorTextView);

        qrCodeImageView = rootView.findViewById(R.id.qrCodeImageView);

        Button generateQRCodeButton = rootView.findViewById(R.id.buttonGenerate);
        generateQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQRCode();
            }
        });

        addressBook = new AddressBook(); // AddressBook initialisieren

        return rootView;
    }

    private void generateQRCode() {
        String lastName = lastNameEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String street = streetEditText.getText().toString().trim();
        String streetNr = streetNrEditText.getText().toString().trim();

        // Check for valid inputs
        boolean isValidInput = true;
        if (!isValidLastName(lastName)) {
            lastNameErrorTextView.setVisibility(View.VISIBLE);
            isValidInput = false;
        } else {
            lastNameErrorTextView.setVisibility(View.GONE);
        }

        if (!isValidFirstName(firstName)) {
            firstNameErrorTextView.setVisibility(View.VISIBLE);
            isValidInput = false;
        } else {
            firstNameErrorTextView.setVisibility(View.GONE);
        }

        if (!isValidStreet(street)) {
            streetErrorTextView.setVisibility(View.VISIBLE);
            isValidInput = false;
        } else {
            streetErrorTextView.setVisibility(View.GONE);
        }

        if (!isValidStreetNr(streetNr)) {
            streetNrErrorTextView.setVisibility(View.VISIBLE);
            isValidInput = false;
        } else {
            streetNrErrorTextView.setVisibility(View.GONE);
        }

        if (isValidInput) {
            // Create a recipient and add an address
            Recipient recipient = new Recipient(firstName, lastName);
            Address address = new Address(street, streetNr);
            address.setPlz("49808");
            recipient.addAddress(address);

            // Add the recipient to the AddressBook
            addressBook.addRecipient(recipient);

            // Generate and display the QR code
            Bitmap qrCodeBitmap = recipient.generateQRCode();
            qrCodeImageView.setImageBitmap(qrCodeBitmap);

            // Save the QR code to internal storage
            recipient.saveQRCodeToInternalStorage(getContext());
        }
    }

    private boolean isValidLastName(@NonNull String lastName) {
        return !lastName.isEmpty();
    }

    private boolean isValidFirstName(@NonNull String firstName) {
        return !firstName.isEmpty();
    }

    private boolean isValidStreet(@NonNull String street) {
        return !street.isEmpty();
    }

    private boolean isValidStreetNr(@NonNull String streetNr) {
        return !streetNr.isEmpty();
    }
}