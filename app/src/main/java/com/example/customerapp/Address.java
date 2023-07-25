package com.example.customerapp;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public Address(String street, String streetNr, String plz) {
        this.street = street;
        this.streetNr = streetNr;
        this.plz = plz;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(plz, address.plz) &&
                Objects.equals(street, address.street) &&
                Objects.equals(streetNr, address.streetNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plz, street, streetNr);
    }

    public boolean isValidAddress() {
        return !street.isEmpty() && !streetNr.isEmpty() && isValidPlz();
    }

    private boolean isValidPlz() {
        List<String> plzList = Arrays.asList("49808", "49809", "49811");
        return plzList.contains(plz);
    }
}
