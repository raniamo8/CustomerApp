package com.example.customerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a fragment that displays a list of QR codes generated for recipients.
 * This fragment allows the user to view and manage the list of QR codes and associated recipients.
 */
public class QRCodeListFragment extends Fragment {
    private List<String> qrCodeFilePaths;
    private QRCodeAdapter qrCodeAdapter;
    private AddressBook addressBook;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrCodeFilePaths = new ArrayList<>();
        addressBook = AddressBook.getInstance();
        addressBook.loadData(getContext());
        qrCodeAdapter = new QRCodeAdapter(getContext(), qrCodeFilePaths, addressBook);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code_list, container, false);
        addressBook = AddressBook.getInstance();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewQRCodeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(qrCodeAdapter);

        loadQRCodeFilePaths();

        view.findViewById(R.id.fabAddQRCode).setOnClickListener(v -> goToCodeFragment());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(qrCodeAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_qr_code_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_all_recipients) {
            deleteAllQRandRecipients();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToCodeFragment() {
        FragmentManagerHelper.goToFragment(
                requireActivity().getSupportFragmentManager(),
                R.id.frame_layout,
                CodeFragment.getInstance(),
                R.anim.slide_in,
                R.anim.slide_out,
                true
        );
    }


    @SuppressLint("NotifyDataSetChanged")
    private void loadQRCodeFilePaths() {
        qrCodeFilePaths.clear();
        File directory = requireContext().getDir("qr_codes", Context.MODE_PRIVATE);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    qrCodeFilePaths.add(file.getAbsolutePath());
                }
            }
        }
        //aktulalisierung
        addressBook.loadData(getContext());
        qrCodeAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    protected void deleteAllQRandRecipients() {
        if (qrCodeFilePaths.isEmpty()) {
            Toast.makeText(getContext(), "Es gibt keine QR-Codes zum Löschen.", Toast.LENGTH_SHORT).show();
        } else {
            addressBook.deleteAllRecipients(getContext());
            qrCodeFilePaths.clear();
            qrCodeAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Alle QR-Codes wurden gelöscht.", Toast.LENGTH_SHORT).show();
        }
    }


}

