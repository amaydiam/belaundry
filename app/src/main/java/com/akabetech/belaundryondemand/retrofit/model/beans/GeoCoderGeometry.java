package com.akabetech.belaundryondemand.retrofit.model.beans;

import java.io.Serializable;

/**
 * Created by akbar.pambudi on 9/12/2016.
 */
public class GeoCoderGeometry implements Serializable
{

    private GeoCoderLocation location;

    public GeoCoderLocation getLocation() {
        return location;
    }

    public void setLocation(GeoCoderLocation location) {
        this.location = location;
    }
}
