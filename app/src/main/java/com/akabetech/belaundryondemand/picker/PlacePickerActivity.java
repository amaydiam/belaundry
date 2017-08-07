package com.akabetech.belaundryondemand.picker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.adapter.LocationAdapter;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.model.ListLocation;
import com.akabetech.belaundryondemand.retrofit.adapter.PlacePickerAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.Address;
import com.akabetech.belaundryondemand.retrofit.model.beans.AddressFromMapsResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacePickerActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener, LocationAdapter.OnLocationItemClickListener {


    @BindView(R.id.search_place)
    EditText searchPlace;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.name_address)
    TextView nameAddress;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.design_bottom_sheet)
    CardView designBottomSheet;
    @BindView(R.id.fab_my_location)
    FloatingActionButton fabMyLocation;


    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @BindView(R.id.clear)
    ImageView imgClear;


    String input;
    private LocationAdapter adapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ListLocation.Prediction> prediction = new ArrayList<>();
    private boolean afterSearch = false;
    private Call<ListLocation> call;
    private View mapView;
    private String current_address;

    @OnClick(R.id.clear)
    void clearSearch() {
        searchPlace.setText("");
    }

    @OnClick(R.id.fab_my_location)
    void FloatingActionButton() {
        toMyLocationPosition();
    }

    @OnClick(R.id.fab)
    void OK() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("latitude", current_latitude);
        returnIntent.putExtra("longitude", current_longitude);
        returnIntent.putExtra("address", current_address);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    private GoogleMap mMap;

    String Lat, Long;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private boolean isLocationChanged = false;
    private double current_latitude = 0;
    private double current_longitude = 0;
    private double current_latitude_mylocation = 0;
    private double current_longitude_mylocation = 0;
    private boolean hasIdetify;
    private float zoomLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);
        ButterKnife.bind(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeButtonEnabled(true);
            actionbar.setTitle(null);
        }


        Intent intent = getIntent();
        Lat = intent.getStringExtra("Lat");
        Long = intent.getStringExtra("Long");
        searchPlace.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    showData(searchPlace.getText().toString());

                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(searchPlace.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        searchPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = searchPlace.getText().toString().trim();
                if (key.length() > 0) {
                    imgClear.setVisibility(View.VISIBLE);
                    showData(key);
                } else
                    imgClear.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        adapter = new LocationAdapter(PlacePickerActivity.this, prediction);
        adapter.setOnLocationItemClickListener(this);

        //recyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // set layout manager
        recyclerView.setLayoutManager(mLayoutManager);

        // set adapter
        recyclerView.setAdapter(adapter);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        locationButton.setVisibility(View.GONE);

       /* LatLng latlong = new LatLng(Prefs.getGeometryLat(this), Prefs.getGeometryLong(this));
        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(latlong, 15);
        mMap.moveCamera(cameraPosition);*/

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
            }
        } else {
            buildGoogleApiClient();
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onCameraIdle() {

        zoomLevel = mMap.getCameraPosition().zoom;

        current_latitude = mMap.getCameraPosition().target.latitude;
        current_longitude = mMap.getCameraPosition().target.longitude;

        checkMyLocation();

        if (!afterSearch) {
            LoadAddress();
        }
        afterSearch = false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if (!isLocationChanged) {

            current_latitude = location.getLatitude();
            current_longitude = location.getLongitude();

            LoadAddress();

            current_latitude_mylocation = location.getLatitude();
            current_longitude_mylocation = location.getLongitude();

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_latitude, current_longitude), 14), 1500, null);
            isLocationChanged = true;
        }

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    public void LoadAddress() {
        hasIdetify = false;
        AdapterComponent
                .getGoogleServiceAdapter(PlacePickerAdapter.class).getAddress(current_latitude + "," + current_longitude).enqueue(new Callback<AddressFromMapsResponse>() {
            @Override
            public void onResponse(Call<AddressFromMapsResponse> call, Response<AddressFromMapsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("OK")) {
                        List<Address> address = response.body().getResults();
                        SetSearchAddress(null, address.get(0).getFormattedAddress());
                    } else {
                        SetSearchAddress(null, null);
                    }
                    hasIdetify = true;
                } else {
                    SetSearchAddress(null, null);
                    hasIdetify = false;
                }
            }

            @Override
            public void onFailure(Call<AddressFromMapsResponse> call, Throwable t) {
                hasIdetify = false;
                SetSearchAddress(null, null);
            }
        });
    }

    private void SetSearchAddress(String name_address, String s_address) {
        if (!TextUtils.isEmpty(name_address)) {
            nameAddress.setText(name_address);
            nameAddress.setVisibility(View.VISIBLE);
        } else
            nameAddress.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(s_address)) {
            current_address=s_address;
            address.setText(s_address);
            fab.setEnabled(true);
        } else {
            current_address="";
            address.setText("");
            fab.setEnabled(false);
        }
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            if (!afterSearch) {
                SetSearchAddress(null, null);
            }
            fabMyLocation.hide();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            if (!afterSearch) {
                SetSearchAddress(null, null);
            }
            fabMyLocation.hide();
        }
    }

    private void checkMyLocation() {
        if (current_latitude != current_latitude_mylocation
                || current_longitude != current_longitude_mylocation)
            fabMyLocation.show();
        else fabMyLocation.hide();
    }


    private void toMyLocationPosition() {

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        if (ContextCompat.checkSelfPermission(PlacePickerActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                new AlertDialog.Builder(PlacePickerActivity.this)
                        .setTitle("Please activate location")
                        .setMessage("Click ok to goto settings.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            } else {

                current_latitude_mylocation = location.getLatitude();
                current_longitude_mylocation = location.getLongitude();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_latitude_mylocation, current_longitude_mylocation), isLocationChanged ? zoomLevel : 14), 1500, null);
                isLocationChanged = true;

            }
        }
    }


    private void showData(String input) {
        if (call != null)
            call.cancel();

        prediction.clear();
        adapter.delete_all();
        progressBar.setVisibility(View.VISIBLE);
    /*
        API_KEY_NYA GANTI
       Constants.API_GOOGLE)*/


        call = AdapterComponent
                .getGoogleApiAdapter(PlacePickerAdapter.class).getSearchLocation(input, "geocode", "id", "AIzaSyAFDCJAsZ-vkOJeu11Kw4G2o8zu6NOk_qQ");
        call.enqueue(new Callback<ListLocation>() {

            @Override
            public void onResponse(Call<ListLocation> call, retrofit2.Response<ListLocation> response) {

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    ListLocation result = response.body();
                    List<ListLocation.Prediction> ll = result.getPredictions();
                    for (int i = 0; i < ll.size(); i++) {
                        prediction.add(ll.get(i));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ListLocation> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onRootClick(View v, int position) {
        try{
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(searchPlace.getWindowToken(), 0);
            ListLocation.Prediction place = adapter.data.get(position);
            recyclerView.setVisibility(View.GONE);
            afterSearch = true;
            searchPlace.setText("");
            getLocation(place.getPlaceId(), place.getStructuredFormatting().getMainText(), place.getDescription());}
        catch (Exception ignored){

        }
    }

    void getLocation(final String placeId, final String mainText, final String description) {
        Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getStatus().isSuccess() && places.getCount() > 0) {
                            final Place myPlace = places.get(0);
                            LatLng latlangObj = myPlace.getLatLng();
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlangObj, isLocationChanged ? zoomLevel : 14), 1500, null);
                            SetSearchAddress(mainText, description);
                            resultPlace = myPlace;
                        } else {
                        }
                        places.release();
                    }
                });
    }

    Place resultPlace = null;
}
