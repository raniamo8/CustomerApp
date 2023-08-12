package com.example.customerapp;

import android.graphics.Bitmap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class RecipientUnitTest {

    private Recipient recipient;
    private Address address;
    private List<Address> addresses;

    @Before
    public void setUp() {
        recipient = new Recipient("TestLastName", "TestFirstName");
        address = new Address("TestStreet", "2", "49808");
        addresses = new ArrayList<>();
    }

    /**
     * Checks if an address can be added to the recipient's address list.
     */
    @Test
    public  void testAddAddress(){
        recipient.addAddress(address);
        Assert.assertEquals(1, recipient.getAddresses().size());
    }

    /**
     * Checks if adding a null address does not alter the recipient's address list.
     */
    @Test
    public  void testAddNullAddress(){
        recipient.addAddress(null);
        Assert.assertEquals(0, recipient.getAddresses().size());
    }

    /**
     * Checks if recipient can have more than one address even if it is duplicate
     */
    @Test
    public void testAddDuplicateAddress() {
        recipient.addAddress(address);
        recipient.addAddress(address);
        Assert.assertEquals(2, recipient.getAddresses().size());
    }

    /**
     * Checks if an address can be removed from the recipient's address list.
     */
    @Test
    public  void testRemoveAddress(){
        recipient.addAddress(address);
        recipient.removeAddress(address);
        Assert.assertEquals(0, recipient.getAddresses().size());
    }

    /**
     * Checks if removing an address that isn't in the list keeps the list unchanged.
     */
    @Test
    public void testRemoveNonExistentAddress() {
        Address anotherAddress = new Address("AnotherStreet", "5", "12345");
        recipient.addAddress(address);
        recipient.removeAddress(anotherAddress);
        Assert.assertEquals(1, recipient.getAddresses().size());
    }

    /**
     * Checks if a QR code can be generated when an address is added to the recipient.
     */
    @Test
    public  void testGenerateQRCode(){
        recipient.addAddress(address);
        Bitmap bitmap = recipient.generateQRCode();
        Assert.assertNotNull(bitmap);
    }

    /**
     * Checks if no QRCode is generated when no address has been added.
     */
    @Test
    public void testGenerateQRCodeWithoutAddress() {
        Bitmap bitmap = recipient.generateQRCode();
        Assert.assertNull(bitmap);
    }

    /**
     * Ensures consistency between encoding to QR code and decoding back.
     */
    @Test
    public void testEncodeDecodeConsistency() {
        recipient.addAddress(address);
        Bitmap bitmap = recipient.generateQRCode();
        String decodedResult = Recipient.decodeQR(bitmap);
        Assert.assertTrue(decodedResult.contains("TestStreet"));
        Assert.assertTrue(decodedResult.contains("2"));
        Assert.assertTrue(decodedResult.contains("49808"));
    }

    /**
     * Checks if the generated QR code string format contains the ampersand (&) character.
     */
    @Test
    public void testGenerateQRCodeStringFormat(){
        recipient.addAddress(address);
        Bitmap bitmap = recipient.generateQRCode();
        String result = Recipient.decodeQR(bitmap);
        Assert.assertTrue(result.contains("&"));
    }

    /**
     * Checks if a QR code generation fails when recipient has incomplete values.
     */
    @Test
    public void testGenerateQRCodeIncompleteValues(){
        Recipient incompleteRecipient = new Recipient(null, "TestFirstName");
        incompleteRecipient.addAddress(address);
        Bitmap bitmap = incompleteRecipient.generateQRCode();
        Assert.assertNull(bitmap);
    }

    /*
    @Test
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void testsaveQRCodeToInternalStorage(){
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "qr_codes");
        tempDir.mkdir();
        Context mockContext = mock(Context.class);
        when(mockContext.getDir(anyString(), anyInt())).thenReturn(mock(File.class));
        System.out.println("Mocked File Path: " + tempDir.getAbsolutePath());
        recipient.addAddress(address);
        assertTrue(recipient.saveQRCodeToInternalStorage(mockContext));
        tempDir.delete();
    }
    */


}