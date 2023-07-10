package com.example.customerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class CodeFragment extends Fragment {

    private EditText personName;

    private Button buttonGenerate;

    private ImageView qrCode;
    public CodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    /*
    public static CodeFragment newInstance(String param1, String param2) {
        CodeFragment fragment = new CodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code, container, false);

        personName = view.findViewById(R.id.PersonName);
        buttonGenerate = view.findViewById(R.id.button_generate);
        qrCode = view.findViewById(R.id.qr_code);

        buttonGenerate.setOnClickListener(v -> {
            String name = personName.getText().toString().trim();
            Recipient recipient = new Recipient(name);
            recipient.sendPost();
            recipient.generateQRCode(qrCode);
        });

        return view;
    }
}