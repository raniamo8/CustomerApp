package customerapp.unittest.customerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import customerapp.models.customerapp.Address;
import customerapp.models.customerapp.AddressBook;
import customerapp.models.customerapp.Recipient;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static customerapp.models.customerapp.AddressBook.PREF_RECIPIENTS_KEY;

@RunWith(RobolectricTestRunner.class)
public class AddressBookUnitTest {
    @Mock
    private Context mockContext;
    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockEditor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        AddressBook.getInstance().reset();
        when(mockContext.getSharedPreferences(mockContext.getPackageName() + "_preferences", Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
    }

    @Test
    public void testSingleton() {
        AddressBook firstInstance = AddressBook.getInstance();
        AddressBook secondInstance = AddressBook.getInstance();
        assertEquals(firstInstance, secondInstance);
    }

    @Test
    public void testAddRecipient() {
        AddressBook addressBook = AddressBook.getInstance();
        Recipient recipient = new Recipient("Macaroni", "Tony", new Address("Kaiserstr", "10C", "49808", "Lingen"));
        addressBook.addRecipient(recipient, mockContext);
        assertEquals(1, addressBook.getRecipients().size());
    }

    @Test
    public void testAddMultipleRecipients() {
        AddressBook addressBook = AddressBook.getInstance();
        Recipient recipient1 = new Recipient("Shady", "Slim", new Address("Kaiserstr", "10C", "49808", "Lingen"));
        Recipient recipient2 = new Recipient("Macaroni", "Tony", new Address("Kaiserstr", "10C", "49808", "Lingen"));
        addressBook.addRecipient(recipient1, mockContext);
        addressBook.addRecipient(recipient2, mockContext);
        Assert.assertEquals(2, addressBook.getRecipients().size());
    }

    @Test
    public void testAddDuplicateRecipient() {
        AddressBook addressBook = AddressBook.getInstance();
        Recipient recipient = new Recipient("Shady", "Slim", new Address("Kaiserstr", "10C", "49808", "Lingen"));
        addressBook.addRecipient(recipient, mockContext);
        addressBook.addRecipient(recipient, mockContext);
        assertEquals(2, addressBook.getRecipients().size());
    }

    @Test
    public void testStorageAfterAddingRecipient() {
        AddressBook addressBook = AddressBook.getInstance();
        Recipient recipient = new Recipient("Shady", "Slim", new Address("Kaiserstr", "10C", "49808", "Lingen"));
        addressBook.addRecipient(recipient, mockContext);
        verify(mockEditor, times(1)).putString(eq(PREF_RECIPIENTS_KEY), anyString());
        verify(mockEditor, times(1)).apply();
    }


    @Test
    public void testAddNullRecipient() {
        AddressBook addressBook = AddressBook.getInstance();
        int initialSize = addressBook.getRecipients().size();
        addressBook.addRecipient(null, mockContext);
        assertEquals(initialSize, addressBook.getRecipients().size());
    }


    @Test
    public void testDeleteOneRecipient() {
        AddressBook addressBook = AddressBook.getInstance();
        Recipient recipient = new Recipient("Shady", "Slim", new Address("Kaiserstr", "10C", "49808", "Lingen"));
        addressBook.addRecipient(recipient, mockContext);
        addressBook.deleteOneRecipient(0, mockContext);
        assertEquals(0, addressBook.getRecipients().size());
    }

    @Test
    public void testDeleteRecipientWithNegativeIndex() {
        AddressBook addressBook = AddressBook.getInstance();
        int initialSize = addressBook.getRecipients().size();
        addressBook.deleteOneRecipient(-1, mockContext);
        assertEquals(initialSize, addressBook.getRecipients().size());
    }

    @Test
    public void testDeleteRecipientWithNoRecipients() {
        AddressBook addressBook = AddressBook.getInstance();
        int initialSize = addressBook.getRecipients().size();
        addressBook.deleteOneRecipient(0, mockContext);
        assertEquals(initialSize, addressBook.getRecipients().size());
    }

    @Test
    public void testDeleteOneRecipientInvalidIndex() {
        AddressBook addressBook = AddressBook.getInstance();
        int initialSize = addressBook.getRecipients().size();
        addressBook.deleteOneRecipient(999, mockContext);
        assertEquals(initialSize, addressBook.getRecipients().size());
    }


    @Test
    public void testDeleteAllRecipients() {
        AddressBook addressBook = AddressBook.getInstance();
        addressBook.deleteAllRecipients(mockContext);
        assertEquals(0, addressBook.getRecipients().size());
    }

    @Test
    public void testDeleteAllRecipientsWithNonEmptyList() {
        AddressBook addressBook = AddressBook.getInstance();
        Recipient recipient = new Recipient("Shady", "Slim", new Address("Kaiserstr", "10C", "49808", "Lingen"));
        addressBook.addRecipient(recipient, mockContext);
        addressBook.deleteAllRecipients(mockContext);
        assertEquals(0, addressBook.getRecipients().size());
    }

    @Test
    public void testStorageAfterDeleteAllRecipients() {
        AddressBook addressBook = AddressBook.getInstance();
        Recipient recipient = new Recipient("Shady", "Slim", new Address("Kaiserstr", "10C", "49808", "Lingen"));
        addressBook.addRecipient(recipient, mockContext);
        addressBook.deleteAllRecipients(mockContext);
        verify(mockEditor, times(2)).putString(eq(PREF_RECIPIENTS_KEY), anyString());
        verify(mockEditor, times(2)).apply();
    }

    @Test
    public void testMultipleDeleteAllRecipients() {
        AddressBook addressBook = AddressBook.getInstance();
        Recipient recipient1 = new Recipient("Shady", "Slim", new Address("Kaiserstr", "10C", "49808", "Lingen"));
        Recipient recipient2 = new Recipient("Macaroni", "Tony", new Address("Kaiserstr", "10D", "49808", "Lingen"));
        addressBook.addRecipient(recipient1, mockContext);
        addressBook.addRecipient(recipient2, mockContext);
        addressBook.deleteAllRecipients(mockContext);
        assertEquals(0, addressBook.getRecipients().size());
        addressBook.addRecipient(recipient1, mockContext);
        addressBook.deleteAllRecipients(mockContext);
        assertEquals(0, addressBook.getRecipients().size());
    }


    @Test
    public void testDeleteAllRecipientsDoesNotAffectOtherPrefs() {
        AddressBook addressBook = AddressBook.getInstance();
        String unrelatedKey = "UNRELATED_KEY";
        String unrelatedValue = "UNRELATED_VALUE";
        when(mockSharedPreferences.getString(unrelatedKey, "")).thenReturn(unrelatedValue);
        addressBook.deleteAllRecipients(mockContext);
        verify(mockEditor, times(0)).remove(unrelatedKey);
    }


    @Test
    public void testLoadDataEmpty() {
        when(mockSharedPreferences.getString("PREF_RECIPIENTS_KEY", "")).thenReturn("");
        AddressBook addressBook = AddressBook.getInstance();
        addressBook.loadData(mockContext);
        assertEquals(0, addressBook.getRecipients().size());
    }

    @Test
    public void testLoadDataNotNull() {
        when(mockSharedPreferences.getString("PREF_RECIPIENTS_KEY", "")).thenReturn("[{\"lastName\":\"Macroni\",\"firstName\":\"Tony\",\"address\":{\"street\":\"Kaiserstr\",\"streetNr\":\"10C\",\"zip\":\"49808\",\"city\":\"Lingen\"}}]");
        AddressBook addressBook = AddressBook.getInstance();
        addressBook.loadData(mockContext);
        assertEquals(1, addressBook.getRecipients().size());
    }


    @Test
    public void testSaveData() {
        when(mockEditor.putString("PREF_RECIPIENTS_KEY", "someString")).thenReturn(mockEditor);
        AddressBook addressBook = AddressBook.getInstance();
        addressBook.saveData(mockContext);
    }

    @Test
    public void testLoadDataWithMultipleRecipients() {
        when(mockSharedPreferences.getString("PREF_RECIPIENTS_KEY", "")).thenReturn("[{\"lastName\":\"Macroni\",\"firstName\":\"Tony\",\"address\":{\"street\":\"Kaiserstr\",\"streetNr\":\"10C\",\"zip\":\"49808\",\"city\":\"Lingen\"}}, {\"lastName\":\"Macroni\",\"firstName\":\"Tony\",\"address\":{\"street\":\"Kaiserstr\",\"streetNr\":\"10C\",\"zip\":\"49808\",\"city\":\"Lingen\"}}]");
        AddressBook addressBook = AddressBook.getInstance();
        addressBook.loadData(mockContext);
        assertEquals(2, addressBook.getRecipients().size());
    }

    @Test
    public void testSaveAndLoadEmptyData() {
        when(mockEditor.putString(PREF_RECIPIENTS_KEY, "[]")).thenReturn(mockEditor);
        when(mockSharedPreferences.getString(PREF_RECIPIENTS_KEY, "")).thenReturn("[]");
        AddressBook addressBook = AddressBook.getInstance();
        addressBook.saveData(mockContext);
        addressBook.loadData(mockContext);
        assertEquals(0, addressBook.getRecipients().size());
    }


}
