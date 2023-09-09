package customerapp.unittest.customerapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import customerapp.models.customerapp.Address;
import customerapp.models.customerapp.Recipient;

@RunWith(RobolectricTestRunner.class)
public class RecipientUnitTest {
    private Recipient recipientSlimShady;
    private Recipient recipientWithNullFields;
    private Recipient recipientWithEmptyFields;
    private Recipient recipientWithMixedFields;

    @Mock
    private MultiFormatWriter mockWriter;

    @Mock
    private BarcodeEncoder mockEncoder;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Address nullAddress = new Address(null, null, null, null);
        Address emptyAddress = new Address("", "", "", "");
        Address mixedAddress = new Address("Elm Street", "", "11111", null);
        Address addressSlimShady = new Address("Kaiserstraße", "10c", "49808", "Lingen");

        recipientSlimShady = new Recipient("Shady", "Slim", addressSlimShady);
        recipientWithNullFields = new Recipient(null, null, nullAddress);
        recipientWithEmptyFields = new Recipient("", "", emptyAddress);
        recipientWithMixedFields = new Recipient("Doe", "", mixedAddress);
    }

    @Test
    public void testToQrString_SlimShady() {
        String expected = "Slim&Shady&Kaiserstraße&10c&49808&Lingen";
        assertEquals(expected, recipientSlimShady.toQrString());
    }

    @Test
    public void testToQrString_withNullFields() {
        String expected = "&&&&&";
        assertEquals(expected, recipientWithNullFields.toQrString());
    }

    @Test
    public void testToQrString_withEmptyFields() {
        String expected = "&&&&&";
        assertEquals(expected, recipientWithEmptyFields.toQrString());
    }

    @Test
    public void testToQrString_withMixedFields() {
        String expected = "&Doe&Elm Street&&11111&";
        assertEquals(expected, recipientWithMixedFields.toQrString());
    }

    @Test
    public void testToQrString_withOnlyCity() {
        Address address = new Address("", "", "", "Metropolis");
        Recipient recipient = new Recipient("", "", address);
        String expected = "&&&&&Metropolis";
        assertEquals(expected, recipient.toQrString());
    }

    @Test
    public void testGenerateQRCode_success() throws Exception {
        BitMatrix mockMatrix = new BitMatrix(500);
        when(mockWriter.encode(anyString(), eq(BarcodeFormat.QR_CODE), eq(500), eq(500))).thenReturn(mockMatrix);
        Bitmap mockBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        when(mockEncoder.createBitmap(mockMatrix)).thenReturn(mockBitmap);

        Bitmap result = recipientSlimShady.generateQRCode();
        assertNotNull(result);
    }


}
