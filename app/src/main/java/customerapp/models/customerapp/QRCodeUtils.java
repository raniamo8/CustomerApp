package customerapp.utils;

import android.content.Context;
import android.widget.Toast;

import customerapp.models.customerapp.AddressBook;

public class QRCodeUtils {
    public static void deleteAllQRandRecipients(Context context, AddressBook addressBook) {
        if (addressBook.getRecipients().isEmpty()) {
            Toast.makeText(context, "Es gibt keine QR-Codes zum Löschen.", Toast.LENGTH_SHORT).show();
        } else {
            addressBook.deleteAllRecipients(context);
            Toast.makeText(context, "Alle QR-Codes wurden gelöscht.", Toast.LENGTH_SHORT).show();
        }
    }
}
