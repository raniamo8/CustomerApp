package customerapp.unittest.customerapp;

import android.graphics.Bitmap;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import static org.junit.Assert.*;

import java.io.Console;

import customerapp.models.customerapp.Address;
import customerapp.models.customerapp.Recipient;

@RunWith(RobolectricTestRunner.class)
public class QRCodePerformanceUnitTest {

    private static final int AMOUNT = 1000;
    private static final String TAG = "QRCodePerformanceTest";
    private Recipient testRecipient;

    @Before
    public void setUp() {
        Address testAddress = new Address("TestStreet", "123", "45678", "TestCity");
        testRecipient = new Recipient("TestFirst", "TestLast", testAddress);
    }

    @Test
    public void testGenerateQRCodePerformance() {
        long totalDuration = 0;

        for (int i = 0; i < AMOUNT; i++) {
            long startTime = System.currentTimeMillis();

            Bitmap qrCode = testRecipient.generateQRCode();

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            totalDuration += duration;

            assertNotNull(qrCode);
        }

        long averageDuration = totalDuration / AMOUNT;
        System.out.println("Average time to generate QR code: " + averageDuration + " milliseconds");
        System.out.println("Total time to generate " + AMOUNT + " QR codes: " + totalDuration + " milliseconds");
    }
}
