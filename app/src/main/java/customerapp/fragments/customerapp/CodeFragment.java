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

/**
 * Represents a user interface fragment for generating and displaying QR codes based on user input.
 * Allows the user to enter recipient details and validates the input. Once validated, a QR code
 * is generated and added to the address book.
 */
public class CodeFragment extends Fragment
{
    private EditText lastNameEditText;
    private EditText firstNameEditText;
    private EditText streetEditText;
    private EditText streetNrEditText;
    private TextView lastNameErrorTextView;
    private TextView firstNameErrorTextView;
    private TextView streetErrorTextView;
    private TextView streetNrErrorTextView;
    private TextView plzErrorTextView;
    private Spinner plzSpinner;
    private Button generateQRCodeButton;
    private AppCompatImageButton backButton;
    private AddressBook addressBook;
    private QRCodeAdapter qrCodeAdapter;

    /**
     * Called to do initial creation of a fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addressBook = AddressBook.getInstance();
        try
        {
            addressBook.loadData(getContext());
        } catch (Exception e)
        {
            Log.e("CodeFragment", "Fehler beim Laden des Addressbuchs.", e);
        }
        qrCodeAdapter = new QRCodeAdapter(getContext());
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_code, container, false);

        initializeViews(rootView);
        generateQRCodeButton.setOnClickListener(v -> generate());
        backButton.setOnClickListener(v -> FragmentManagerHelper.goBackToPreviousFragment(requireActivity().getSupportFragmentManager()));

        return rootView;
    }

    /**
     * Called when the fragment is no longer being used.
     */
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        try
        {
            addressBook.saveData(getContext());
        } catch (Exception e)
        {
            Log.e("CodeFragment", "Fehler beim Speichern des Addressbuchs.", e);
        }
    }


    /**
     * Called when the fragment is visible to the user and actively running.
     */
    @Override
    public void onResume()
    {
        super.onResume();
        clearInputFields();
    }


    /**
     * Initializes the views in the fragment.
     *
     * @param rootView The root view of the fragment.
     */
    private void initializeViews(View rootView)
    {
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


    /**
     * Validates the user input for recipient creation.
     *
     * @param lastName The last name of the recipient.
     * @param firstName The first name of the recipient.
     * @param street The street address of the recipient.
     * @param houseNumber The house number of the recipient.
     * @param zip The zip code of the recipient.
     * @return true if the input is valid, otherwise false.
     */
    private boolean isInputValid(String lastName, String firstName, String street, String houseNumber, String zip)
    {
        boolean isValid = true;

        if (lastName.isEmpty())
        {
            lastNameErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else
        {
            lastNameErrorTextView.setVisibility(View.GONE);
        }

        if (firstName.isEmpty())
        {
            firstNameErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else
        {
            firstNameErrorTextView.setVisibility(View.GONE);
        }

        if (street.isEmpty())
        {
            streetErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else
        {
            streetErrorTextView.setVisibility(View.GONE);
        }

        if (houseNumber.isEmpty() || houseNumber.length() > 5)
        {
            streetNrErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else
        {
            streetNrErrorTextView.setVisibility(View.GONE);
        }

        if (zip.isEmpty() || zip.equals("Wählen Sie eine PLZ"))
        {
            plzErrorTextView.setVisibility(View.VISIBLE);
            isValid = false;
        } else
        {
            plzErrorTextView.setVisibility(View.GONE);
        }

        return isValid;
    }

    /**
     * Clears the input fields of the fragment.
     */
    private void clearInputFields()
    {
        lastNameEditText.setText("");
        firstNameEditText.setText("");
        streetEditText.setText("");
        streetNrEditText.setText("");
    }


    /**
     * Creates and saves a new recipient based on the provided information.
     *
     * @param lastName The last name of the recipient.
     * @param firstName The first name of the recipient.
     * @param street The street address of the recipient.
     * @param houseNumber The house number of the recipient.
     * @param zip The zip code of the recipient.
     */
    @SuppressLint("NotifyDataSetChanged")
    private void createAndSaveRecipient(String lastName, String firstName, String street, String houseNumber, String zip)
    {
        Address address = new Address(street, houseNumber, zip);
        Recipient recipient = new Recipient(lastName, firstName, address);
        addressBook.addRecipient(recipient, getContext());
        qrCodeAdapter.notifyDataSetChanged();
        Log.d("Recipient Info", "First Name: " + firstName
                + ", Last Name: " + lastName
                + ", Street: " + street
                + ", Street Nr: " + houseNumber
                + ", PLZ: " + zip
                + ", City: " + address.getCity());
        showSuccessDialog();
    }

    /**
     * Handles the generation of a QR code based on user input.
     */
    private void generate()
    {
        String lastName = lastNameEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String street = streetEditText.getText().toString().trim();
        String houseNumber = streetNrEditText.getText().toString().trim();

        String zip = null;
        try
        {
            zip = plzSpinner.getSelectedItem().toString();
        } catch (NullPointerException npe)
        {
            Log.e("CodeFragment", "Fehler beim Abrufen der ausgewählten PLZ", npe);
            Toast.makeText(getContext(), "Bitte wählen Sie eine PLZ aus", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isInputValid(lastName, firstName, street, houseNumber, zip))
        {
            createAndSaveRecipient(lastName, firstName, street, houseNumber, zip);
            try
            {
                addressBook.saveData(getContext());
            } catch (Exception e)
            {
                Log.e("CodeFragment", "Fehler beim Speichern des Addressbuchs", e);
                Toast.makeText(getContext(), "Fehler beim Speichern des QR-Codes", Toast.LENGTH_SHORT).show();
                return;
            }
            //Toast.makeText(getContext(), "Der QR-Code wurde erfolgreich erstellt", Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(getContext(), "Es liegt einen Fehler beim Ausfüllen vor", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Displays a success dialog to the user after successful QR code generation.
     */
    private void showSuccessDialog()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                //.setTitle("QR-Code")
                .setMessage("Der QR-Code wurde erfolgreich erstellt!")
                .setPositiveButton("Zurück zur Übersicht", (dialog, which) -> clickOnFragment())
                //.setNegativeButton("Abbrechen", null)
                .create();

        alertDialog.setOnCancelListener(dialog -> clickOnFragment());
        alertDialog.show();
    }


    /**
     * Navigates the user to the QRCodeListFragment.
     */
    private void clickOnFragment()
    {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentManagerHelper.goToFragment(fragmentManager,
                R.id.frame_layout,
                new QRCodeListFragment(),
                0,
                0,
                true);
    }

}
