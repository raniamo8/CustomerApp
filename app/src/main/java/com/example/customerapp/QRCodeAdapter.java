package com.example.customerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.QRCodeItem;
import com.example.customerapp.R;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class QRCodeAdapter extends RecyclerView.Adapter<QRCodeAdapter.QRCodeViewHolder> {

    private List<QRCodeItem> qrCodeItems;

    public QRCodeAdapter(List<QRCodeItem> qrCodeItems) {
        this.qrCodeItems = qrCodeItems;
    }

    @NonNull
    @Override
    public QRCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qr_code, parent, false);
        return new QRCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QRCodeViewHolder holder, int position) {
        QRCodeItem qrCodeItem = qrCodeItems.get(position);
        holder.textViewContent.setText(qrCodeItem.getContent());
    }

    @Override
    public int getItemCount() {
        return qrCodeItems.size();
    }

    static class QRCodeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent;

        public QRCodeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.textViewContent);
        }
    }
}

