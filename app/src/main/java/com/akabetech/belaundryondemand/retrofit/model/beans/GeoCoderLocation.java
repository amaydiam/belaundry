package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by akbar.pambudi on 9/12/2016.
 */
public class GeoCoderLocation implements Serializable {
    @SerializedName("lat")
    private Double latitude;
    @SerializedName("lng")
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
