package customerapp.unittest.customerapp;

import android.graphics.Bitmap;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import customerapp.models.customerapp.Address;
import customerapp.models.customerapp.Recipient;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O)
public class QRCodePerformanceUnitTest {
    private static final int TEST_COUNT = 10000;

    @Test
    public void testQRCodeCreationStress() {
        Context context = ApplicationProvider.getApplicationContext();
        long startTime = System.currentTimeMillis();
        int successfulQRCodes = 0;

        for (int i = 0; i < TEST_COUNT; i++) {
            Recipient recipient = new Recipient("TestNachname" + i, "TestVorname" + i);
            recipient.addAddress(new Address("Teststraße", String.valueOf(i), "49808"));

            Bitmap qrCodeBitmap = recipient.generateQRCode();
            if (qrCodeBitmap != null) {
                successfulQRCodes++;
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        Log.d("QRCodeStressTest", "Zeit für die Erstellung von " + TEST_COUNT + " QR-Codes: " + duration + "ms");
        assertEquals(TEST_COUNT, successfulQRCodes);
    }

    @Test
    public void testQRCodeCreationAndSaveStress() {
        Context context = ApplicationProvider.getApplicationContext();
        long startTime = System.currentTimeMillis();
        int successfulQRCodes = 0;

        for (int i = 0; i < TEST_COUNT; i++) {
            Recipient recipient = new Recipient("TestNachname" + i, "TestVorname" + i);
            recipient.addAddress(new Address("Teststraße", String.valueOf(i), "49808"));

            Bitmap qrCodeBitmap = recipient.generateQRCode();
            assertNotNull(qrCodeBitmap);
            if (recipient.saveQRCodeToInternalStorage(context)) {
                successfulQRCodes++;
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        Log.d("QRCodeStressTest", "Zeit für die Erstellung von " + TEST_COUNT + " QR-Codes: " + duration + "ms");
        assertEquals(TEST_COUNT, successfulQRCodes);
    }

}
