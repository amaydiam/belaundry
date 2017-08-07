package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.DownloadImageTask;
import com.akabetech.belaundryondemand.LocationSearchActivity;
import com.akabetech.belaundryondemand.MapReadURLTask;
import com.akabetech.belaundryondemand.MapsRespondParserTask;
import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliStoreAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.GeoCoderAddress;
import com.akabetech.belaundryondemand.retrofit.model.beans.Store;
import com.akabetech.belaundryondemand.ui.dialog.StoreDialog;
import com.akabetech.belaundryondemand.util.Utility;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class MapStoreFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private ImageButton btnSearch_cancel;
    private String[] data;
    boolean wait;
    private static final LatLng INDONESIA_POS = new LatLng(-4.347654, 118.0684913);
    private static final int INDONESIA_ZOOM = 3;
    private static final int FINE_ZOOM = 15;
    private boolean fromSearch = false;
    private LocationManager locationManager;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private ArrayList<Marker> markers = new ArrayList<>();
    public Location curLocation;
    private ArrayList<Marker> directionMarker = new ArrayList<>();
    private Polyline directionPolyline;
    Bitmap storeLogo;
    private GoogleApiClient googleApiClient;
    boolean showDirection = false;

    private final Runnable mapUnPickup = new Runnable() {
        @Override
        public void run() {
            provideAdapterUnpickup();
        }

    };
    private final Thread provideAdapterProccessUnpickup = new Thread(mapUnPickup);
    private final Runnable mapPickup = new Runnable() {
        @Override
        public void run() {
            provideAdapterPickup();
        }
    };
    private final Thread provideAdapterProccessPickup = new Thread(mapPickup);

    public GoogleMap mMap;
    private Map<String, Store> storeStorage = new HashMap<>();
    private LocationListener initListener = null;

    public MapStoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapStoreFrament.
     */
    // TODO: Rename and change types and number of parameters
    public static MapStoreFragment newInstance() {
        MapStoreFragment fragment = new MapStoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();

        buildGoogleApiClient();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map_store_frament, container, false);
        ButterKnife.bind(this, v);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        getActivity().setTitle(R.string.choose_store);
        btnSearch_cancel = (ImageButton) v.findViewById(R.id.btn_search_box_cancel);

        button_pickup_unselect.setVisibility(View.GONE);
        btnSearch_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_bar.setVisibility(View.GONE);
                if(button_pickup_unselect.getVisibility()==View.VISIBLE){
                    refreshLocation(true);}
                else {refreshLocation(false);}
            }
        });
        button_pickup_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLocation(true);
                button_pickup_unselect.setVisibility(View.VISIBLE);
                button_pickup_select.setVisibility(View.GONE);
            }
        });
        button_pickup_unselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLocation(false);

                button_pickup_select.setVisibility(View.VISIBLE);
                button_pickup_unselect.setVisibility(View.GONE);
            }
        });
        buttonRefreshLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_pickup_unselect.getVisibility()==View.VISIBLE){
                    refreshLocation(true);}
                else {refreshLocation(false);}
            }
        });
        button_search_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });

        return v;

    }

    private void refreshLocation(boolean pickup) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        provideAdapterProccessPickup.interrupt();
        provideAdapterProccessUnpickup.interrupt();
        mMap.clear();
        LocationRequest request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setInterval(1000);
        initListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), FINE_ZOOM));
                MapStoreFragment.this.curLocation = location;
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, initListener);
            }
        };
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request,
                initListener
        );

        if(pickup)
        {
            provideAdapterPickup();
        }
        else {
            provideAdapterUnpickup();
        }
    }
    @BindView(R.id.edt_search_box)
    TextView edt_search_box;

    @BindView(R.id.search_bar)
    LinearLayout search_bar;

    @BindView(R.id.button_search_location)
    FloatingActionButton button_search_location;

    @BindView(R.id.button_refresh_location)
    FloatingActionButton buttonRefreshLocation;

    @BindView(R.id.button_pickup_unselect)
    FloatingActionButton button_pickup_unselect;

    @BindView(R.id.button_pickup_select)
    FloatingActionButton button_pickup_select;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        prepareSearchBox();

        provideAdapterProccessUnpickup.start();


    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                LocationRequest request = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setInterval(1000);
                initListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), FINE_ZOOM));
                        MapStoreFragment.this.curLocation = location;
                        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, initListener);
                    }
                };
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request,
                        initListener
                );

            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(getActivity(), connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        }).addApi(LocationServices.API).build();

        googleApiClient.connect();
    }

    private void provideAdapterUnpickup() {
        AdapterComponent.getAdapter(BeAsliStoreAdapter.class).getAll().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(final Call<List<Store>> call, Response<List<Store>> response) {
                if (MapStoreFragment.this.isVisible()) {
                    if (response.code() == 200) {

                        for (final Store store : response.body()) {
                            final LatLng storePos = new LatLng(store.getLatitude(), store.getLongitude());

                            new DownloadImageTask(new DownloadImageTask.OnImageDownloadedListener() {
                                @Override
                                public void getBitmap(Bitmap bitmap) {
                                    if (MapStoreFragment.this.isVisible()) {
                                        Bitmap pointer = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_pointer_map);

                                        Bitmap bmOverlay = Bitmap.createBitmap(pointer.getWidth(), pointer.getHeight(), pointer.getConfig());
                                        Canvas canvas = new Canvas(bmOverlay);
                                        canvas.drawBitmap(pointer, new Matrix(), null);

                                        bitmap = Utility.scaleBitmap(bitmap, canvas.getWidth() / 2, canvas.getWidth() / 2);
                                        int centreX = (canvas.getWidth() - bitmap.getWidth()) / 2;

                                        int centreY = (int) ((canvas.getHeight() - bitmap.getHeight()) * 0.3f);

                                        canvas.drawBitmap(bitmap, centreX, centreY, null);
                                        Marker m = mMap.addMarker(new MarkerOptions().position(storePos).icon(BitmapDescriptorFactory.fromBitmap(bmOverlay)).title(store.getName()));
                                        storeStorage.put(m.getId(), store);
                                        markers.add(m);
                                        store.setLogoBitmap(bitmap);

                                    }

                                }
                            }, true).execute("http://owner.belaundry.co.id/assets/images/logo/" + store.getLogo());


                        }
                        mMap.setOnMarkerClickListener(MapStoreFragment.this);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {

            }
        });
    }

    private void provideAdapterPickup() {
        AdapterComponent.getAdapter(BeAsliStoreAdapter.class).getReadyPickup().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(final Call<List<Store>> call, Response<List<Store>> response) {
                if (MapStoreFragment.this.isVisible()) {
                    if (response.code() == 200) {

                        for (final Store store : response.body()) {
                            final LatLng storePos = new LatLng(store.getLatitude(), store.getLongitude());

                            new DownloadImageTask(new DownloadImageTask.OnImageDownloadedListener() {
                                @Override
                                public void getBitmap(Bitmap bitmap) {
                                    if (MapStoreFragment.this.isVisible()) {
                                        Bitmap pointer = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_pointer_map);

                                        Bitmap bmOverlay = Bitmap.createBitmap(pointer.getWidth(), pointer.getHeight(), pointer.getConfig());
                                        Canvas canvas = new Canvas(bmOverlay);
                                        canvas.drawBitmap(pointer, new Matrix(), null);

                                        bitmap = Utility.scaleBitmap(bitmap, canvas.getWidth() / 2, canvas.getWidth() / 2);
                                        int centreX = (canvas.getWidth() - bitmap.getWidth()) / 2;

                                        int centreY = (int) ((canvas.getHeight() - bitmap.getHeight()) * 0.3f);

                                        canvas.drawBitmap(bitmap, centreX, centreY, null);
                                        Marker m = mMap.addMarker(new MarkerOptions().position(storePos).icon(BitmapDescriptorFactory.fromBitmap(bmOverlay)).title(store.getName()));
                                        storeStorage.put(m.getId(), store);
                                        markers.add(m);
                                        store.setLogoBitmap(bitmap);

                                    }

                                }
                            }, true).execute("http://owner.belaundry.co.id/assets/images/logo/" + store.getLogo());


                        }
                        mMap.setOnMarkerClickListener(MapStoreFragment.this);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {

            }
        });
    }

    public void hideMarkers() {

        for (Marker marker : markers) {
            marker.setVisible(false);
        }
    }

    public void showMarkers() {
        for (Marker marker : directionMarker) {
            marker.remove();
        }

        if (directionPolyline != null) {
            directionPolyline.remove();
        }

        for (Marker marker : markers) {
            marker.setVisible(true);
        }
    }

    public Marker showMarkerAt(Store store) {
        LatLng storePos = new LatLng(store.getLatitude(), store.getLongitude());
        Bitmap pointer = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_pointer_map);
        Bitmap bitmap = store.getLogoBitmap();

        Bitmap bmOverlay = Bitmap.createBitmap(pointer.getWidth(), pointer.getHeight(), pointer.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(pointer, new Matrix(), null);

        bitmap = Utility.scaleBitmap(bitmap, canvas.getWidth() / 2, canvas.getWidth() / 2);
        int centreX = (canvas.getWidth() - bitmap.getWidth()) / 2;

        int centreY = (int) ((canvas.getHeight() - bitmap.getHeight()) * 0.3f);

        canvas.drawBitmap(bitmap, centreX, centreY, null);
        return mMap.addMarker(new MarkerOptions().position(storePos).icon(BitmapDescriptorFactory.fromBitmap(bmOverlay)).title(store.getName()));

    }

    public void showDirectionToStore(Store store) {
        if (!showDirection) {
            directionMarker = new ArrayList<>();

            LatLng storeLatLng = new LatLng(store.getLatitude(), store.getLongitude());
            Location storeLoc = new Location("Store");
            storeLoc.setLatitude(store.getLatitude());
            storeLoc.setLongitude(store.getLongitude());
            String url = getMapsApiDirectionsUrl(curLocation, storeLoc);

            LatLng curLatLng = new LatLng(curLocation.getLatitude(), curLocation.getLongitude());
            Marker curLocMarker = mMap.addMarker(new MarkerOptions().position(curLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.home)).title(store.getName()));
            Marker storeMarker = showMarkerAt(store);
            directionMarker.add(curLocMarker);
            directionMarker.add(storeMarker);

            MapReadURLTask.loadURL(mMap, url, new MapsRespondParserTask.MapsRespondParserListener() {
                @Override
                public void getPolylines(Polyline polyline) {
                    directionPolyline = polyline;
                    showDirection = true;
                }
            });

            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            boundsBuilder.include(storeLatLng);
            boundsBuilder.include(curLatLng);

            LatLngBounds bounds = boundsBuilder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
        }

    }


    public String getMapsApiDirectionsUrl(Location start, Location end) {
        String waypoints = "waypoints=optimize:true|"
                + start.getLatitude() + "," + start.getLongitude()
                + "|" + "|" + end.getLatitude() + ","
                + end.getLongitude();
        String OriDest = "origin=" + start.getLatitude() + "," + start.getLongitude() + "&destination=" + end.getLatitude() + "," + end.getLongitude();

        String sensor = "sensor=false";
        String params = OriDest + "&%20" + waypoints + "&" + sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + params;
        return url;

    }

    private void prepareSearchBox() {
        wait = false;
        //GeoCoderAutoTextAdapter geoCoderAutoTextAdapter = new GeoCoderAutoTextAdapter(getContext(),android.R.layout.simple_dropdown_item_1line);
//        searchEdt.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, new String[]{"surabaya"}));
//        searchEdt.setThreshold(10);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (markers.contains(marker)) {
            StoreDialog storeDialog = new StoreDialog();
            storeDialog.setTargetFragment(this, 1);
            storeDialog.show(getFragmentManager().beginTransaction(), "store dialog");
            storeDialog.setStore(storeStorage.get(marker.getId()));
            showDirection = false;

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), FINE_ZOOM));
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.i(MapStoreFragment.class.getName()+".onActivityResult","data :"+resultCode+" request = "+requestCode);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Location targetLocation = new Location("");//provider name is unnecessary
                targetLocation.setLatitude(place.getLatLng().latitude);//your coords of course
                targetLocation.setLongitude(place.getLatLng().longitude);
                initListener.onLocationChanged(targetLocation);
                search_bar.setVisibility(View.VISIBLE);
                edt_search_box.setText(place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i("error", status.getStatusMessage());

            }
        }

    }
}
