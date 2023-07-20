package com.example.customerapp;

import android.location.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {
    private List<Recipient> recipients;

    public AddressBook() {
        this.recipients = new ArrayList<>();
    }

    public void addRecipient(Recipient recipient) {
        recipients.add(recipient);
        System.out.println("Recipient wurde zum AddressBook erfolgreich hinzugefügt");
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }
}

