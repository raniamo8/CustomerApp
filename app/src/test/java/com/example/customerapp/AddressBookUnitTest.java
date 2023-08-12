package com.example.customerapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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

    /**
     * Checks if adding a new recipient works correctly.
     */
    @Test
    public void testAddRecipient() {
        addressBook.addRecipient(recipient, context);
        assertEquals(1, addressBook.getRecipients().size());
        assertTrue(addressBook.getRecipients().contains(recipient));
    }

    /**
     * Checks if adding a null recipient does not affect the recipients list.
     */
    @Test
    public void testAddNullRecipient() {
        addressBook.addRecipient(null, context);
        assertEquals(0, addressBook.getRecipients().size());
    }

    /**
     * Checks if adding a recipient with empty names does not affect the recipients list.
     */
    @Test
    public void testAddRecipientWithEmptyNames() {
        Recipient emptyRecipient = new Recipient("", "");
        addressBook.addRecipient(emptyRecipient, context);
        assertEquals(0, addressBook.getRecipients().size());
    }

    /**
     * Checks if adding the same recipient twice does not duplicate it in the recipients list.
     */
    @Test
    public void testAddSameRecipientTwice() {
        addressBook.addRecipient(recipient, context);
        addressBook.addRecipient(recipient, context);
        assertEquals(1, addressBook.getRecipients().size());
    }

    /**
     * Checks if the QR code counter increments correctly when adding a new recipient.
     */
    public void testQRCodeCounterIncrement() {
        Recipient validRecipient = new Recipient("Slim", "Shady");
        addressBook.addRecipient(validRecipient, context);
        assertEquals(2, AddressBook.getQRCodeCounter(context));
        addressBook.addRecipient(validRecipient, context);
        assertEquals(2, AddressBook.getQRCodeCounter(context));
    }

    /**
     * Checks if deleting one recipient works correctly.
     */
    @Test
    public void testDeleteOneRecipient() {
        addressBook.addRecipient(recipient, context);
        addressBook.deleteOneRecipient(recipient, context);
        assertEquals(addressBook.getRecipients().size(), 0);
    }

    /**
     * Checks if deleting a non-existing recipient does not affect the recipients list.
     */
    @Test
    public void testDeleteOneRecipientNonExistingRecipient() {
        addressBook.deleteOneRecipient(recipient, context);
        assertEquals(0, addressBook.getRecipients().size());
    }

    /**
     * Checks if deleting a null recipient does not affect the recipients list.
     */
    @Test
    public void testDeleteOneRecipientNullRecipient() {
        addressBook.addRecipient(recipient, context);
        addressBook.deleteOneRecipient(null, context); // Should not remove the existing recipient
        assertEquals(1, addressBook.getRecipients().size());
    }

    /**
     * Checks if deleting a recipient from an empty list does not affect the QR code counter.
     */
    @Test
    public void testDeleteOneRecipientListIsEmpty() {
        addressBook.deleteOneRecipient(recipient, context); // List is already empty
        assertEquals(0, addressBook.getRecipients().size());
    }

    @Test
    public void testDeleteOneRecipientQRCodeCounterDecrement() {
        Recipient recipient1 = new Recipient("TestFirstName1", "TestLastName1");
        Recipient recipient2 = new Recipient("TestFirstName2", "TestLastName2");

        // Sie sollten dem Empfänger eine Adresse hinzufügen, damit der QRCodeCounter dekrementiert wird
        Address address = new Address("TestAddress", "2", "49808");
        recipient1.addAddress(address);
        addressBook.addRecipient(recipient, context);
        addressBook.addRecipient(recipient1, context);
        addressBook.addRecipient(recipient2, context);

        int initialCounter = AddressBook.getQRCodeCounter(context);

        addressBook.deleteOneRecipient(recipient1, context);

        int finalCounter = AddressBook.getQRCodeCounter(context);

        assertEquals(initialCounter - 1, finalCounter);
    }

    /**
     * Checks if deleting one recipient resets the QR code counter when necessary.
     */
    @Test
    public void testDeleteOneRecipientQRCodeCounterReset() {
        Recipient recipient = new Recipient("TestFirstName", "TestLastName");
        addressBook.addRecipient(recipient, context);
        addressBook.deleteOneRecipient(recipient, context);
        assertEquals(0, AddressBook.getQRCodeCounter(context));
    }

    /**
     * Checks if deleting all recipients works correctly.
     */
    @Test
    public void testDeleteAllRecipients() {
        addressBook.addRecipient(recipient, context);
        addressBook.addRecipient(new Recipient("SecondTestFirstName", "SecondTestLastName"), context);
        assertEquals(2, addressBook.getRecipients().size());
        addressBook.deleteAllRecipients(context);
        assertEquals(0, addressBook.getRecipients().size());
        assertFalse(addressBook.getRecipients().contains(recipient));
    }

    /**
     * Checks if deleting all recipients resets the QR code counter.
     */
    @Test
    public void testDeleteAllRecipientsResetQRCodeCounter() {
        Recipient recipient1 = new Recipient("TestFirstName1", "TestLastName1");
        Recipient recipient2 = new Recipient("TestFirstName2", "TestLastName2");
        addressBook.addRecipient(recipient1, context);
        addressBook.addRecipient(recipient2, context);
        assertEquals(2, addressBook.getRecipients().size());
        AddressBook.setQRCodeCounter(context, 5);
        addressBook.deleteAllRecipients(context);
        assertEquals(0, addressBook.getRecipients().size());
        assertEquals(0, AddressBook.getQRCodeCounter(context));
    }

    /**
     * Checks if loading data from shared preferences works correctly.
     */
    @Test
    public void testLoadDataAndSaveData() {
        addressBook.addRecipient(recipient, context);
        addressBook.saveData(context);
        addressBook.getRecipients().clear();
        assertEquals(addressBook.getRecipients().size(), 0);
        addressBook.loadData(context);
        assertEquals(addressBook.getRecipients().size(), 1);
    }

    /**
     * Checks if loading data with no data in shared preferences results in an empty recipients list.
     */
    @Test
    public void testLoadDataWithNoData() {
        addressBook.loadData(context);
        assertEquals(0, addressBook.getRecipients().size());
    }

    /**
     * Checks if loading data with null data in shared preferences results in an empty recipients list.
     */
    @Test
    public void testLoadDataWithNullData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("address_book_prefs_key", null);
        editor.apply();
        addressBook.loadData(context);
        assertEquals(0, addressBook.getRecipients().size());
    }

    /**
     * Checks if loading data with empty data in shared preferences results in an empty recipients list.
     */
    @Test
    public void testLoadDataWithEmptyData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("address_book_prefs_key", "");
        editor.apply();
        addressBook.loadData(context);
        assertEquals(0, addressBook.getRecipients().size());
    }

    /**
     * Checks if setting and getting the QR code counter works correctly.
     */
    @Test
    public void testSetAndGetQRCodeCounter() {
        int testCounter = 5;
        AddressBook.setQRCodeCounter(context, testCounter);
        assertEquals(AddressBook.getQRCodeCounter(context), testCounter);
    }

    /**
     * Checks if incrementing the QR code counter works correctly.
     */
    @Test
    public void testSetAndGetQRCodeIncrement() {
        int initialCounter = AddressBook.getQRCodeCounter(context);
        AddressBook.setQRCodeCounter(context, initialCounter + 1);
        int incrementedCounter = AddressBook.getQRCodeCounter(context);
        assertEquals(initialCounter + 1, incrementedCounter);
    }

}