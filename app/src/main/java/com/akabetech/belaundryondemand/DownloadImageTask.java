package com.akabetech.belaundryondemand;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.akabetech.belaundryondemand.util.Utility;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by andreyyoshuamanik on 9/28/16.
 * To make an app
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    OnImageDownloadedListener listener;
    boolean rescale;

    public DownloadImageTask(OnImageDownloadedListener listener, boolean rescale) {
        this.listener = listener;
        this.rescale = rescale;
    }

    public interface OnImageDownloadedListener {
        void getBitmap(Bitmap bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(strings[0]);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            bm = BitmapFactory.decodeStream(is);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
        }
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            if (rescale)
                result = Utility.scaleBitmap(result, 200, 100);

            listener.getBitmap(result);

        }

    }
}