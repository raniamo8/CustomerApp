package customerapp.models.customerapp;

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
    private Address address;
    private String telephone;
    private String email;
    private String logo;
    private String backgroundImage;
    private LatLng coordinates;


    public StoreDetails(String id, String name, String owner, Address address, String telephone, String email, String logo,
                        String backgroundImage, LatLng coordinates) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.address = address;
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

    public Address getAddress() {
        return address;
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

    public LatLng getCoordinates() {
        return coordinates;
    }
}
