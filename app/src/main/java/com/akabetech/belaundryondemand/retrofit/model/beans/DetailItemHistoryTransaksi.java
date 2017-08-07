package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akbar.pambudi on 9/18/2016.
 */
public class DetailItemHistoryTransaksi extends BaseBean {

    @SerializedName("titleItemTransaksi")
    private String titleItemTransaksi;

    @SerializedName("itemTransaksi")
    private String itemTransaksi;

    @SerializedName("packageServiceItemTraksi")
    private String packageServiceItemTraksi;

    @SerializedName("noteItemTransaksi")
    private String noteItemTransaksi;

    @SerializedName("feeItemTransaksi")
    private String feeItemTransaksi;

    public String getTitleItemTransaksi() {
        return titleItemTransaksi;
    }

    public void setTitleItemTransaksi(String titleItemTransaksi) {
        this.titleItemTransaksi = titleItemTransaksi;
    }

    public String getItemTransaksi() {
        return itemTransaksi;
    }

    public void setItemTransaksi(String itemTransaksi) {
        this.itemTransaksi = itemTransaksi;
    }

    public String getPackageServiceItemTraksi() {
        return packageServiceItemTraksi;
    }

    public void setPackageServiceItemTraksi(String packageServiceItemTraksi) {
        this.packageServiceItemTraksi = packageServiceItemTraksi;
    }

    public String getNoteItemTransaksi() {
        return noteItemTransaksi;
    }

    public void setNoteItemTransaksi(String noteItemTransaksi) {
        this.noteItemTransaksi = noteItemTransaksi;
    }

    public String getFeeItemTransaksi() {
        return feeItemTransaksi;
    }

    public void setFeeItemTransaksi(String feeItemTransaksi) {
        this.feeItemTransaksi = feeItemTransaksi;
    }
}
