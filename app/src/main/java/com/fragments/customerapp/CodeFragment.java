package com.fragments.customerapp;

import static com.models.customerapp.FragmentManagerHelper.goBackToPreviousFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.customerapp.R;
import com.models.customerapp.EmojiExcludeFilter;
import com.models.customerapp.FragmentManagerHelper;
import com.models.customerapp.Address;
import com.models.customerapp.AddressBook;
import com.models.customerapp.Recipient;

/**
 * Represents a fragment where the user can enter recipient information and generate a QR code for the recipient.
 * The fragment also provides the functionality to save the generated QR code to the internal storage.
 */
public class CodeFragment extends Fragment {
    private EditText lastNameEditText, firstNameEditText, streetEditText, streetNrEditText;
    private TextView lastNameErrorTextView, firstNameErrorTextView, streetErrorTextView, streetNrErrorTextView;
    private ImageView qrCodeImageView;
    Button generateQRCodeButton;
    private AppCompatImageButton backButton;
    private AddressBook addressBook = new AddressBook();

    @SuppressLint("StaticFieldLeak")
    public static CodeFragment instance;

    public static CodeFragment getInstance() {
        if (instance == null) {
            instance = new CodeFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressBook = AddressBook.getInstance();
        addressBook.loadData(getContext());
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

        Spinner zipSpinner = rootView.findViewById(R.id.plzSpinner);
        String selectedZIP = zipSpinner.getSelectedItem().toString();

        //qrCodeImageView = rootView.findViewById(R.id.qrCodeImageView);

        EmojiExcludeFilter emojiFilter = new EmojiExcludeFilter();
        lastNameEditText.setFilters(new InputFilter[]{emojiFilter});
        firstNameEditText.setFilters(new InputFilter[]{emojiFilter});
        streetEditText.setFilters(new InputFilter[]{emojiFilter});
        streetNrEditText.setFilters(new InputFilter[]{emojiFilter});

        generateQRCodeButton = rootView.findViewById(R.id.buttonGenerate);
        generateQRCodeButton.setOnClickListener(v -> {
            generateQRCode();
        });

        backButton = rootView.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            goBackToPreviousFragment(fragmentManager);
        });

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            requireActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addressBook.saveData(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        clearInputFields();
    }


    private void generateQRCode() {
        String lastName = lastNameEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String street = streetEditText.getText().toString().trim();
        String houseNumber = streetNrEditText.getText().toString().trim();
        String selectedZIP = ((Spinner) requireView().findViewById(R.id.plzSpinner)).getSelectedItem().toString();

        if (isInputValid(lastName, firstName, street, houseNumber)) {
            createAndSaveRecipient(lastName, firstName, street, houseNumber, selectedZIP);
            addressBook.saveData(getContext());
        } else {
            Toast.makeText(getContext(), "Es liegt einen Fehler beim Ausfüllen vor.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NewApi")
    private void createAndSaveRecipient(String lastName, String firstName, String street, String houseNumber, String zip) {
        Recipient recipient = new Recipient(lastName, firstName);
        Address address = new Address(street, houseNumber, zip);
        int qrCodeCounter = AddressBook.getQRCodeCounter(getContext());
        address.getCity();
        recipient.addAddress(address);

        Log.d("Recipient Info", "First Name: " + firstName +
                ", Last Name: " + lastName +
                ", Street: " + street +
                ", Street Nr: " + houseNumber +
                ", PLZ: " + zip +
                ", City: " + address.getCity());

        addressBook.addRecipient(recipient, getContext());
        recipient.setQRCodeCounter(qrCodeCounter);
        //Bitmap qrCodeBitmap = recipient.generateQRCode();
        //qrCodeImageView.setImageBitmap(qrCodeBitmap);

        if (recipient.saveQRCodeToInternalStorage(getContext())) {
            qrCodeCounter++;
            AddressBook.setQRCodeCounter(getContext(), qrCodeCounter);
            //Toast.makeText(getContext(), "Der QR-Code wurde erfolgreich generiert.", Toast.LENGTH_SHORT).show();
            showSuccessDialog();
        } else {
            Toast.makeText(getContext(), "Fehler beim Speichern des QR-Codes", Toast.LENGTH_SHORT).show();
        }
        clearInputFields();
    }

    private boolean isInputValid(String lastName, String firstName, String street, String houseNumber) {
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

        //TODO: error handle
        if (!isValidStreetNr(houseNumber)) {
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

    private boolean isValidStreetNr(@NonNull String houseNumber) {
        return !houseNumber.isEmpty() && houseNumber.length() <= 5;
    }


    private void clearInputFields() {
        lastNameEditText.setText("");
        firstNameEditText.setText("");
        streetEditText.setText("");
        streetNrEditText.setText("");
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(requireContext())
                //.setTitle("QR-Code")
                .setMessage("Der QR-Code wurde erfolgreich erstellt!")
                .setPositiveButton("Zurück zur Übersicht", (dialog, which) -> {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentManagerHelper.goToFragment(fragmentManager,
                            R.id.frame_layout,
                            new QRCodeListFragment(),
                            0,
                            0,
                            true);
                })
                //.setNegativeButton("Abbrechen", null)
                .show();
    }


}