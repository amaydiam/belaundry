package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.GeocoderResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by akbar.pambudi on 9/12/2016.
 */
public interface GeoCoderAdapter {
    @GET("geocode/json")
    Call<GeocoderResult> getGeocodeFromAddress(@Query("address") String address,@Query(encoded = false,value = "components") String component);
}
