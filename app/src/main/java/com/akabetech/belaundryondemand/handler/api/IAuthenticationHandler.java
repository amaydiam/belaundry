package com.akabetech.belaundryondemand.handler.api;

import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;

/**
 * Created by akbar.pambudi on 8/4/2016.
 */
public interface IAuthenticationHandler {
    void doLogin(String username,String password);
    String getBasic();
    UserBeasli getUserAuth();
    void setUserAuth(UserBeasli userAuth);
    void doLogout();
}
