package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.Transaction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by andreyyoshuamanik on 9/21/16.
 * To make an app
 */
public interface BeAsliTransactionAdapter {
    @PUT("api/transaksi/{codeTransaksi}")
    Call<String> update(@Body Transaction transaction, @Path("codeTransaksi") String codeTransaksi);

}
