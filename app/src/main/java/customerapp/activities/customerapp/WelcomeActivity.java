package customerapp.activities.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.customerapp.R;
import customerapp.fragments.customerapp.WelcomeFragmentOne;
import customerapp.models.customerapp.FragmentManagerHelper;


/**
 * Activity for the introduction of the App. It uses two fragments (WelcomeFragmentOne & WelcomeFragmentTwo).
 * The Activity checks if the first run of the app.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences preferences = getSharedPreferences("app_preferences", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("is_first_run", true);

        if (isFirstRun) {
            FragmentManagerHelper.replace(getSupportFragmentManager(), R.id.welcome_frame_layout, new WelcomeFragmentOne());
            preferences.edit().putBoolean("is_first_run", false).apply();
        }
    }

}
