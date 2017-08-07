package com.akabetech.belaundryondemand.core.enumeration;

/**
 * Created by akbar.pambudi on 8/21/2016.
 */
public enum PriceListBtnStatus {
    COLLAPSE("collapse"),EXPAND("expand");


    PriceListBtnStatus(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
