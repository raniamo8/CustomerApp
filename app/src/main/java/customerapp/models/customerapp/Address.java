package customerapp.models.customerapp;

import java.io.Serializable;

/**
 * Represents an address with street, street number, postal code, and city.
 * Use this class to store recipient addresses in the application.
 */
public class Address implements Serializable
{
    private String street;
    private String streetNr;
    private String zip;
    private String city = "Lingen";

    public void setStreet(String street)
    {
        this.street = street;
    }

    public void setStreetNr(String streetNr)
    {
        this.streetNr = streetNr;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    /**
     * Constructor to initialize an address with all fields.
     *
     * @param street The name of the street.
     * @param streetNr The street number.
     * @param zip The postal code.
     * @param city The city.
     */
    public Address(String street, String streetNr, String zip, String city)
    {
        this.street = street;
        this.streetNr = streetNr;
        this.zip = zip;
        this.city = city;
    }

    /**
     * Constructor to initialize an address without specifying the city.
     * Defaults to "Lingen" for the city.
     * 
     * @param street The name of the street.
     * @param streetNr The street number.
     * @param zip The postal code.
     */
    public Address(String street, String streetNr, String zip)
    {
        this.street = street;
        this.streetNr = streetNr;
        this.zip = zip;
        getCity();
    }

    public String getStreet()
    {
        return street;
    }

    public String getStreetNr()
    {
        return streetNr;
    }

    public String getZip()
    {
        return zip;
    }

    public String getCity()
    {
        return city;
    }

    /**
     * Provides a string representation of the full address.
     *
     * @return The full address in "Street StreetNumber, ZipCode City" format.
     */
    public String getFullAddress()
    {
        return street + " " + streetNr + "\n" + zip + " " + city;
    }

}
