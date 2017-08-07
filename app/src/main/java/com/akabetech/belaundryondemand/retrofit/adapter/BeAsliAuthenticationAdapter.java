package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.Login;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasliTemp;
import com.akabetech.belaundryondemand.retrofit.model.request.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by akbar.pambudi on 8/14/2016.
 */
public interface BeAsliAuthenticationAdapter {
    @POST("api_beasli/authentication")
    Call<Login> login(@Body LoginRequest loginRequest);

    @POST("api_beasli/uservalidation")
    Call<UserBeasli> loginWithVerify(@Body UserBeasliTemp loginWithVerify);

    @POST("api_beasli/forgetpassword")
    Call<String> forgetPassword(@Body UserBeasliTemp loginWithVerify);

    @POST("api_beasli/resend-code")
    Call<String> resendCodeConfirmasi(@Body UserBeasliTemp data);
}
