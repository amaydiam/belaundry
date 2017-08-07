package com.akabetech.belaundryondemand.model;

import android.content.Intent;

/**
 * Created by akbar.pambudi on 8/23/2016.
 */
public class SimpleActionSheetItem {
    private String itemText;
    private Intent directionIntent;
    private boolean needResultCallback;
    private int callback;

    public SimpleActionSheetItem(String itemText, Intent directionIntent, boolean needResultCallback) {
        this.itemText = itemText;
        this.directionIntent = directionIntent;
        this.needResultCallback = needResultCallback;
    }

    public SimpleActionSheetItem(String itemText, Intent directionIntent, boolean needResultCallback, int callback) {
        this.itemText = itemText;
        this.directionIntent = directionIntent;
        this.needResultCallback = needResultCallback;
        this.callback = callback;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public Intent getDirectionIntent() {
        return directionIntent;
    }

    public boolean isNeedResultCallback() {
        return needResultCallback;
    }

    public int getCallback() {
        return callback;
    }
}
