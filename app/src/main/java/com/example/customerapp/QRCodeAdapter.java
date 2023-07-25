package com.example.customerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class QRCodeAdapter extends RecyclerView.Adapter<QRCodeAdapter.QRCodeViewHolder> {

    private List<String> qrCodeFilePaths;
    private Context context;
    private AddressBook addressBook;


    public QRCodeAdapter(Context context, List<String> qrCodeFilePaths, AddressBook addressBook) {
        this.context = context;
        this.qrCodeFilePaths = qrCodeFilePaths;
        this.addressBook = addressBook;
    }


    @NonNull
    @Override
    public QRCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qr_code, parent, false);
        return new QRCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QRCodeViewHolder holder, int position) {
        String filePath = qrCodeFilePaths.get(position);
        Bitmap qrCodeBitmap = BitmapFactory.decodeFile(filePath);
        holder.imageViewQrCode.setImageBitmap(qrCodeBitmap);
        setRecipientInfo(holder, position);

        holder.buttonOpenQRCode.setOnClickListener(v -> {
            openQRCodeDisplayActivity(v.getContext(), position);
        });

        holder.deleteButton.setOnClickListener(v -> {
            deleteQRCodeAndRecipient(position);
        });
    }

    @Override
    public int getItemCount() {
        return qrCodeFilePaths.size();
    }

    static class QRCodeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewQrCode;
        Button deleteButton;
        Button buttonOpenQRCode;
        TextView textViewRecipientInfo;

        public QRCodeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewQrCode = itemView.findViewById(R.id.imageViewQrCode);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            buttonOpenQRCode = itemView.findViewById(R.id.buttonOpenQRCode);
            textViewRecipientInfo = itemView.findViewById(R.id.textViewRecipientInfo);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setRecipientInfo(@NonNull QRCodeViewHolder holder, int position) {
        if (addressBook != null && position < addressBook.getRecipients().size()) {
            Recipient recipient = addressBook.getRecipients().get(position);
            String fullName = recipient.getFirstName() + " " + recipient.getLastName();
            holder.textViewRecipientInfo.setText(fullName);
            Log.d("QRCodeAdapter", "Recipient at position " + position + ": " + fullName);
        } else {
            holder.textViewRecipientInfo.setText("Unknown Recipient position problem");
        }
    }


    private void deleteQRCodeAndRecipient(int position) {
        Recipient recipient = addressBook.getRecipients().get(position);
        if (recipient != null) {
            String filePath = qrCodeFilePaths.get(position);
            File file = new File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    qrCodeFilePaths.remove(position);
                    Address addressToRemove = recipient.getAddresses().get(0);
                    if (recipient.getAddresses().contains(addressToRemove)) {
                        Log.d("AddressBook", "Adresse vor dem Löschen: " + addressToRemove.toString());
                    } else {
                        Log.d("AddressBook", "Adresse nicht gefunden, bevor sie entfernt wird.");
                    }
                    addressBook.deleteOneRecipient(recipient, context);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, qrCodeFilePaths.size());
                    Toast.makeText(context, "QR-Code und Recipient gelöscht.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Fehler beim Löschen des QR-Codes.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void openQRCodeDisplayActivity(Context context, int position) {
        if (position >= 0 && position < qrCodeFilePaths.size()) {
            String filePath = qrCodeFilePaths.get(position);
            Intent intent = new Intent(context, QRCodeDisplayActivity.class);
            intent.putExtra("qrCodeFilePath", filePath);
            context.startActivity(intent);
        }
    }

}