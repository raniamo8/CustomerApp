package com.example.customerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private List<StoreDetails> storeList;
    private StoreListAdapter storeListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeList = getDummyStoreData();
        storeListAdapter = new StoreListAdapter(requireContext(), storeList);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewStores);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(storeListAdapter);

        return view;
    }


    // Dummy-Daten für die Liste der Läden (ersetze sie durch deine eigenen Daten)
    private List<StoreDetails> getDummyStoreData() {
        List<StoreDetails> dummyData = new ArrayList<>();
        dummyData.add(new StoreDetails("Willenbrock", "Owner 1", new Address("Street 1", "1", "12345"), "123456789", "email1@example.com", R.drawable.logo1));
        dummyData.add(new StoreDetails("Hochschule Osnabrück", "Owner 2", new Address("Street 2", "2", "67890"), "987654321", "email2@example.com", R.drawable.logo2));
        return dummyData;
    }
}