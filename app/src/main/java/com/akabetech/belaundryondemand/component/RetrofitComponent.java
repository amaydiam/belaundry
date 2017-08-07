package com.akabetech.belaundryondemand.component;

import com.akabetech.belaundryondemand.core.NetworkConstants;
import com.akabetech.belaundryondemand.retrofit.interceptor.ApiAuthenticationInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by akbar.pambudi on 8/14/2016.
 */
public class RetrofitComponent {
    private static RetrofitComponent instance;
    private Retrofit retrofit;
    private Retrofit googleRetrofit;
    private RetrofitComponent(){

    }

    public static RetrofitComponent getInstance(){
        if(instance == null){
            instance = new RetrofitComponent();
        }
        return instance;
    }

    public Retrofit getGoogleServiceRetrofit(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient customHttpClient = new OkHttpClient.
                    Builder()
                    .connectTimeout(NetworkConstants.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(NetworkConstants.CONNECTION_TIMEOUT,TimeUnit.MILLISECONDS)
                    .readTimeout(NetworkConstants.CONNECTION_TIMEOUT,TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                     .addInterceptor(logging)
                    .build();

            googleRetrofit = new Retrofit
                    .Builder()
                    .baseUrl(NetworkConstants.GOOGLE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(customHttpClient)
                    .build();

        return googleRetrofit;
    }

    public Retrofit getGoogleApiRetrofit(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient customHttpClient = new OkHttpClient.
                    Builder()
                    .connectTimeout(NetworkConstants.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(NetworkConstants.CONNECTION_TIMEOUT,TimeUnit.MILLISECONDS)
                    .readTimeout(NetworkConstants.CONNECTION_TIMEOUT,TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(logging)
                    .build();

            googleRetrofit = new Retrofit
                    .Builder()
                    .baseUrl(NetworkConstants.GOOGLE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(customHttpClient)
                    .build();

        return googleRetrofit;
    }

    public Retrofit getRetrofit(){
            OkHttpClient customHttpClient = new OkHttpClient.
                                        Builder()
                                        .addInterceptor(new ApiAuthenticationInterceptor())
                                        .connectTimeout(NetworkConstants.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                                        .writeTimeout(NetworkConstants.CONNECTION_TIMEOUT,TimeUnit.MILLISECONDS)
                                        .readTimeout(NetworkConstants.CONNECTION_TIMEOUT,TimeUnit.MILLISECONDS)
                                        .retryOnConnectionFailure(true)
                                        .build();

            retrofit = new Retrofit
                           .Builder()
                            .baseUrl(NetworkConstants.BASE_URL)
                           .addConverterFactory(GsonConverterFactory.create())
                           .client(customHttpClient)
                           .build();

        return retrofit;
    }



}

