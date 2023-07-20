package com.example.customerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;



public class Recipient {
    private static final int WIDTH_HEIGHT_NR = 400;

    private String firstName;
    private String lastName;
    private List<Address> addresses;

    public Recipient(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = new ArrayList<>();
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

    public Bitmap generateQRCode() {
        if (firstName == null || lastName == null || addresses.isEmpty()) {
            Log.e("Recipient", "Cannot generate QR code. Incomplete recipient information.");
            return null;
        }

        Address address = addresses.get(0);
        String text = lastName + " " + firstName + "\n" + address.getStreet() + " " + address.getStreetNr() + "\n" + address.getPlz();
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

    public boolean saveQRCodeToInternalStorage(Context context) {
        Bitmap qrCodeBitmap = generateQRCode();

        if (qrCodeBitmap == null) {
            return false;
        }

        try {
            File directory = context.getDir("qr_codes", Context.MODE_PRIVATE);
            File file = new File(directory, "qr_code.png");

            OutputStream outputStream = new FileOutputStream(file);
            qrCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Log.d("Recipient", "QR code saved to: " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
