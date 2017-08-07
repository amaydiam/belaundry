package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id_review")
    @Expose
    private String idReview;
    @SerializedName("id_user_beasli")
    @Expose
    private String idUserBeasli;
    @SerializedName("storeCode")
    @Expose
    private String storeCode;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     *
     * @return
     * The idReview
     */
    public String getIdReview() {
        return idReview;
    }

    /**
     *
     * @param idReview
     * The id_review
     */
    public void setIdReview(String idReview) {
        this.idReview = idReview;
    }

    /**
     *
     * @return
     * The idUserBeasli
     */
    public String getIdUserBeasli() {
        return idUserBeasli;
    }

    /**
     *
     * @param idUserBeasli
     * The id_user_beasli
     */
    public void setIdUserBeasli(String idUserBeasli) {
        this.idUserBeasli = idUserBeasli;
    }

    /**
     *
     * @return
     * The storeCode
     */
    public String getStoreCode() {
        return storeCode;
    }

    /**
     *
     * @param storeCode
     * The storeCode
     */
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    /**
     *
     * @return
     * The review
     */
    public String getReview() {
        return review;
    }

    /**
     *
     * @param review
     * The review
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     *
     * @return
     * The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}