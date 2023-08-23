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
//-------------------------//
//TODO: test are configured as gradle!!!!!!
//TODO: IntroReset
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

        SharedPreferences preferences = getSharedPreferences("app_preferences", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("is_first_run", true);

        if (isFirstRun) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }

        if (savedInstanceState == null) {
            replaceFragmentBottomNavigation(new QRCodeListFragment());
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.code:
                    replaceFragmentBottomNavigation(new QRCodeListFragment());
                    break;
                case R.id.explore:
                    new CheckServerReachabilityTask() {
                        @Override
                        protected void onPostExecute(Boolean isReachable) {
                            if (isReachable) {
                                replaceFragmentBottomNavigation(new ExploreFragment());
                            } else {
                                Toast.makeText(MainActivity.this, "Der Server ist nicht erreichbar", Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();
                    break;
                case R.id.settings:
                    replaceFragmentBottomNavigation(new SettingFragment());
                    break;
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

    public void replaceFragmentBottomNavigation(Fragment fragment) {
        currentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        if (fragment instanceof CodeFragment) {
            CodeFragment.instance = (CodeFragment) fragment;
        }
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