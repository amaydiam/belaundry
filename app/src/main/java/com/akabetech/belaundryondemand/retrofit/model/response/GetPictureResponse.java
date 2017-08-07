package com.akabetech.belaundryondemand.retrofit.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya on 11/5/2016.
 */

public class GetPictureResponse {
    @SerializedName("image")
    String imageData;

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
