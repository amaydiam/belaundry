package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.Promo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by akbar.pambudi on 9/18/2016.
 */
public interface BeasliPromoAdapter {

    @GET("api_beasli/promo-store/{storeCode}")
    Call<List<Promo>> getPromosByStoreCode(@Path("storeCode") String storeCode);

    @GET("api_beasli/promo")
    Call<List<Promo>> getAllPromo();
}
