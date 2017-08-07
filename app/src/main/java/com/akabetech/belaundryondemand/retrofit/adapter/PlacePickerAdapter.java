package com.akabetech.belaundryondemand.retrofit.adapter;
import android.content.res.Resources;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.core.Constants;
import com.akabetech.belaundryondemand.model.ListLocation;
import com.akabetech.belaundryondemand.retrofit.model.beans.AddressFromMapsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by akbar.pambudi on 9/12/2016.
 */
public interface PlacePickerAdapter {
    @GET("geocode/json?sensor=true&key="+ Constants.API_GOOGLE)
    Call<AddressFromMapsResponse> getAddress(@Query("latlng") String tags);

    @GET("place/autocomplete/json")
    Call<ListLocation> getSearchLocation(@Query("input") String input, @Query("types") String type, @Query("language") String language, @Query("key") String key);

}
