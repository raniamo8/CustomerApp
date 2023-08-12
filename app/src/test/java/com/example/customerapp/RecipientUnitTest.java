package com.example.customerapp;

import static com.example.customerapp.Recipient.decodeQR;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
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

    @Test
    public  void testAddAddress(){
        recipient.addAddress(address);
        assertEquals(1, recipient.getAddresses().size());
    }

    @Test
    public  void testAddNullAddress(){
        recipient.addAddress(null);
        assertEquals(0, recipient.getAddresses().size());
    }


    @Test
    public  void testRemoveAddress(){
        recipient.addAddress(address);
        recipient.removeAddress(address);
        assertEquals(0, recipient.getAddresses().size());
    }

    @Test
    public  void testGenerateQRCode(){
        recipient.addAddress(address);
        Bitmap bitmap = recipient.generateQRCode();
        assertNotNull(bitmap);
    }

    @Test
    public void testGenerateQRCodeWithMock(){
        Recipient mockRecipient = mock(Recipient.class);
        when(mockRecipient.generateQRCode()).thenReturn(mock(Bitmap.class));
        assertNotNull(mockRecipient.generateQRCode());
    }

    @Test
    public void testGenerateQRCodeStringFormat(){
        recipient.addAddress(address);
        Bitmap bitmap = recipient.generateQRCode();
        String result = Recipient.decodeQR(bitmap);
        assertTrue(result.contains("&"));
    }

    @Test
    public void testGenerateQRCodeIncompleteValues(){
        Recipient incompleteRecipient = new Recipient(null, "TestFirstName");
        incompleteRecipient.addAddress(address);
        Bitmap bitmap = incompleteRecipient.generateQRCode();
        assertNull(bitmap);
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