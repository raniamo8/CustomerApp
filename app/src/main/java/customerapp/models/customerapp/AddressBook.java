package customerapp.models.customerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an address book that stores a list of recipients and their information.
 * Provides methods to add, delete, and retrieve recipients, as well as manage the QR code counter.
 */
public class AddressBook
{
    private static AddressBook instance;
    private ArrayList<Recipient> recipients;
    public static final String PREF_RECIPIENTS_KEY = "PREF_RECIPIENTS_KEY";

    /**
     * Constructor initializing an empty list of recipients.
     */
    public AddressBook()
    {
        this.recipients = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of the AddressBook.
     *
     * @return the singleton instance
     */
    public static AddressBook getInstance()
    {
        if (instance == null)
        {
            instance = new AddressBook();
        }
        return instance;
    }

    /**
     * Returns the singleton instance of the AddressBook.
     *
     * @return the singleton instance
     */
    public ArrayList<Recipient> getRecipients()
    {
        return recipients;
    }

    /**
     * Adds a recipient to the address book.
     *
     * @param recipient the recipient to be added
     * @param context the Android context
     */
    public void addRecipient(Recipient recipient, Context context)
    {
        if (recipient != null)
        {
            recipients.add(recipient);
            saveData(context);
        }
    }

    /**
     * Deletes a recipient at the specified index.
     *
     * @param index the index of the recipient to be deleted
     * @param context the Android context
     */
    public void deleteOneRecipient(int index, Context context)
    {
        if (index >= 0 && index < recipients.size())
        {
            recipients.remove(index);
            saveData(context);
        }
    }

    /**
     * Deletes all recipients in the address book.
     *
     * @param context the Android context
     */
    public void deleteAllRecipients(Context context)
    {
        if (!recipients.isEmpty())
        {
            recipients.clear();
            saveData(context);
        }
    }


    /**
     * Saves the list of recipients to shared preferences.
     *
     * @param context the Android context
     */
    public void saveData(Context context)
    {
        if (context == null)
        {
            Log.e("AddressBook", "Context provided to saveData is null.");
            return;
        }
        if (recipients == null)
        {
            Log.e("AddressBook", "Recipients list is null. Nothing to save.");
            return;
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipients);
        editor.putString(PREF_RECIPIENTS_KEY, json);

        editor.apply();
    }


    /**
     * Loads the list of recipients from shared preferences.
     *
     * @param context the Android context
     * @noinspection checkstyle:WhitespaceAround, checkstyle:WhitespaceAround, checkstyle:LeftCurly
     */
    public void loadData(Context context)
    {
        if (context == null)
        {
            Log.e("AddressBook", "Context provided to loadData is null.");
            return;
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(PREF_RECIPIENTS_KEY, "");
        if (!json.isEmpty())
        {
            try
            {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Recipient>>() { }.getType();
                List<Recipient> loadedRecipients = gson.fromJson(json, type);
                if (loadedRecipients != null)
                {
                    recipients = new ArrayList<>(loadedRecipients);
                } else
                {
                    Log.e("AddressBook", "Loaded recipients are null.");
                    recipients = new ArrayList<>();
                }
            } catch (JsonSyntaxException e)
            {
                Log.e("AddressBook", "Error parsing recipients from JSON.", e);
                recipients = new ArrayList<>();
            }
        } else
        {
            recipients = new ArrayList<>();
        }
    }


    /**
     * Clears the list of recipients.
     */
    public void reset()
    {
        this.recipients.clear();
    }

}
