package customerapp.models.customerapp;

import java.io.Serializable;

/**
 * Represents an address with street, street number, postal code, and city.
 * Use this class to store recipient addresses in the application.
 */
public class Address implements Serializable {
    private String street;
    private String streetNr;
    private String zip;
    private String city = "Lingen";

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNr(String streetNr) {
        this.streetNr = streetNr;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Address(String street, String streetNr, String zip, String city) {
        this.street = street;
        this.streetNr = streetNr;
        this.zip = zip;
        this.city = city;
    }

    public Address(String street, String streetNr, String zip) {
        this.street = street;
        this.streetNr = streetNr;
        this.zip = zip;
        getCity();
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNr() {
        return streetNr;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getFullAddress() {
        return street + " " + streetNr + "\n" + zip + " " + city;
    }

}