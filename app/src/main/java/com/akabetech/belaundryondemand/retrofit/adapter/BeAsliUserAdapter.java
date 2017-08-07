package com.akabetech.belaundryondemand.retrofit.adapter;

import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;
import com.akabetech.belaundryondemand.retrofit.model.response.GetPictureResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by akbar.pambudi on 8/17/2016.
 */
public interface BeAsliUserAdapter {
    @GET("api_beasli/user-beasli/{id}")
    Call<UserBeasli> get(@Path("id") int id);

    @POST("api_beasli/beasli-registration")
    Call<String> add(@Body UserBeasli user);

    @PUT("api_beasli/user-beasli/{id}")
    Call<String> update(@Path("id") int id, @Body UserBeasli user);

    @Multipart
    @POST("api_beasli/upload-picture/{idUserbeasli}")
    Call<String> updatePicture(@Path("idUserbeasli") int id, @Part MultipartBody.Part file);

    @GET("api_beasli/get-picture/{idUserbeasli}")
    Call<GetPictureResponse> getPicture(@Path("idUserbeasli") int id);

}
