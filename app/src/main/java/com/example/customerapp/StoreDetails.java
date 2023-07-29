package com.example.customerapp;

import java.io.Serializable;

/**
 * Represents store details, including shop name, owner name, address, phone number, email, and logo resource ID.
 * This class implements Serializable to enable passing store details between activities using Intent.
 */
public class StoreDetails implements Serializable {
    private String shopname;
    private String owner;
    private Address address;
    private String phonenumber;
    private String email;
    private int logoResourceId;

    public StoreDetails(String shopname, String owner, Address address, String phonenumber, String email, int logoResourceId) {
        this.shopname = shopname;
        this.owner = owner;
        this.address = address;
        this.phonenumber = phonenumber;
        this.email = email;
        this.logoResourceId = logoResourceId;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLogoResourceId() {
        return logoResourceId;
    }

    public void setLogoResourceId(int logoResourceId) {
        this.logoResourceId = logoResourceId;
    }
}