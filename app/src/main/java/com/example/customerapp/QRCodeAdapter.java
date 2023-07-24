package com.example.customerapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class QRCodeAdapter extends RecyclerView.Adapter<QRCodeAdapter.QRCodeViewHolder> {

    private List<String> qrCodeFilePaths;
    private Context context;

    public QRCodeAdapter(Context context, List<String> qrCodeFilePaths) {
        this.context = context;
        this.qrCodeFilePaths = qrCodeFilePaths;
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

        holder.buttonOpenQRCode.setOnClickListener(v -> {
            openQRCodeDisplayActivity(v.getContext(), position);
        });

        holder.deleteButton.setOnClickListener(v -> {
            deleteQRCode(position);
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

        public QRCodeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewQrCode = itemView.findViewById(R.id.imageViewQrCode);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            buttonOpenQRCode = itemView.findViewById(R.id.buttonOpenQRCode);
        }
    }


    //TODO: integration of the class AddressBook!
    private void deleteQRCode(int position) {
        String filePath = qrCodeFilePaths.get(position);
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                qrCodeFilePaths.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, qrCodeFilePaths.size());
                Toast.makeText(context, "QR-Code gelöscht.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Fehler beim Löschen des QR-Codes.", Toast.LENGTH_SHORT).show();
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



