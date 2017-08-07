package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.Pickup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Aditya on 10/27/2016.
 */

/**
 * Interface for retrieving history of user pickups from a specific store.
 */
public interface BeAsliPickupHistoryAdapter {
    @GET("api_beasli/pickup-store-user/{storeCode}/{userId}")
    Call<List<Pickup>> get(@Path("storeCode") String storeCode, @Path("userId") String userId);
}
