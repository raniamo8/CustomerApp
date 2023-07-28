package com.example.customerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private List<StoreDetails> storeList;
    private StoreDetailsAdapter storeListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeList = createAndGetStoreData();
        storeListAdapter = new StoreDetailsAdapter(requireContext(), storeList);
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



    private List<StoreDetails> createAndGetStoreData() {
        List<StoreDetails> dummyData = new ArrayList<>();
        dummyData.add(new StoreDetails("Willenbrock", "Hendrick Willenbrock", new Address("Bernd-Rosen-Meyer", "40", "49809"), "0591 963360", "wb@wb.de", R.drawable.logo1));
        dummyData.add(new StoreDetails("Hochschule Osnabrück", "Land Niedersachsen", new Address("Kaiserstraße", "10C", "49809"), "0591 80098402", "webmaster@hs-osnabrueck.de", R.drawable.logo2));
        return dummyData;
    }
}