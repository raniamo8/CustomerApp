package com.example.customerapp;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OwnerInformationActivity extends AppCompatActivity {
    TextView inormationTitle, generalsTextView, groupTextView, copyrightTextView;
    ImageButton backButtonToSetting;
    ImageView lieferlogoImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_inforamtion);
        inormationTitle = findViewById(R.id.inormationTitle);
        generalsTextView = findViewById(R.id.generalsTextView);
        groupTextView = findViewById(R.id.groupTextView);
        copyrightTextView = findViewById(R.id.copyrightTextView);
        backButtonToSetting = findViewById(R.id.backButtonToSetting);
        lieferlogoImageView = findViewById(R.id.lieferlogoImageView);
        backButtonToSetting.setOnClickListener(v -> onBackPressed());
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}