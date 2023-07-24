package com.example.customerapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class CodeFragment extends Fragment {

    private EditText lastNameEditText, firstNameEditText, streetEditText, streetNrEditText;
    private TextView lastNameErrorTextView, firstNameErrorTextView, streetErrorTextView, streetNrErrorTextView;
    private ImageView qrCodeImageView;

    private Toolbar toolbar;

    private AppCompatImageButton backButton;

    private static final int WIDTH_HEIGHT_NR = 400;

    private AddressBook addressBook= new AddressBook();

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

        Spinner plzSpinner = rootView.findViewById(R.id.plzSpinner);
        String selectedPLZ = plzSpinner.getSelectedItem().toString();

        qrCodeImageView = rootView.findViewById(R.id.qrCodeImageView);

        Button generateQRCodeButton = rootView.findViewById(R.id.buttonGenerate);
        generateQRCodeButton.setOnClickListener(v -> {
            generateQRCode(selectedPLZ);
        });

        backButton = rootView.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            goBackToQRCodeListFragment();
        });



        return rootView;
    }

    private void goBackToQRCodeListFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void generateQRCode(String plz) {
        String lastName = lastNameEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String street = streetEditText.getText().toString().trim();
        String streetNr = streetNrEditText.getText().toString().trim();
        String selectedPlz = ((Spinner) getView().findViewById(R.id.plzSpinner)).getSelectedItem().toString();

        if (isInputValid(lastName, firstName, street, streetNr)) {
            createAndSaveRecipient(lastName, firstName, street, streetNr, selectedPlz);
        } else {
            Toast.makeText(getContext(), "Es liegt einen Fehler beim Ausfüllen vor.", Toast.LENGTH_SHORT).show();
        }
    }


    private void createAndSaveRecipient(String firstName, String lastName, String street, String streetNr, String plz) {
        Recipient recipient = new Recipient(firstName, lastName);
        Address address = new Address(street, streetNr, plz);
        recipient.addAddress(address);

        Log.d("Recipient Info", "First Name: " + firstName +
                ", Last Name: " + lastName +
                ", Street: " + street +
                ", Street Nr: " + streetNr +
                ", PLZ: " + plz);
        addressBook.addRecipient(recipient, getContext());

        int qrCodeCounter = AddressBook.getQRCodeCounter(getContext());
        recipient.setQRCodeCounter(qrCodeCounter);

        Bitmap qrCodeBitmap = recipient.generateQRCode();
        qrCodeImageView.setImageBitmap(qrCodeBitmap);

        if (recipient.saveQRCodeToInternalStorage(getContext())) {
            qrCodeCounter++;
            AddressBook.setQRCodeCounter(getContext(), qrCodeCounter);
            Toast.makeText(getContext(), "Der QR-Code wurde erfolgreich generiert.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Fehler beim Speichern des QR-Codes", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInputValid(String lastName, String firstName, String street, String streetNr) {
        boolean isValid = true;

        if (!isValidLastName(lastName)) {
            lastNameErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            lastNameErrorTextView.setVisibility(View.GONE);
        }

        if (!isValidFirstName(firstName)) {
            firstNameErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            firstNameErrorTextView.setVisibility(View.GONE);
        }

        if (!isValidStreet(street)) {
            streetErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            streetErrorTextView.setVisibility(View.GONE);
        }

        if (!isValidStreetNr(streetNr)) {
            streetNrErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            streetNrErrorTextView.setVisibility(View.GONE);
        }

        return isValid;
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            requireActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}