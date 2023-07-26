package com.example.customerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;



public class Recipient {


    private static int qrCodeCounter = 1;
    private static final int WIDTH_HEIGHT_NR = 400;

    private String firstName;
    private String lastName;
    private List<Address> addresses;

    public Recipient(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = new ArrayList<>();
        this.qrCodeCounter = 0;
    }

    public int getQRCodeCounter() {
        return qrCodeCounter;
    }

    public void setQRCodeCounter(int qrCodeCounter) {
        this.qrCodeCounter = qrCodeCounter;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
    }

    public Bitmap generateQRCode() {
        if (firstName == null || lastName == null || addresses.isEmpty()) {
            Log.e("Recipient", "Cannot generate QR code. Incomplete recipient information.");
            return null;
        }

        Address address = addresses.get(0);
        String text = lastName + "&" + firstName + "&" + address.getStreet() + "&"
                + address.getStreetNr()+ "&" + "Lingen" + "&" + address.getPlz();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, WIDTH_HEIGHT_NR, WIDTH_HEIGHT_NR);
            BarcodeEncoder encoder = new BarcodeEncoder();
            return encoder.createBitmap(matrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean saveQRCodeToInternalStorage(Context context) {
        Bitmap qrCodeBitmap = generateQRCode();
        if (qrCodeBitmap == null) {
            return false;
        }

        try {
            File directory = context.getDir("qr_codes", Context.MODE_PRIVATE);
            String fileName = "qr_code" + qrCodeCounter + ".png";
            File file = new File(directory, fileName);
            OutputStream outputStream = Files.newOutputStream(file.toPath());
            qrCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Log.d("Recipient", "QR code saved to: " + file.getAbsolutePath());
            qrCodeCounter++;

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}