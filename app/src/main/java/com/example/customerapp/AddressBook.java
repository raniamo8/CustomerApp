package com.example.customerapp;

import android.location.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {
    private List<Address> addresses;

    public AddressBook() {
        addresses = new ArrayList<>();
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void deleteAddress(int index) {
        if (index >= 0 && index < addresses.size()) {
            addresses.remove(index);
        }
    }

    public void deleteAllAddresses() {
        addresses.clear();
    }

    public void loadData() {
        // Hier können Sie den Code zum Laden der Adressen implementieren, z.B. aus einer Datenbank oder einer Datei
    }

    public void saveData() {
        // Hier können Sie den Code zum Speichern der Adressen implementieren, z.B. in einer Datenbank oder einer Datei
    }
}
