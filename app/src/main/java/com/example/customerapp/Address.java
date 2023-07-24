package com.example.customerapp;

import java.util.Arrays;
import java.util.List;

public class Address {
    private String street;
    private String streetNr;
    private String plz;

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNr(String streetNr) {
        this.streetNr = streetNr;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public Address(String street, String streetNr) {
        this.street = street;
        this.streetNr = streetNr;
        this.plz = "";
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNr() {
        return streetNr;
    }

    public String getPlz() {
        return plz;
    }

    public boolean isValidAddress() {
        return !street.isEmpty() && !streetNr.isEmpty() && isValidPlz();
    }

    private boolean isValidPlz() {
        List<String> plzList = Arrays.asList("49808", "49809", "49811");
        return plzList.contains(plz);
    }
}