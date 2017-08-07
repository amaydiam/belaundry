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
import java.util.ArrayList;

/**
 * Created by andreyyoshuamanik on 10/22/16.
 * To make an app
 */

public class DownloadImagesTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {

    DownloadImagesTask.OnImageDownloadedListener listener;
    boolean rescale;

    public DownloadImagesTask(DownloadImagesTask.OnImageDownloadedListener listener, boolean rescale) {
        this.listener = listener;
        this.rescale = rescale;
    }

    public interface OnImageDownloadedListener {
        void getBitmaps(ArrayList<Bitmap> bitmaps);
    }

    @Override
    protected ArrayList<Bitmap> doInBackground(String... strings) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (String url : strings) {
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                Bitmap bitmap = BitmapFactory.decodeStream(is);
                bitmap = Utility.scaleBitmap(bitmap, 400, 400);
                bis.close();
                is.close();
                bitmaps.add(bitmap);
            } catch (IOException e) {
                Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
            }
        }

        return bitmaps;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> results) {
        if (results != null) {
            listener.getBitmaps(results);

        }

    }
}
