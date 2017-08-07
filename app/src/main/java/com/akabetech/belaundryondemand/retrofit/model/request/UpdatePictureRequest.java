package com.akabetech.belaundryondemand.retrofit.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya on 11/5/2016.
 */

public class UpdatePictureRequest {

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    @SerializedName("imageBase64")
    String imageData;
}
