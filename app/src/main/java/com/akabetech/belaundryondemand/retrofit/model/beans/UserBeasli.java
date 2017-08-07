package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.akabetech.belaundryondemand.component.GsonComponent;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akbar.pambudi on 8/14/2016.
 */
public class UserBeasli extends BaseBean {
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

    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserBeasli)) return false;

        UserBeasli that = (UserBeasli) o;

        if (getId() != that.getId()) return false;
        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(that.getPhoneNumber()) : that.getPhoneNumber() != null)
            return false;
        return getPassword() != null ? getPassword().equals(that.getPassword()) : that.getPassword() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return GsonComponent.getInstance().getGson().toJson(this);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
