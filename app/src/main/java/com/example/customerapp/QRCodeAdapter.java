package com.example.customerapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QRCodeAdapter extends RecyclerView.Adapter<QRCodeAdapter.QRCodeViewHolder> {

    private List<String> qrCodeFilePaths;

    public QRCodeAdapter(List<String> qrCodeFilePaths) {
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
    }

    @Override
    public int getItemCount() {
        return qrCodeFilePaths.size();
    }

    static class QRCodeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewQrCode;

        public QRCodeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewQrCode = itemView.findViewById(R.id.imageViewQrCode);
        }
    }
}



