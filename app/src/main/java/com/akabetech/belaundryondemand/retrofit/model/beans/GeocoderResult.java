package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by akbar.pambudi on 9/12/2016.
 */
public class GeocoderResult {
    @SerializedName("results")
    private List<GeoCoderAddress> geoCoderAddresses;
    private String status;

    public List<GeoCoderAddress> getGeoCoderAddresses() {
        return geoCoderAddresses;
    }

    public void setGeoCoderAddresses(List<GeoCoderAddress> geoCoderAddresses) {
        this.geoCoderAddresses = geoCoderAddresses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
