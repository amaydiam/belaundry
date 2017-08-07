package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.KiloanPrice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by akbar.pambudi on 8/21/2016.
 */
public interface BeAsliPackageKiloanStoreAdapter {
    @GET("api/pricelist-package-kiloan-store/{storeCode}")
    Call<List<KiloanPrice>> getKiloanPricesByStoreCode(@Path("storeCode") String storeCode);
}
