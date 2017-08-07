package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by akbar.pambudi on 9/12/2016.
 */
public class GeoCoderAddress implements Serializable{
    @SerializedName("formatted_address")
    private String address;
    @SerializedName("geometry")
    private GeoCoderGeometry geoCoderGeometry;

    private GeoCoderLocation location;



    public GeoCoderAddress(String address) {
        this.address = address;
    }

    public GeoCoderAddress() {
    }

    public GeoCoderLocation getLocation() {
        return location;
    }

    public void setLocation(GeoCoderLocation location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoCoderGeometry getGeoCoderGeometry() {
        return geoCoderGeometry;
    }

    public void setGeoCoderGeometry(GeoCoderGeometry geoCoderGeometry) {
        this.geoCoderGeometry = geoCoderGeometry;
    }
}
