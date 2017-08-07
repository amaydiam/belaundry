package com.akabetech.belaundryondemand.model;

import com.akabetech.belaundryondemand.core.enumeration.PriceListBtnStatus;

import java.util.List;

/**
 * Created by akbar.pambudi on 8/21/2016.
 */
public class PriceListType {

    private String name;
    private PriceListBtnStatus status;
    private List<PriceListChild> children;

    public PriceListType() {
    }

    public PriceListType(String name, PriceListBtnStatus status) {
        this.name = name;
        this.status = status;
    }

    public List<PriceListChild> getChildren() {
        return children;
    }

    public void setChildren(List<PriceListChild> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriceListBtnStatus getStatus() {
        return status;
    }

    public void setStatus(PriceListBtnStatus status) {
        this.status = status;
    }


    public List<?> getChildItemList() {
        return children;
    }

}
