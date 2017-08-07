package com.akabetech.belaundryondemand.pica.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliUserAdapter;
import com.akabetech.belaundryondemand.retrofit.model.response.GetPictureResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Aditya on 11/5/2016.
 */

public class Base64Loader extends com.squareup.picasso.RequestHandler {
    @Override
    public boolean canHandleRequest(Request data) {
        return true;
    }

    @Override
    public Result load(Request request, int networkPolicy) throws IOException {
        Response<GetPictureResponse> response = AdapterComponent.getAdapter(BeAsliUserAdapter.class).getPicture(
                Integer.parseInt(request.uri.getLastPathSegment())
        ).execute();
        GetPictureResponse data = response.body();
        String imageBase64 = data.getImageData();
        byte[] imageBytes = com.firebase.client.utilities.Base64.decode(imageBase64);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return new Result(bitmap, Picasso.LoadedFrom.NETWORK);
    }
}
