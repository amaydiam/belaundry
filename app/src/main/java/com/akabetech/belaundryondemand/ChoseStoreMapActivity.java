package com.akabetech.belaundryondemand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliStoreAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.Store;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoseStoreMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        AdapterComponent.getAdapter(BeAsliStoreAdapter.class).getAll().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if(response.code() == 200){
                    for(Store store: response.body()){
                        LatLng storePos = new LatLng(store.getLatitude(),store.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(storePos).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pointer_map)).title(store.getName()));
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {

            }
        });
    }
}
