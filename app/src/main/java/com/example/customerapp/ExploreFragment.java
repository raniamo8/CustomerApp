package com.example.customerapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents a fragment the Explore screen, which displays a list of stores.
 * The fragment contains a list of store data and uses a RecyclerView with the StoreDetailsAdapter to display the store details.
 */
public class ExploreFragment extends Fragment {

    private ArrayList<StoreDetails> storeList;
    private StoreDetailsAdapter storeListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeList = new ArrayList<>();
        storeListAdapter = new StoreDetailsAdapter(requireContext(), storeList);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewStores);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        storeListAdapter = new StoreDetailsAdapter(requireContext(), storeList);
        recyclerView.setAdapter(storeListAdapter);

        if (isNetworkAvailable()) {
            executorService.execute(() -> {
                ArrayList<StoreDetails> result = downloadData("http://131.173.65.77:8080/store-details");
                if (result != null) {
                    requireActivity().runOnUiThread(() -> updateUI(result));
                } else {
                    requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Die Verbindung zum Server ist fehlgeschlagen", Toast.LENGTH_LONG).show());
                }
            });
        } else {
            requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Keine Netzwerkverbindung", Toast.LENGTH_LONG).show());
        }
        return view;
    }

    private ArrayList<StoreDetails> downloadData(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            connection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            ArrayList<StoreDetails> storeList = new ArrayList<>();
            int storeIndex;
            for (storeIndex = 0; storeIndex < jsonArray.length(); storeIndex++) {
                JSONObject jsonObject = jsonArray.getJSONObject(storeIndex);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String owner = jsonObject.getString("owner");
                String street = jsonObject.getString("street");
                String houseNumber = jsonObject.getString("houseNumber");
                String zip = jsonObject.getString("zip");
                String city = jsonObject.getString("city");
                String telephone = jsonObject.getString("telephone");
                String email = jsonObject.getString("email");
                String logo = jsonObject.getString("logo");

                StoreDetails storeDetails = new StoreDetails(id, name, owner, street, houseNumber, zip, city, telephone, email, logo);
                storeList.add(storeDetails);
            }
            return storeList;
            //catch block problem
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error downloading or decoding JSON data", e);
            return null;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateUI(ArrayList<StoreDetails> result) {
        storeList.clear();
        storeList.addAll(result);
        if (storeListAdapter != null) {
            storeListAdapter.notifyDataSetChanged();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }


}
