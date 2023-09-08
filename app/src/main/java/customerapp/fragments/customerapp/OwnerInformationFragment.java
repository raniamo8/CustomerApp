package customerapp.fragments.customerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.customerapp.R;

import customerapp.models.customerapp.FragmentManagerHelper;


/**
 * A Fragment that displays detailed information about the developers of the app.
 */
public class OwnerInformationFragment extends Fragment
{
    private TextView informationTitleTextView;
    private TextView generalsTextView;
    private TextView groupTextView;
    private TextView copyrightTextView;
    private ImageButton backButtonToSetting;
    private ImageView lieferlogoImageView;

    public OwnerInformationFragment()
    {
    }

    /**
     * Inflates the fragment layout and initializes UI components.
     *
     * @param inflater           Used to inflate the layout.
     * @param container          The parent view.
     * @param savedInstanceState State information.
     * @return A view representing the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_owner_inforamtion, container, false);

        informationTitleTextView = view.findViewById(R.id.informationTitleTextView);
        generalsTextView = view.findViewById(R.id.generalsTextView);
        groupTextView = view.findViewById(R.id.groupTextView);
        copyrightTextView = view.findViewById(R.id.copyrightTextView);
        backButtonToSetting = view.findViewById(R.id.backButtonToSetting);
        lieferlogoImageView = view.findViewById(R.id.lieferlogoImageView);

        backButtonToSetting.setOnClickListener(v ->
        {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentManagerHelper.goBackToPreviousFragment(fragmentManager);
        });
        return view;
    }
}
