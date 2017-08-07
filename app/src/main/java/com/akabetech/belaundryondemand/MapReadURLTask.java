package com.akabetech.belaundryondemand;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;

/**
 * Created by andreyyoshuamanik on 9/28/16.
 * To make an app
 */
public class MapReadURLTask extends AsyncTask<String, Void, String> {
    GoogleMap map;

    MapsRespondParserTask.MapsRespondParserListener listener;

    public static void loadURL(GoogleMap googleMap, String string, MapsRespondParserTask.MapsRespondParserListener listener) {
        MapReadURLTask task = new MapReadURLTask();
        task.map = googleMap;
        task.listener = listener;
        task.execute(string);
    }
    @Override
    protected String doInBackground(String... url) {
        String data = "";
        try {
            HttpConnection http = new HttpConnection();
            data = http.readUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        MapsRespondParserTask.loadStringMap(map, result, new MapsRespondParserTask.MapsRespondParserListener() {
            @Override
            public void getPolylines(Polyline polyline) {
                listener.getPolylines(polyline);
            }
        });
    }
}
