package customerapp.fragments.customerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import customerapp.activities.customerapp.MainActivity;
import com.example.customerapp.R;

import customerapp.models.customerapp.FragmentManagerHelper;

/**
 * Second fragment of the introduction.
 */
public class WelcomeFragmentTwo extends Fragment {
    ImageButton backToIntroButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View welcomeView = inflater.inflate(R.layout.fragment_welcome_two, container, false);
        Button introEndButton = welcomeView.findViewById(R.id.introEndButton);
        introEndButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

            requireActivity().finish();
        });

        backToIntroButton = welcomeView.findViewById(R.id.backToIntroButton);
        backToIntroButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentManagerHelper.goBackToPreviousFragment(fragmentManager);
        });

        return welcomeView;
    }
}