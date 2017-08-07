package com.akabetech.belaundryondemand.retrofit.model.beans;

/**
 * Created by mrari on 5/31/2017.
 */

public class Login extends BaseBean {
    public String status;
    public UserBeasli body;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserBeasli getBody() {
        return body;
    }

    public void setBody(UserBeasli body) {
        this.body = body;
    }
}
