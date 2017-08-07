package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akbar.pambudi on 8/14/2016.
 */
public class UserBeasliTemp extends BaseBean {
    @SerializedName("id_user_beasli")
    private int id;
    @SerializedName("email_user_beasli")
    private String email;
    @SerializedName("nama_user_beasli")
    private String name;
    @SerializedName("alamat_user_beasli")
    private String address;
    @SerializedName("nomer_user_beasli")
    private String phoneNumber;
    @SerializedName("password_user_beasli")
    private String password;
    @SerializedName("old_password_user_beasli")
    private String oldPassword;
    @SerializedName("con_code")
    private String con_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getCon_code() {
        return con_code;
    }

    public void setCon_code(String con_code) {
        this.con_code = con_code;
    }
}
