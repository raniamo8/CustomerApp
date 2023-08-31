package customerapp.fragments.customerapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.customerapp.R;

import customerapp.adapters.cutsomerapp.QRCodeAdapter;
import customerapp.models.customerapp.Address;
import customerapp.models.customerapp.EmojiExcludeFilter;
import customerapp.models.customerapp.AddressBook;
import customerapp.models.customerapp.FragmentManagerHelper;
import customerapp.models.customerapp.Recipient;

public class CodeFragment extends Fragment {
    private EditText lastNameEditText, firstNameEditText, streetEditText, streetNrEditText;
    private TextView lastNameErrorTextView, firstNameErrorTextView, streetErrorTextView, streetNrErrorTextView, plzErrorTextView;
    private Spinner plzSpinner;
    Button generateQRCodeButton;
    private AppCompatImageButton backButton;
    private AddressBook addressBook;
    private QRCodeAdapter qrCodeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressBook = AddressBook.getInstance();
        addressBook.loadData(getContext());
        qrCodeAdapter = new QRCodeAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_code, container, false);

        initializeViews(rootView);
        generateQRCodeButton.setOnClickListener(v -> generate());
        backButton.setOnClickListener(v -> FragmentManagerHelper.goBackToPreviousFragment(requireActivity().getSupportFragmentManager()));

        return rootView;
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

    private void initializeViews(View rootView) {
        lastNameEditText = rootView.findViewById(R.id.lastNameEditText);
        firstNameEditText = rootView.findViewById(R.id.firstNameEditText);
        streetEditText = rootView.findViewById(R.id.streetEditText);
        streetNrEditText = rootView.findViewById(R.id.streetNrEditText);
        plzSpinner = rootView.findViewById(R.id.plzSpinner);

        lastNameErrorTextView = rootView.findViewById(R.id.lastNameErrorTextView);
        firstNameErrorTextView = rootView.findViewById(R.id.firstNameErrorTextView);
        streetErrorTextView = rootView.findViewById(R.id.streetErrorTextView);
        streetNrErrorTextView = rootView.findViewById(R.id.streetNrErrorTextView);
        plzErrorTextView = rootView.findViewById(R.id.plzErrorTextView);

        EmojiExcludeFilter emojiFilter = new EmojiExcludeFilter();
        lastNameEditText.setFilters(new InputFilter[]{emojiFilter});
        firstNameEditText.setFilters(new InputFilter[]{emojiFilter});
        streetEditText.setFilters(new InputFilter[]{emojiFilter});
        streetNrEditText.setFilters(new InputFilter[]{emojiFilter});

        generateQRCodeButton = rootView.findViewById(R.id.buttonGenerate);

        backButton = rootView.findViewById(R.id.backButton);
    }


    private boolean isInputValid(String lastName, String firstName, String street, String houseNumber, String zip) {
        boolean isValid = true;

        if (lastName.isEmpty()) {
            lastNameErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            lastNameErrorTextView.setVisibility(View.GONE);
        }

        if (firstName.isEmpty()) {
            firstNameErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            firstNameErrorTextView.setVisibility(View.GONE);
        }

        if (street.isEmpty()) {
            streetErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            streetErrorTextView.setVisibility(View.GONE);
        }

        if (houseNumber.isEmpty() || houseNumber.length() > 5) {
            streetNrErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            streetNrErrorTextView.setVisibility(View.GONE);
        }

        if (zip.isEmpty() || zip.equals("Wählen Sie eine PLZ")) {  // Hier gehe ich davon aus, dass "Wählen Sie eine PLZ" der Standardtext ist. Ändern Sie diesen Wert entsprechend.
            plzErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            plzErrorTextView.setVisibility(View.GONE);
        }

        return isValid;
    }

    private void clearInputFields() {
        lastNameEditText.setText("");
        firstNameEditText.setText("");
        streetEditText.setText("");
        streetNrEditText.setText("");
    }


    @SuppressLint("NotifyDataSetChanged")
    private void createAndSaveRecipient(String lastName, String firstName, String street, String houseNumber, String zip) {
        Address address = new Address(street, houseNumber, zip);
        Recipient recipient = new Recipient(lastName, firstName, address);
        addressBook.addRecipient(recipient, getContext());
        qrCodeAdapter.notifyDataSetChanged();
        Log.d("Recipient Info", "First Name: " + firstName +
                ", Last Name: " + lastName +
                ", Street: " + street +
                ", Street Nr: " + houseNumber +
                ", PLZ: " + zip +
                ", City: " + address.getCity());
        showSuccessDialog();
    }

    private void generate() {
        String lastName = lastNameEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String street = streetEditText.getText().toString().trim();
        String houseNumber = streetNrEditText.getText().toString().trim();
        String zip = plzSpinner.getSelectedItem().toString();

        if (isInputValid(lastName, firstName, street, houseNumber, zip)) {
            createAndSaveRecipient(lastName, firstName, street, houseNumber, zip);
            addressBook.saveData(getContext());
            //Toast.makeText(getContext(), "Der QR-Code wurde erfolgreich erstellt", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Es liegt einen Fehler beim Ausfüllen vor", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSuccessDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                //.setTitle("QR-Code")
                .setMessage("Der QR-Code wurde erfolgreich erstellt!")
                .setPositiveButton("Zurück zur Übersicht", (dialog, which) -> clickOnFragment())
                //.setNegativeButton("Abbrechen", null)
                .create();

        alertDialog.setOnCancelListener(dialog -> clickOnFragment());
        alertDialog.show();
    }


    private void clickOnFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentManagerHelper.goToFragment(fragmentManager,
                R.id.frame_layout,
                new QRCodeListFragment(),
                0,
                0,
                true);
    }

}
