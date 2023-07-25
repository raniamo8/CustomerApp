package com.example.customerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.customerapp.databinding.ActivitymainBinding;

//TODO: test the app on real device
public class MainActivity extends AppCompatActivity {
    private static final String CURRENT_FRAGMENT_TAG = "current_fragment_tag";
    private Fragment currentFragment;
    ActivitymainBinding binding;
    private Toolbar toolbar;
    private AddressBook addressBook;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitymainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("MainActivity", "onCreate");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addressBook = AddressBook.getInstance();
        addressBook.loadData(getApplicationContext());

        if (savedInstanceState == null) {
            replaceFragment(new QRCodeListFragment());
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.code:
                    replaceFragment(new QRCodeListFragment());
                    break;
                case R.id.explore:
                    replaceFragment(new ExploreFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingFragment());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
        addressBook.saveData(getApplicationContext());
    }

    private void replaceFragment(Fragment fragment){
        currentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        if (fragment instanceof CodeFragment) {
            CodeFragment.instance = (CodeFragment) fragment;
        }
    }

}