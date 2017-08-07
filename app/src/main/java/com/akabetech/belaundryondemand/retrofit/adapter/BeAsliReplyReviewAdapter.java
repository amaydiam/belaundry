package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.ReplyReview;
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
public interface BeAsliReplyReviewAdapter {
    @GET("api_beasli/reply-review-by-idreview/{id_review}")
    Call<List<ReplyReview>> get(@Path("id_review") String id_review);

    @GET("api_beasli/reply-review")
    Call<List<ReplyReview>> getAll();

    @POST("api_beasli/reply-review")
    Call<String> add(@Body Review review);
}
