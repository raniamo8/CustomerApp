package com.example.customerapp;

import com.google.android.gms.maps.model.LatLng;

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
    private String backgroundImage;

    private LatLng coordinates;


    public StoreDetails(String id, String name, String owner, String street, String houseNumber, String zip, String city, String telephone, String email, String logo, String backgroundImage, LatLng coordinates) {
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
        this.backgroundImage = backgroundImage;
        this.coordinates = coordinates;
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

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }
    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getLogo() {
        return logo;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }
}
