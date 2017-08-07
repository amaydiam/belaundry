package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.Transaction;
import com.akabetech.belaundryondemand.retrofit.model.beans.TransactionHistoryInProgress;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by akbar.pambudi on 8/28/2016.
 */
public interface TransactionHistoryAdapter {
    @GET("api/show-transaksi-active/{customerId}")
    Call<List<Transaction>> getActiveTransactions(@Path("customerId") int customerId);
    @GET("api/show-transaksi-history/{customerId}")
    Call<List<Transaction>> getTransactionHistories(@Path("customerId") int customerId);

    @GET("api/show-transaksi-inprogress-bycustomer/{customerId}")
    Call<List<TransactionHistoryInProgress>> getInProgressTransaksi(@Path("customerId") int customerId);

    @GET("api/show-transaksi-complete-bycustomer/{customerId}")
    Call<List<TransactionHistoryInProgress>> getCompleteTransaksi(@Path("customerId") int customerId);
//    @GET("api/show-transaksi-history/{customerId}")
//    Call<List<Transaction>> getTransactionHistories(@Path("customerId") int customerId);
}
