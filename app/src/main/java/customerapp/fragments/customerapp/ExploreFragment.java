package customerapp.fragments.customerapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.customerapp.R;
import customerapp.adapters.cutsomerapp.StoreDetailsAdapter;
import com.google.android.gms.maps.model.LatLng;
import customerapp.models.customerapp.Address;
import customerapp.models.customerapp.StoreDetails;

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
public class ExploreFragment extends Fragment
{
    private ArrayList<StoreDetails> storeList;
    private StoreDetailsAdapter storeListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Initializes the fragment's essential data components.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        storeList = new ArrayList<>();
        storeListAdapter = new StoreDetailsAdapter(requireContext(), storeList);
    }

    /**
     * Inflates the fragment layout and initializes UI components.
     *
     * @param inflater           Used to inflate the layout.
     * @param container          The parent view.
     * @param savedInstanceState State information.
     * @return A view representing the fragment.
     */
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewStores);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        storeListAdapter = new StoreDetailsAdapter(requireContext(), storeList);
        recyclerView.setAdapter(storeListAdapter);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> refreshData(true));

        refreshData(false);
        return view;
    }


    /**
     * Initializes the options menu for this fragment.
     *
     * @param menu     The options menu.
     * @param inflater Menu inflater.
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_store_details_fragment, menu);
    }

    /**
     * Handles item selections from the options menu.
     *
     * @param item The selected menu item.
     * @return true if the event was handled, false otherwise.
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_refresh)
        {
            refreshData(true);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Downloads store data from the remote server.
     *
     * @return A list of StoreDetails or null if there's an error during fetch.
     */
    private ArrayList<StoreDetails> downloadData()
    {
        try
        {
            URL url = new URL("http://131.173.65.77:8080/api/store-details");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            connection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            ArrayList<StoreDetails> storeList = new ArrayList<>();
            int storeIndex;
            for (storeIndex = 0; storeIndex < jsonArray.length(); storeIndex++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(storeIndex);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String owner = jsonObject.getString("owner");
                JSONObject addressObject = jsonObject.getJSONObject("address");
                String street = addressObject.getString("street");
                String houseNumber = addressObject.getString("houseNumber");
                String zip = addressObject.getString("zip");
                String city = addressObject.getString("city");
                String telephone = jsonObject.getString("telephone");
                String email = jsonObject.getString("email");
                String logo = jsonObject.getString("logo");
                String backgroundImage = jsonObject.getString("backgroundImage");
                JSONObject coordinatesObject = jsonObject.getJSONObject("coordinates");
                double latitude = coordinatesObject.getDouble("latitude");
                double longitude = coordinatesObject.getDouble("longitude");
                StoreDetails storeDetails = new StoreDetails(id, name, owner, new Address(street, houseNumber, zip, city), telephone, email,
                        logo, backgroundImage, new LatLng(latitude, longitude));
                storeList.add(storeDetails);
                System.out.println(backgroundImage);
            }
            return storeList;
        } catch (IOException e)
        {
            Log.e(TAG, "Error during network connection", e);
            return null;
        } catch (JSONException e)
        {
            Log.e(TAG, "Error decoding JSON data", e);
            return null;
        }
    }

    /**
     * Updates the UI with the provided store details list.
     *
     * @param result List of {@link StoreDetails} to display.
     */
    @SuppressLint("NotifyDataSetChanged")
    private void updateUI(ArrayList<StoreDetails> result)
    {
        storeList.clear();
        storeList.addAll(result);
        if (storeListAdapter != null)
        {
            storeListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Checks if a network connection is available.
     *
     * @return true if a network connection is available, false otherwise.
     */
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    /**
     * Refreshes the displayed store data. Fetches new data from the server if a network connection is available,
     * otherwise, it displays a relevant message to the user.
     *
     * @param isSwipeRefresh Indicates whether this refresh was initiated by the swipe-to-refresh gesture.
     */
    private void refreshData(boolean isSwipeRefresh)
    {
        if (isNetworkAvailable())
        {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() ->
            {
                ArrayList<StoreDetails> result = downloadData();
                requireActivity().runOnUiThread(() ->
                {
                    if (result != null)
                    {
                        updateUI(result);
                        if (isSwipeRefresh)
                        {
                            Toast.makeText(requireContext(), "Die Daten wurden aktualisiert", Toast.LENGTH_SHORT).show();
                        }
                    } else
                    {
                        Toast.makeText(requireContext(), "Die Verbindung zum Server ist fehlgeschlagen", Toast.LENGTH_SHORT).show();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                });
            });
        } else
        {
            Toast.makeText(requireContext(), "Keine Netzwerkverbindung", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
