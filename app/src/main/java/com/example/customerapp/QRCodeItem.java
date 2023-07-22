package com.example.customerapp;

import android.graphics.Bitmap;

public class QRCodeItem {
    private Bitmap qrCodeBitmap;

    public QRCodeItem(Bitmap qrCodeBitmap) {
        this.qrCodeBitmap = qrCodeBitmap;
    }

    public Bitmap getQrCodeBitmap() {
        return qrCodeBitmap;
    }

    public void setQrCodeBitmap(Bitmap qrCodeBitmap) {
        this.qrCodeBitmap = qrCodeBitmap;
    }
}
