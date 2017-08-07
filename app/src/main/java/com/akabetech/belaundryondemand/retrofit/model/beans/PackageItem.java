package com.akabetech.belaundryondemand.retrofit.model.beans;

/**
 * Created by akbar.pambudi on 8/21/2016.
 */
public class PackageItem extends Price {

    private int packagePercentage;

    public int getPackagePercentage() {
        return packagePercentage;
    }

    public void setPackagePercentage(int packagePercentage) {
        this.packagePercentage = packagePercentage;
    }
}
