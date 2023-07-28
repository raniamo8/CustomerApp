package com.example.customerapp;

import java.util.Arrays;
import java.util.List;

public class Address {
    private String street;
    private String streetNr;
    private String plz;
    private final String city = "Lingen";

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNr(String streetNr) {
        this.streetNr = streetNr;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public Address(String street, String streetNr, String plz) {
        this.street = street;
        this.streetNr = streetNr;
        this.plz = plz;
        getCity();
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

    public String getCity() {
        return city;
    }

    public String getFullAddress(){
        return street + " " + streetNr + "\n" + plz + " " + city;
    }
}
