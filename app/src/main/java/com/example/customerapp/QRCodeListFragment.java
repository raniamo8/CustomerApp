package com.example.customerapp;

import android.content.Context;
import android.graphics.Bitmap;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QRCodeListFragment extends Fragment {

    private List<String> qrCodeFilePaths;
    private QRCodeAdapter qrCodeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrCodeFilePaths = new ArrayList<>();
        qrCodeAdapter = new QRCodeAdapter(qrCodeFilePaths);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewQRCodeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(qrCodeAdapter);

        loadQRCodeFilePaths();

        view.findViewById(R.id.fabAddQRCode).setOnClickListener(v -> {
            replaceFragment(new CodeFragment());
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void loadQRCodeFilePaths() {
        qrCodeFilePaths.clear();
        File directory = getContext().getDir("qr_codes", Context.MODE_PRIVATE);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    qrCodeFilePaths.add(file.getAbsolutePath());
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_qr_code_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_all_recipients) {
            deleteAllRecipients();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllRecipients() {
        AddressBook addressBook = new AddressBook();
        addressBook.deleteAllRecipients(getContext());
        qrCodeFilePaths.clear(); // Liste leeren, da alle QR-Codes gelöscht wurden
        qrCodeAdapter.notifyDataSetChanged(); // Aktualisieren der RecyclerView-Ansicht
        Toast.makeText(getContext(), "Alle Empfänger und QR-Codes wurden gelöscht.", Toast.LENGTH_SHORT).show();
    }
}

