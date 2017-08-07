package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by andreyyoshuamanik on 9/21/16.
 * To make an app
 */
public interface BeAsliReviewAdapter {
    @GET("api_beasli/review-by-store/{code}")
    Call<List<Review>> get(@Path("code") String code);

    @GET("api_beasli/review")
    Call<List<Review>> getAll();

    @POST("api_beasli/review")
    Call<String> add(@Body Review review);
}
