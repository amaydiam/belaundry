package com.akabetech.belaundryondemand.retrofit.model.beans;

/**
 * Created by akbar.pambudi on 8/21/2016.
 */
public class KiloanPrice extends Price{

    private String packagePrice;

    public KiloanPrice(String packageCode, String packageName, String storeCode, String packagePrice) {
        super(packageCode, packageName, storeCode);
        this.packagePrice = packagePrice;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }
}
