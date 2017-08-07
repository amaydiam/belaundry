package com.akabetech.belaundryondemand.model;

import com.akabetech.belaundryondemand.retrofit.model.beans.Store;

/**
 * Created by akbar.pambudi on 8/28/2016.
 */
public class TransactionHistory {
    private Store store;
    private int status;
    private int stuffWeight;
    private String dateTransaksi;

    public TransactionHistory(Store store, int status, int stuffWeight,String dateTransaksi) {
        this.store = store;
        this.status = status;
        this.stuffWeight = stuffWeight;
        this.dateTransaksi = dateTransaksi;
    }

    public TransactionHistory() {
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStuffWeight() {
        return stuffWeight;
    }

    public void setStuffWeight(int stuffWeight) {
        this.stuffWeight = stuffWeight;
    }

    public String getDateTransaksi(){
        return dateTransaksi;
    }
}
