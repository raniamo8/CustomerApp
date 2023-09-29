package customerapp.models.customerapp;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Represents store details, including shop name, owner name, address, phone number, email, and logo resource ID.
 * This class implements Serializable to enable passing store details between activities using Intent.
 */
public class StoreDetails implements Serializable
{
    private String id;
    private String storeName;
    private String owner;
    private Address address;
    private String telephone;
    private String email;
    private String logo;
    private String backgroundImage;
    private LatLng coordinates;


    /**
     * Initializes a new StoreDetails instance with the provided parameters.
     *
     * @param id               Unique identifier for the store.
     * @param storeName        Store's name.
     * @param owner            Owner's name.
     * @param address          Address of the store.
     * @param telephone        Telephone number.
     * @param email            Email ID.
     * @param logo             Logo's resource ID or URI.
     * @param backgroundImage  Background image's resource ID or URI.
     * @param coordinates      Geographical coordinates of the store.
     */
    public StoreDetails(String id, String storeName, String owner, Address address, String telephone, String email, String logo,
                        String backgroundImage, LatLng coordinates)
    {
        this.id = id;
        this.storeName = storeName;
        this.owner = owner;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.logo = logo;
        this.backgroundImage = backgroundImage;
        this.coordinates = coordinates;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getOwner()
    {
        return owner;
    }

    public Address getAddress()
    {
        return address;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public String getEmail()
    {
        return email;
    }

    public String getLogo()
    {
        return logo;
    }

    public String getBackgroundImage()
    {
        return backgroundImage;
    }

    public LatLng getCoordinates()
    {
        return coordinates;
    }
}
