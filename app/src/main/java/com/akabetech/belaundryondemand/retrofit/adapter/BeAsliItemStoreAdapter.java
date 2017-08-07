package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.PackageItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by akbar.pambudi on 8/21/2016.
 */
public interface BeAsliItemStoreAdapter {
    @GET("api/package-item-store/{storeCode}")
    Call<List<PackageItem>> getPackageListByStoreCode(@Path("storeCode") String storeCode);
}
