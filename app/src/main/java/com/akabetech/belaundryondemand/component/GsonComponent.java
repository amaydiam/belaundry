package com.akabetech.belaundryondemand.component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by akbar.pambudi on 8/12/2016.
 */
public class GsonComponent {
    private Gson gson;
    private static GsonComponent gsonComponent;
    private GsonComponent(){

    }
    public static GsonComponent getInstance(){
        if(gsonComponent == null){
            gsonComponent = new GsonComponent();
        }
        return gsonComponent;
    }

    public Gson getGson(){
        if(gson ==null){
            gson = new GsonBuilder().create();
        }
        return gson;
    }
}
