package com.akabetech.belaundryondemand;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andreyyoshuamanik on 9/28/16.
 * To make an app
 */
public class MapsRespondParserTask extends
        AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

    GoogleMap map;

    public interface MapsRespondParserListener {
        void getPolylines(Polyline polyline);
    }

    MapsRespondParserListener listener;

    static void loadStringMap(GoogleMap map, String string, MapsRespondParserListener listener) {
        MapsRespondParserTask task = new MapsRespondParserTask();

        task.map = map;
        task.listener = listener;
        task.execute(string);
    }

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(
            String... jsonData) {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);
            MapsPathJSONParser parser = new MapsPathJSONParser();
            routes = parser.parse(jObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
        ArrayList<LatLng> points = null;
        PolylineOptions polyLineOptions = null;

        if (routes == null) {
            return;
        }
        // traversing through routes
        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList<LatLng>();
            polyLineOptions = new PolylineOptions();
            List<HashMap<String, String>> path = routes.get(i);

            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));

                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            polyLineOptions.addAll(points);
            polyLineOptions.width(11);
            polyLineOptions.color(Color.BLUE);
        }

        if (polyLineOptions != null) {
            Polyline polyline = map.addPolyline(polyLineOptions);
            listener.getPolylines(polyline);
        }
    }
}