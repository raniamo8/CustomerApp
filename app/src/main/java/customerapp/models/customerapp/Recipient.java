package customerapp.models.customerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

/**
 * Represents a recipient with its information.
 * The recipient can generate a QR code based on their information, and save it to the internal storage of the app.
 */
public class Recipient implements Serializable {
    private String firstName;
    private String lastName;
    private Address address;

    public Recipient(String lastName, String firstName, Address address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String toQrString() {
        return lastName + "&" + firstName + "&" + address.getStreet() + "&" + address.getStreetNr() + "&" + address.getZip() + "&" + address.getCity();
    }

    public Bitmap generateQRCode() {
        String qrString = toQrString();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(qrString, BarcodeFormat.QR_CODE, 500, 500); // size can be adjusted based on your needs
            BarcodeEncoder encoder = new BarcodeEncoder();
            return encoder.createBitmap(matrix);
        } catch (WriterException e) {
            Log.e("Recipient", "Error generating QR code: " + e.getMessage());
            return null;
        }
    }
}