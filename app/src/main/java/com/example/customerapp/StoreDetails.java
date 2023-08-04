package com.example.customerapp;

import java.io.Serializable;

/**
 * Represents store details, including shop name, owner name, address, phone number, email, and logo resource ID.
 * This class implements Serializable to enable passing store details between activities using Intent.
 */
public class StoreDetails implements Serializable {

    private String id;
    private String name;
    private String owner;
    private String street;
    private String houseNumber;
    private String zip;
    private String city;
    private String telephone;
    private String email;
    private String logo;

    public StoreDetails(String id, String name, String owner, String street, String houseNumber, String zip, String city, String telephone, String email, String logo) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zip = zip;
        this.city = city;
        this.telephone = telephone;
        this.email = email;
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}