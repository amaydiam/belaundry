package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.Pickup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by akbar.pambudi on 8/19/2016.
 */
public interface BeAsliPickupAdapter {
    @POST("api_beasli/pickup")
    Call<String> add(@Body Pickup pickup);
    @GET("api_beasli/pickup-user/{idUser}")
    Call<Pickup> getbyUser(@Path("idUser") String idUser);
}
