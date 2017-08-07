package com.akabetech.belaundryondemand.util;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by akbar.pambudi on 8/20/2016.
 */
public class LocationUtil {
    public static Location getLastFineLocation(LocationManager lm,LocationListener requestNewLocationListener){
       Location location = null;
       location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null){
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if(location == null){
            location = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
        if(location == null){
            if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_LOW);
                criteria.setBearingRequired(true);
                criteria.setCostAllowed(true);
                lm.requestSingleUpdate(criteria,requestNewLocationListener,Looper.getMainLooper());

            }
        }

        return location;
    }


    public static Location getLocationFromAddress(String address, Context context, Locale locale){

        Geocoder geocoder = new Geocoder(context,locale);
        try {
            List<Address> addresses = geocoder.getFromLocationName(address,1);
            if(addresses.size()>0){
                Location l =  new Location(LocationManager.GPS_PROVIDER);
                l.setLatitude(addresses.get(0).getLatitude());
                l.setLongitude(addresses.get(0).getLongitude());
                return l;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;

    }
}
