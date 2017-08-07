package com.akabetech.belaundryondemand.model;

import android.support.annotation.LayoutRes;
import com.akabetech.belaundryondemand.retrofit.model.beans.KiloanPrice;
import com.akabetech.belaundryondemand.retrofit.model.beans.PackageItem;
import com.akabetech.belaundryondemand.retrofit.model.beans.PerItemPrice;

import java.util.List;

/**
 * Created by akbar.pambudi on 8/21/2016.
 */
public class PriceListChild {
   private List<KiloanPrice> kiloanPrices;
   private List<PerItemPrice> perItemPrices;
   private List<PackageItem> packageItems;
   private @LayoutRes int layout;
    public PriceListChild() {
    }

    public PriceListChild(List<KiloanPrice> kiloanPrices, List<PerItemPrice> perItemPrices, List<PackageItem> packageItems, int layout) {
        this.kiloanPrices = kiloanPrices;
        this.perItemPrices = perItemPrices;
        this.packageItems = packageItems;
        this.layout = layout;
    }

    public int getLayout() {
        return layout;
    }

    public List<KiloanPrice> getKiloanPrices() {
        return kiloanPrices;
    }

    public void setKiloanPrices(List<KiloanPrice> kiloanPrices) {
        this.kiloanPrices = kiloanPrices;
    }

    public List<PerItemPrice> getPerItemPrices() {
        return perItemPrices;
    }

    public void setPerItemPrices(List<PerItemPrice> perItemPrices) {
        this.perItemPrices = perItemPrices;
    }

    public List<PackageItem> getPackageItems() {
        return packageItems;
    }

    public void setPackageItems(List<PackageItem> packageItems) {
        this.packageItems = packageItems;
    }
}
