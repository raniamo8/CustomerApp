package com.example.customerapp;

public class QRCodeItem {
    private String qrCodeFilePath;
    private String recipientName;

    public QRCodeItem(String qrCodeFilePath, String recipientName) {
        this.qrCodeFilePath = qrCodeFilePath;
        this.recipientName = recipientName;
    }

    public String getQrCodeFilePath() {
        return qrCodeFilePath;
    }

    public String getRecipientName() {
        return recipientName;
    }
}