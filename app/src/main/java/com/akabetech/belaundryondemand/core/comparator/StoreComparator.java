package com.akabetech.belaundryondemand.core.comparator;

import com.akabetech.belaundryondemand.retrofit.model.beans.Store;

import java.util.Comparator;

public class StoreComparator implements Comparator<Store> {

        @Override
        public int compare(Store lhs, Store rhs) {
           return lhs.getCode().compareTo(rhs.getCode());
        }
    }
