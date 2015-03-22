package com.trolley.model;

/**
 * Created by sunny on 22/3/2015.
 */
public class DeliveryAddress {
    String locality;
    String apartment;
    String flat;

    public DeliveryAddress(String locality, String apartment, String flat) {
        this.locality = locality;
        this.apartment = apartment;
        this.flat = flat;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

}
