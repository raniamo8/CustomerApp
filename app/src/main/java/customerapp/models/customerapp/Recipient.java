package customerapp.models.customerapp;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.Serializable;

/**
 * Represents a recipient with its information.
 * The recipient can generate a QR code based on their information, and save it to the internal storage of the app.
 */
public class Recipient implements Serializable
{
    private String firstName;
    private String lastName;
    private Address address;

    /**
     * Constructor to initialize a new Recipient.
     *
     * @param lastName  The last name of the recipient.
     * @param firstName The first name of the recipient.
     * @param address   The address of the recipient.
     */
    public Recipient(String lastName, String firstName, Address address)
    {
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    /**
     * Converts the recipient details into a string suitable for QR code generation.
     *
     * @return A concatenated string of recipient details.
     */
    public String toQrString()
    {
        return firstName + "&" + lastName + "&" + address.getStreet() + "&" + address.getStreetNr() + "&" + address.getZip() + "&" + address.getCity();
    }

    /**
     * Generates a QR code based on the recipient's details and the string representation.
     *
     * @return A Bitmap representation of the QR code.
     */
    public Bitmap generateQRCode()
    {
        String qrString = toQrString();
        MultiFormatWriter writer = new MultiFormatWriter();
        try
        {
            BitMatrix matrix = writer.encode(qrString, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder encoder = new BarcodeEncoder();
            return encoder.createBitmap(matrix);
        } catch (WriterException e)
        {
            Log.e("Recipient", "Error generating QR code: " + e.getMessage());
            return null;
        }
    }
}
