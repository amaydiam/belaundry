package com.akabetech.belaundryondemand.component;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by akbar.pambudi on 8/14/2016.
 */
public class LoadingBarComponent {
    private Context context;
    private static LoadingBarComponent instance;
    private ProgressDialog simpleProgressDialog;
    private LoadingBarComponent(){

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static LoadingBarComponent getInstance(Context context){
        if(instance == null) instance = new LoadingBarComponent();
        instance.setContext(context);
        return instance;
    }

    public ProgressDialog getSimpleProgressDialog(String title,String content){
        if(simpleProgressDialog == null)simpleProgressDialog = new ProgressDialog(context);
        simpleProgressDialog.setMessage(content);
        simpleProgressDialog.setTitle(title);
        simpleProgressDialog.setCancelable(false);
        return simpleProgressDialog;
    }

    public ProgressDialog getSimpleProgressDialog(String content){
        if(simpleProgressDialog == null)simpleProgressDialog = new ProgressDialog(context);
        simpleProgressDialog.setMessage(content);
        simpleProgressDialog.setCancelable(false);
        return simpleProgressDialog;
    }


}
