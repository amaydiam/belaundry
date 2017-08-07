package com.akabetech.belaundryondemand.component;

/**
 * Created by akbar.pambudi on 8/14/2016.
 */
public class AdapterComponent {

    public static<E> E getAdapter(Class<E> clazz){
       return RetrofitComponent.getInstance().getRetrofit().create(clazz);
    }

    public static<E> E getGoogleServiceAdapter(Class<E> clazz) {
        return RetrofitComponent.getInstance().getGoogleServiceRetrofit().create(clazz);
    }


    public static<E> E getGoogleApiAdapter(Class<E> clazz){
        return RetrofitComponent.getInstance().getGoogleApiRetrofit().create(clazz);
    }

}
