package com.akabetech.belaundryondemand.picker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.akabetech.belaundryondemand.R;

public class LocationPickerActivity extends AppCompatActivity {

    public static final int PICK_LOCATION_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);
    }
}
