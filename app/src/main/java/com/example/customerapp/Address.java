package com.example.customerapp;

import java.io.Serializable;

/**
 * Represents an address with street, street number, postal code, and city.
 * Use this class to store recipient addresses in the application.
 */
public class Address implements Serializable {
    private String street;
    private String houseNumber;
    private String zip;
    private String city = "Lingen";
    private double latitude;
    private double longitude;

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Address(String street, String streetNr) {
        this.street = street;
        this.houseNumber = streetNr;
    }

    public Address(String street, String streetNr, String zip) {
        this.street = street;
        this.houseNumber = streetNr;
        this.zip = zip;
        getCity();
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getFullAddress() {
        return street + " " + houseNumber + "\n" + zip + " " + city;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}