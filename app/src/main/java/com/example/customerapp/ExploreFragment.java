package com.example.customerapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Download");
        new DownloadJsonTask().execute("http://131.173.65.77:3000/store-details");
        System.out.println("Download complete");
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

    /*private List<StoreDetails> createAndGetStoreData() {
        List<StoreDetails> dummyData = new ArrayList<>();
        dummyData.add(new StoreDetails("Willenbrock", "Hendrick Willenbrock", new Address("Bernd-Rosen-Meyer", "40", "49809"), "0591 963360", "wb@wb.de", "R.drawable.logo1"));
        dummyData.add(new StoreDetails("Hochschule Osnabrück", "Land Niedersachsen", new Address("Kaiserstraße", "10C", "49809"), "0591 80098402", "webmaster@hs-osnabrueck.de", "R.drawable.logo2"));
        return dummyData;
    }*/

    private class DownloadJsonTask extends AsyncTask<String, Void, ArrayList<StoreDetails>> {

        @Nullable
        @Override
        protected ArrayList<StoreDetails> doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String storename = jsonObject.getString("name");
                    String owner = jsonObject.getString("owner");
                    String street = jsonObject.getString("street");
                    String streetNr = jsonObject.getString("streetNr");
                    String phonenumber = jsonObject.getString("phonenumber");
                    String email = jsonObject.getString("email");
                    String logoResourceId = jsonObject.getString("logo"); // Replace with the default logo resource ID

                    StoreDetails storeDetails = new StoreDetails(storename, owner, new Address(street, streetNr), phonenumber, email, logoResourceId);
                    storeList.add(storeDetails);
                    System.out.println(storeDetails.getOwner());
                }

                return storeList;

            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error downloading or decoding JSON data", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<StoreDetails> result) {
            if (result != null) {
                storeList.clear();
                storeList.addAll(result);
                storeListAdapter.notifyDataSetChanged();
            }
        }
    }

}