package com.akabetech.belaundryondemand.model;

import android.support.annotation.DrawableRes;

/**
 * Created by akbar.pambudi on 8/17/2016.
 */
public class Tab {
    private String text;
    private @DrawableRes int imageResource;

    public Tab(String text, int imageResource) {
        this.text = text;
        this.imageResource = imageResource;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
