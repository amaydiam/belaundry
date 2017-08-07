package com.akabetech.belaundryondemand.retrofit.model.request;

import com.akabetech.belaundryondemand.component.GsonComponent;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akbar.pambudi on 8/12/2016.
 */
public class LoginRequest {
    @SerializedName("email_user_beasli")
    private String email;
    @SerializedName("password_user_beasli")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginRequest)) return false;

        LoginRequest loginRequest = (LoginRequest) o;

        if (getEmail() != null ? !getEmail().equals(loginRequest.getEmail()) : loginRequest.getEmail() != null)
            return false;
        return getPassword() != null ? getPassword().equals(loginRequest.getPassword()) : loginRequest.getPassword() == null;

    }

    @Override
    public int hashCode() {
        int result = getEmail() != null ? getEmail().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return GsonComponent.getInstance().getGson().toJson(this);
    }
}
