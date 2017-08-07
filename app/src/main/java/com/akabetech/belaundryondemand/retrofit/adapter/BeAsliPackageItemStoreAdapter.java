package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.PerItemPrice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by akbar.pambudi on 8/21/2016.
 */
public interface BeAsliPackageItemStoreAdapter {
    @GET("api/pricelist-package-item-store/{storeCode}")
    Call<List<PerItemPrice>> getPerItemPricesByStoreCode(@Path("storeCode") String storeCode);
}
