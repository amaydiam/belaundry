package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.behavior.activity.FormValidationBehavior;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.picker.PlacePickerActivity;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliPickupAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.Pickup;
import com.akabetech.belaundryondemand.retrofit.model.beans.Store;
import com.akabetech.belaundryondemand.ui.dialog.StoreDialog;
import com.akabetech.belaundryondemand.util.DateUtils;
import com.akabetech.belaundryondemand.util.ValidationUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;


import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickupNowFragment extends Fragment implements FormValidationBehavior {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                /*
                Place place = PlacePicker.getPlace(getContext(), data);
                pickedPosition = place.getLatLng();
                etAddress.setText(place.getName() + " / " + place.getAddress());*/

                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String address = data.getStringExtra("address");

                pickedPosition = new LatLng(latitude, longitude);
                etAddress.setText(address);

            }
        }

    }

    private GoogleApiClient mClient;
  private static final int PLACE_PICKER_REQUEST = 101;
    @BindView(R.id.etAddressPickupNow)
    EditText etAddress;
    @BindView(R.id.etPhonePickupNow)
    EditText etPhone;
    @BindView(R.id.etDescriptionPickupNow)
    EditText etDesc;
    @BindView(R.id.edit_address_detail)
    EditText editAddressDetail;

    LatLng pickedPosition;

    Unbinder unbinder;

    public PickupNowFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.etAddressPickupNow)
    public void AddressPickupNow() {

        Intent intent = new Intent(getActivity(), PlacePickerActivity.class);
       startActivityForResult(intent, PLACE_PICKER_REQUEST);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pickup_now, container, false);
        unbinder = ButterKnife.bind(this, v);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etAddress.setKeyListener(null);

        requestPermission();
    }

    private void requestPermission() {
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
//            }
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ActivityCompat.requestPermissions(getActivity(),new String[]{}, 101);
//        }
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        mClient.connect();
    }

    @Override
    public void onStop() {
//        mClient.disconnect();
        super.onStop();
    }

    @Override
    public void onDestroy() {
//        mClient.disconnect();
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean isValid() {
        boolean valid = true;
        if (!ValidationUtil.blankFieldValidator(etAddress, String.format(getString(R.string.error_blank), "address"))) {
            valid = false;
        }
        if (!ValidationUtil.blankFieldValidator(etDesc, String.format(getString(R.string.error_blank), "description"))) {
            valid = false;
        }
        if (!ValidationUtil.blankFieldValidator(etPhone, String.format(getString(R.string.error_blank), "phone"))) {
            valid = false;
        }
        return valid;
    }

    @Override
    @OnClick(R.id.btnSubmitNow)
    public void doSubmitForm(View v) {
        final Button submit = (Button) v;
        if (isValid()) {
            postData(submit);

        }
    }

    private void postData(final Button submit) {
        Pickup pickup = new Pickup();
        pickup.setPhone(etPhone.getText().toString());
        pickup.setUserId(((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth().getId());
        pickup.setPickupDate(DateUtils.toStoreDateFormat(new Date()));
        pickup.setTimePickup(String.valueOf(new Date().getTime()));
        pickup.setNotePickup(etDesc.getText().toString());
        pickup.setAddress(etAddress.getText().toString() + " // " + editAddressDetail.getText().toString());
        pickup.setTransId(1);
        pickup.setLongitude(pickedPosition.longitude);
        pickup.setLatitude(pickedPosition.latitude);
        pickup.setStoreCode(((Store) getArguments().getParcelable(StoreDialog.STORE)).getCode());
        submit.setText("sending ..");
        submit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.submit_button_tosca_disabled));
        AdapterComponent
                .getAdapter(BeAsliPickupAdapter.class)
                .add(pickup)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        submit.setText(getText(R.string.login_btn));
                        submit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.submit_button_tosca));
                        if (response.code() == 201) {
                            Toast.makeText(getContext(), "transaction successfull", Toast.LENGTH_LONG).show();
                            reset();
                        } else {
                            Toast.makeText(getContext(), response.code() + "-transaction failure", Toast.LENGTH_LONG).show();
                            reset();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    @Override
    public void reset() {
        etAddress.setText("");
        etDesc.setText("");
        etPhone.setText("");
    }
}
