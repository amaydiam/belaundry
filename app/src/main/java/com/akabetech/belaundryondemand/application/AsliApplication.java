package com.akabetech.belaundryondemand.application;

import android.support.multidex.MultiDexApplication;

import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.handler.AuthenticationHandler;
import com.akabetech.belaundryondemand.handler.api.IAuthenticationHandler;

/**
 * Created by akbar.pambudi on 8/4/2016.
 */
public class AsliApplication extends MultiDexApplication implements AuthenticateApplication {

    private AuthenticationHandler authenticationHandler;

    @Override
    public IAuthenticationHandler getAuth() {
        if(authenticationHandler == null)
            authenticationHandler = new AuthenticationHandler(this);
        return authenticationHandler;
    }

}
