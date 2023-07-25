package com.example.customerapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AddressBookUnitTest {
    private AddressBook addressBook;
    private Recipient recipient;
    private Context context;
    private SharedPreferences sharedPreferences;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application.getApplicationContext();
        addressBook = AddressBook.getInstance();
        addressBook.reset();
        recipient = new Recipient("TestFirstName", "TestLastName");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().clear().commit();
    }


    @Test
    public void testAddRecipient() {
        addressBook.addRecipient(recipient, context);
        assertEquals(1, addressBook.getRecipients().size());
        assertTrue(addressBook.getRecipients().contains(recipient));
    }

    @Test
    public void testDeleteOneRecipient() {
        addressBook.addRecipient(recipient, context);
        addressBook.deleteOneRecipient(recipient, context);
        assertEquals(addressBook.getRecipients().size(), 0);
    }

    @Test
    public void testLoadDataAndSaveData() {
        addressBook.addRecipient(recipient, context);
        addressBook.saveData(context);
        addressBook.getRecipients().clear();
        assertEquals(addressBook.getRecipients().size(), 0);
        addressBook.loadData(context);
        assertEquals(addressBook.getRecipients().size(), 1);
    }

    @Test
    public void testDeleteAllRecipients() {
        addressBook.addRecipient(recipient, context);
        addressBook.addRecipient(new Recipient("SecondTestFirstName", "SecondTestLastName"), context);
        assertEquals(2, addressBook.getRecipients().size());
        addressBook.deleteAllRecipients(context);
        assertEquals(0, addressBook.getRecipients().size());
        assertFalse(addressBook.getRecipients().contains(recipient));
    }

    @Test
    public void testSetAndGetQRCodeCounter() {
        int testCounter = 5;
        AddressBook.setQRCodeCounter(context, testCounter);
        assertEquals(AddressBook.getQRCodeCounter(context), testCounter);
    }

}