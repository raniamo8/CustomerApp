package customerapp.fragments.customerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import customerapp.adapters.cutsomerapp.QRCodeAdapter;
import customerapp.models.customerapp.AddressBook;
import customerapp.models.customerapp.FragmentManagerHelper;
import customerapp.models.customerapp.SwipeToDeleteCallback;

/**
 * Represents a fragment that displays a list of QR codes generated for recipients.
 * This fragment allows the user to view and manage the list of QR codes and associated recipients.
 */
public class QRCodeListFragment extends Fragment {
    private AddressBook addressBook;
    private RecyclerView recyclerViewQRCodeList;
    private QRCodeAdapter qrCodeAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressBook = AddressBook.getInstance();
        addressBook.loadData(getContext());
        qrCodeAdapter = new QRCodeAdapter(getContext());
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code_list, container, false);
        addressBook = AddressBook.getInstance();

        recyclerViewQRCodeList = view.findViewById(R.id.recyclerViewQRCodeList);
        recyclerViewQRCodeList.setAdapter(qrCodeAdapter);
        recyclerViewQRCodeList.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fabAddQRCode = view.findViewById(R.id.fabAddQRCode);
        fabAddQRCode.setOnClickListener(v -> navigateToCodeFragment());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(qrCodeAdapter, getContext()));
        itemTouchHelper.attachToRecyclerView(recyclerViewQRCodeList);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        qrCodeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_qr_code_list, menu);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_all_recipients) {
            if(addressBook.getRecipients().isEmpty()) {
                Toast.makeText(getContext(), "Es gibt keine QR-Codes zum Löschen.", Toast.LENGTH_SHORT).show();
            } else {
                addressBook.deleteAllRecipients(getContext());
                qrCodeAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Alle QR-Codes wurden gelöscht.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToCodeFragment() {
        FragmentManagerHelper.goToFragment(
                requireActivity().getSupportFragmentManager(),
                R.id.frame_layout,
                new CodeFragment(),
                R.anim.slide_in,
                R.anim.slide_out,
                true
        );
    }


}


