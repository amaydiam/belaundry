package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReplyReview {

    @SerializedName("id_review_child")
    @Expose
    private String id_review_child;
    @SerializedName("id_review")
    @Expose
    private String id_review;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    public String getId_review_child() {
        return id_review_child;
    }

    public void setId_review_child(String id_review_child) {
        this.id_review_child = id_review_child;
    }

    public String getId_review() {
        return id_review;
    }

    public void setId_review(String id_review) {
        this.id_review = id_review;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}