package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by akbar.pambudi on 8/15/2016.
 */
public class BaseBean implements Serializable{
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("update_at")
    private String updateAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
