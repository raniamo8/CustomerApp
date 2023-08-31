package customerapp.fragments.customerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.customerapp.R;

import customerapp.adapters.cutsomerapp.QRCodeAdapter;
import customerapp.models.customerapp.FragmentManagerHelper;
import customerapp.models.customerapp.AddressBook;

/**
 * A fragment that allows users to manage app settings, including enabling or disabling dark mode,
 * adding addresses to recipients, and deleting all QR codes and recipients.
 */
public class SettingFragment extends Fragment {
    Button deleteAllButton, addAddressButton;
    ImageButton informationButton;
    private QRCodeAdapter qrCodeAdapter;
    private AddressBook addressBook;

    public SettingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressBook = AddressBook.getInstance();
        addressBook.loadData(getContext());
        qrCodeAdapter = new QRCodeAdapter(getContext());
        setHasOptionsMenu(true);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        addressBook = AddressBook.getInstance();

        addAddressButton = view.findViewById(R.id.addAddressButton);
        view.findViewById(R.id.addAddressButton).setOnClickListener(v -> goToCodeFragment());

        deleteAllButton = view.findViewById(R.id.deleteAllButton);
        deleteAllButton.setOnClickListener(v -> deleteAllQRandRecipients());

        informationButton = view.findViewById(R.id.informationButton);
        informationButton.setOnClickListener(view1 -> goToOwnerInformationFragment());

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteAllQRandRecipients() {
        if(addressBook.getRecipients().isEmpty()) {
            Toast.makeText(getContext(), "Es gibt keine QR-Codes zum Löschen.", Toast.LENGTH_SHORT).show();
        } else {
            addressBook.deleteAllRecipients(getContext());
            qrCodeAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Alle QR-Codes wurden gelöscht.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToCodeFragment() {
        FragmentManagerHelper.goToFragment(
                requireActivity().getSupportFragmentManager(),
                R.id.frame_layout,
                new CodeFragment(),
                R.anim.slide_in_right,
                R.anim.slide_out,
                true
        );
    }

    private void goToOwnerInformationFragment() {
        FragmentManagerHelper.goToFragment(
                requireActivity().getSupportFragmentManager(),
                R.id.frame_layout,
                new OwnerInformationFragment(),
                R.anim.slide_in_right,
                R.anim.slide_out,
                true
        );
    }

}
