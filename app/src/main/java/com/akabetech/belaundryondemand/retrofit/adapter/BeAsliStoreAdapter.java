package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by akbar.pambudi on 8/15/2016.
 */
public interface BeAsliStoreAdapter {
    @POST("api/store")
    Call<String> create(@Body Store store);
    @PUT("api/store/{id}")
    Call<String> update(@Body Store store, @Path("id") int id);
    @DELETE("api/store/{id}")
    Call<String> delete(@Path("id") int id);
    @GET("api/store/{id}")
    Call<Store> get(@Path("id") int id);

    @GET("api/storeReadyPickup/1")
    Call<List<Store>> getReadyPickup();
    @GET("api/store")
    Call<List<Store>> getAll();
}
