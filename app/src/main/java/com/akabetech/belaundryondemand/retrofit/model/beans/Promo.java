package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akbar.pambudi on 9/18/2016.
 */
public class Promo extends BaseBean {
    @SerializedName("amount")
    private String amount;


    @SerializedName("id_jenis_promo")
    private String id_jenis_promo;

    @SerializedName("max_used")
    private String max_used;

    @SerializedName("tipe_trans")
    private String tipe_trans;

    @SerializedName("poster")
    private String poster;

    @SerializedName("code_promo")
    private String code_promo;


    @SerializedName("expired_date")
    private String expired_date;

    @SerializedName("multiple")
    private String multiple;

    @SerializedName("id_promo")
    private String id_promo;

    @SerializedName("value_promo")
    private String value_promo;

    @SerializedName("storeCode")
    private String storeCode;

    @SerializedName("lastID")
    private String lastID;

    @SerializedName("action")
    private String action;

    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getId_jenis_promo() {
        return id_jenis_promo;
    }

    public void setId_jenis_promo(String id_jenis_promo) {
        this.id_jenis_promo = id_jenis_promo;
    }

    public String getMax_used() {
        return max_used;
    }

    public void setMax_used(String max_used) {
        this.max_used = max_used;
    }

    public String getTipe_trans() {
        return tipe_trans;
    }

    public void setTipe_trans(String tipe_trans) {
        this.tipe_trans = tipe_trans;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCode_promo() {
        return code_promo;
    }

    public void setCode_promo(String code_promo) {
        this.code_promo = code_promo;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getId_promo() {
        return id_promo;
    }

    public void setId_promo(String id_promo) {
        this.id_promo = id_promo;
    }

    public String getValue_promo() {
        return value_promo;
    }

    public void setValue_promo(String value_promo) {
        this.value_promo = value_promo;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getLastID() {
        return lastID;
    }

    public void setLastID(String lastID) {
        this.lastID = lastID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
