package com.akabetech.belaundryondemand.application.api;

import com.akabetech.belaundryondemand.handler.api.IAuthenticationHandler;

/**
 * Created by akbar.pambudi on 8/4/2016.
 */
public interface AuthenticateApplication {
    IAuthenticationHandler getAuth();

}
