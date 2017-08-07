package com.akabetech.belaundryondemand.retrofit.interceptor;

import com.akabetech.belaundryondemand.core.NetworkConstants;
import com.akabetech.belaundryondemand.util.Base64Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by akbar.pambudi on 8/14/2016.
 */
public class ApiAuthenticationInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request()
                            .newBuilder()
                            .addHeader("Authorization", "Basic "+Base64Utils.encode(NetworkConstants.API_UATH))
                            .method(chain.request().method(),chain.request().body())
                            .build();

        return chain.proceed(newRequest);


    }
}
