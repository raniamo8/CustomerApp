package com.example.customerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.customerapp.databinding.ActivitymainBinding;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

//TODO Code Fragment: PLz spinner color
//TODO: Checkstyle
//TODO: Readme
//TODO: Documentation
//TODO: language check
//-------------------------//
//TODO: test are configured as gradle!!!!!!
//TODO: IntroReset
//TODO: open qr code as layout clickable
//TODO: control increment test in addressbook
//TODO: refreshData
//TODO: placeholder
public class MainActivity extends AppCompatActivity {
    private static final String CURRENT_FRAGMENT_TAG = "current_fragment_tag";
    private Fragment currentFragment;
    ActivitymainBinding binding;
    private AddressBook addressBook;

    @SuppressLint({"NonConstantResourceId", "StaticFieldLeak"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitymainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("MainActivity", "onCreate");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addressBook = AddressBook.getInstance();
        addressBook.loadData(getApplicationContext());

        //TODO: refactor to methode
        SharedPreferences preferences = getSharedPreferences("app_preferences", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("is_first_run", true);

        if (isFirstRun) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }

        if (savedInstanceState == null) {
            FragmentManagerHelper.replace(getSupportFragmentManager(), R.id.frame_layout, new QRCodeListFragment());
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.code:
                    selectedFragment = new QRCodeListFragment();
                    break;
                case R.id.explore:
                    new CheckServerReachabilityTask() {
                        @Override
                        protected void onPostExecute(Boolean isReachable) {
                            if (isReachable) {
                                FragmentManagerHelper.replace(getSupportFragmentManager(), R.id.frame_layout, new ExploreFragment());
                            } else {
                                Toast.makeText(MainActivity.this, "Der Server ist nicht erreichbar", Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();
                    return true;
                case R.id.settings:
                    selectedFragment = new SettingFragment();
                    break;
            }
            if (selectedFragment != null) {
                FragmentManagerHelper.replace(getSupportFragmentManager(), R.id.frame_layout, selectedFragment);
                if (selectedFragment instanceof CodeFragment) {
                    CodeFragment.instance = (CodeFragment) selectedFragment;
                }
            }
            return true;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (currentFragment != null) {
            getSupportFragmentManager().putFragment(outState, CURRENT_FRAGMENT_TAG, currentFragment);
        }
    }

    public boolean isServerReachable(String url, int timeoutMillis) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeoutMillis);
            connection.setReadTimeout(timeoutMillis);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
        addressBook.saveData(getApplicationContext());
    }

    @SuppressLint("StaticFieldLeak")
    private class CheckServerReachabilityTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return isServerReachable("http://131.173.65.77:8080/api/store-details", 500);
        }

        @Override
        protected void onPostExecute(Boolean isReachable) {
            if (!isReachable) {
                System.out.println("Server nicht erreichbar");
                Toast.makeText(MainActivity.this, "Der Server ist nicht erreichbar", Toast.LENGTH_SHORT).show();
            }
        }
    }

}