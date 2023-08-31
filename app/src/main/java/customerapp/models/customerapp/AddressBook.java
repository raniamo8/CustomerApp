package customerapp.models.customerapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Represents an address book that stores a list of recipients and their information.
 * Provides methods to add, delete, and retrieve recipients, as well as manage the QR code counter.
 */
public class AddressBook {
    private static AddressBook instance;
    private ArrayList<Recipient> recipients;
    public static final String PREF_RECIPIENTS_KEY = "PREF_RECIPIENTS_KEY";

    public AddressBook() {
        this.recipients = new ArrayList<>();
    }

    public static AddressBook getInstance() {
        if (instance == null) {
            instance = new AddressBook();
        }
        return instance;
    }

    public ArrayList<Recipient> getRecipients() {
        return recipients;
    }

    public void addRecipient(Recipient recipient, Context context) {
        if (recipient != null) {
            recipients.add(recipient);
            saveData(context);
        }
    }

    public void deleteOneRecipient(int index, Context context) {
        if (index >= 0 && index < recipients.size()) {
            recipients.remove(index);
            saveData(context);
        }
    }

    public void deleteAllRecipients(Context context) {
        if (!recipients.isEmpty()) {
            recipients.clear();
            saveData(context);
        }
    }


    public void saveData(Context context) {
        if (context == null) {
            Log.e("AddressBook", "Context provided to saveData is null.");
            return;
        }
        if (recipients == null) {
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


    public void loadData(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(PREF_RECIPIENTS_KEY, "");
        if (!json.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Recipient>>() {}.getType();
            recipients = gson.fromJson(json, type);
        } else {
            recipients = new ArrayList<>();
        }
    }

    public void reset() {
        this.recipients.clear();
    }

}