package customerapp.activities.customerapp;

import static customerapp.models.customerapp.FragmentManagerHelper.replace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.customerapp.R;
import com.example.customerapp.databinding.ActivitymainBinding;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import customerapp.fragments.customerapp.ExploreFragment;
import customerapp.fragments.customerapp.QRCodeListFragment;
import customerapp.fragments.customerapp.SettingFragment;
import customerapp.models.customerapp.AddressBook;


//TODO: Readme
//-------------------------//
//TODO: test are configured as gradle!!!!!!
//TODO: IntroReset

/**
 * Represents the primary user interface of the app.
 */
public class MainActivity extends AppCompatActivity
{
    private static final String CURRENT_FRAGMENT_TAG = "current_fragment_tag";
    private Fragment currentFragment;
    ActivitymainBinding binding;
    private AddressBook addressBook;

    @SuppressLint({"NonConstantResourceId", "StaticFieldLeak"})
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivitymainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("MainActivity", "onCreate");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addressBook = AddressBook.getInstance();
        addressBook.loadData(getApplicationContext());

        SharedPreferences preferences = getSharedPreferences("app_preferences", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("is_first_run", true);

        if (isFirstRun)
        {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }

        if (savedInstanceState == null)
        {
            replace(getSupportFragmentManager(), R.id.frame_layout, new QRCodeListFragment());
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item ->
        {
            Fragment selectedFragment = null;
            switch (item.getItemId())
            {
                case R.id.code:
                    selectedFragment = new QRCodeListFragment();
                    break;
                case R.id.explore:
                    new CheckServerReachabilityTask()
                    {
                        @Override
                        protected void onPostExecute(Boolean isReachable)
                        {
                            if (isReachable)
                            {
                                replace(getSupportFragmentManager(), R.id.frame_layout, new ExploreFragment());
                            } else
                            {
                                Toast.makeText(MainActivity.this, "Der Server ist nicht erreichbar", Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();
                    return true;
                case R.id.settings:
                    selectedFragment = new SettingFragment();
                    break;
            }
            if (selectedFragment != null)
            {
                replace(getSupportFragmentManager(), R.id.frame_layout, selectedFragment);
            }
            return true;
        });
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if (currentFragment != null)
        {
            getSupportFragmentManager().putFragment(outState, CURRENT_FRAGMENT_TAG, currentFragment);
        }
    }

    /**
     * Checks if the server specified by the given URL is reachable within a specified timeout.
     *
     * @param url The URL of the server to check.
     * @param timeoutMillis The maximum time, in milliseconds, before which a response should be received.
     * @return true if the server is reachable within the specified timeout, false otherwise.
     */
    public boolean isServerReachable(String url, int timeoutMillis)
    {
        try
        {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeoutMillis);
            connection.setReadTimeout(timeoutMillis);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e)
        {
            return false;
        }
    }

    /**
     * Called when the fragment is no longer being used.
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
        addressBook.saveData(getApplicationContext());
    }


    /**
     * An asynchronous task to check the reachability of the server. Once the task completes,
     * it updates the UI based on whether the server is reachable or not.
     */
    @SuppressLint("StaticFieldLeak")
    private class CheckServerReachabilityTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... voids)
        {
            return isServerReachable("http://131.173.65.77:8080/api/store-details", 500);
        }
        @Override
        protected void onPostExecute(Boolean isReachable)
        {
            if (!isReachable)
            {
                System.out.println("Server nicht erreichbar");
                Toast.makeText(MainActivity.this, "Der Server ist nicht erreichbar", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
