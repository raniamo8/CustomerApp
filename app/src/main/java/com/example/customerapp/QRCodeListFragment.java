package com.example.customerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class QRCodeListFragment extends Fragment {

    private List<QRCodeItem> qrCodeItems;
    private QRCodeAdapter qrCodeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrCodeItems = new ArrayList<>();
        qrCodeAdapter = new QRCodeAdapter(qrCodeItems);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewQRCodeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(qrCodeAdapter);

        //For the test!
        addQRCodeItem("Inhalt 1");

        return view;
    }

    private void addQRCodeItem(String content) {
        QRCodeItem qrCodeItem = new QRCodeItem(content);
        qrCodeItems.add(qrCodeItem);
        qrCodeAdapter.notifyDataSetChanged();
    }

    private void replaceFragment(Fragment fragment) {

    }
}

