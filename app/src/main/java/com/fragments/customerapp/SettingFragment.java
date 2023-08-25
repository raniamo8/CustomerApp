package com.fragments.customerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.customerapp.R;
import com.models.customerapp.FragmentManagerHelper;
import com.adapters.cutsomerapp.QRCodeAdapter;
import com.models.customerapp.AddressBook;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that allows users to manage app settings, including enabling or disabling dark mode,
 * adding addresses to recipients, and deleting all QR codes and recipients.
 */
public class SettingFragment extends Fragment {
    Button deleteAllButton, addAddressButton;
    ImageButton informationButton;
    private List<String> qrCodeFilePaths;
    private QRCodeAdapter qrCodeAdapter;
    private AddressBook addressBook;


    public SettingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrCodeFilePaths = new ArrayList<>();
        addressBook = AddressBook.getInstance();
        addressBook.loadData(getContext());
        qrCodeAdapter = new QRCodeAdapter(getContext(), qrCodeFilePaths, addressBook);
        setHasOptionsMenu(true);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        addressBook = AddressBook.getInstance();


        addAddressButton = view.findViewById(R.id.addAddressButton);
        view.findViewById(R.id.addAddressButton).setOnClickListener(v -> goToCodeFragment());

        deleteAllButton = view.findViewById(R.id.deleteAllButton);
        deleteAllButton.setOnClickListener(v -> deleteAllQRandRecipients());

        informationButton = view.findViewById(R.id.informationButton);
        informationButton.setOnClickListener(view1 -> goToOwnerInformationFragment());

        return view;
    }

    private void goToCodeFragment() {
        FragmentManagerHelper.goToFragment(
                requireActivity().getSupportFragmentManager(),
                R.id.frame_layout,
                CodeFragment.getInstance(),
                R.anim.slide_in_right,
                R.anim.slide_out,
                true
        );
    }

    private void goToOwnerInformationFragment() {
        FragmentManagerHelper.goToFragment(
                requireActivity().getSupportFragmentManager(),
                R.id.frame_layout,
                new OwnerInformationFragment(),
                R.anim.slide_in_right,
                R.anim.slide_out,
                true
        );
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteAllQRandRecipients() {
        if (addressBook.getRecipients().isEmpty()) {
            Toast.makeText(getContext(), "Es gibt keine QR-Codes zum Löschen.", Toast.LENGTH_SHORT).show();
        } else {
            addressBook.deleteAllRecipients(getContext());
            Toast.makeText(getContext(), "Alle QR-Codes wurden gelöscht.", Toast.LENGTH_SHORT).show();
        }
    }

}