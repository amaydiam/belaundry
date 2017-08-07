package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;


import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
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
import com.akabetech.belaundryondemand.ui.dialog.DatePicker;
import com.akabetech.belaundryondemand.ui.dialog.StoreDialog;
import com.akabetech.belaundryondemand.ui.dialog.TimePicker;
import com.akabetech.belaundryondemand.util.ValidationUtil;
import com.google.android.gms.maps.model.LatLng;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickupLaterFragment extends Fragment implements FormValidationBehavior, TimePicker.TimePickerListener, DatePicker.DatePickerListener {
    private TimePicker timePicker;
    private DatePicker datePicker;
    private Unbinder unbinder;
    private Pickup pickup = new Pickup();

    //binding
    @BindView(R.id.etHourPickupLater)
    EditText etHour;
    @BindView(R.id.etDatePickupLater)
    EditText etDate;
    @BindView(R.id.etAddressPickupLater)
    EditText etAddress;
    @BindView(R.id.etDescriptionPickupLater)
    EditText etDescription;
    @BindView(R.id.btnSubmitLater)
    Button submit;
    @BindView(R.id.etPhonePickupLater)
    EditText phoneNumber;
    @BindView(R.id.edit_address_detail)
    EditText editAddressDetail;
    private static final int PLACE_PICKER_REQUEST = 101;
    private LatLng pickedPosition;
    private Date dateSelect;
    int openHourStore = 8;
    int closeHourStore = 20;

    public PickupLaterFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.etAddressPickupLater)
    public void AddressPickupLater() {
        Intent intent = new Intent(getActivity(), PlacePickerActivity.class);
        startActivityForResult(intent, PLACE_PICKER_REQUEST);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pickup_later, container, false);
        unbinder = ButterKnife.bind(this, v);
        timePicker = new TimePicker();
        datePicker = new DatePicker();

        timePicker.setTimePickerListener(this, openHourStore, closeHourStore);
        datePicker.setDatePickerListener(this, openHourStore, closeHourStore);
        datePicker.setSimpleDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH));
        //make read only
        etHour.setInputType(InputType.TYPE_NULL);
        etDate.setInputType(InputType.TYPE_NULL);

        etAddress.setKeyListener(null);


        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean isValid() {
        boolean valid = true;
        if (!ValidationUtil.blankFieldValidator(etDate, String.format(getString(R.string.error_blank), "date"))) {
            valid = false;
        }
        if (!ValidationUtil.blankFieldValidator(etHour, String.format(getString(R.string.error_blank), "hour"))) {
            valid = false;
        }
        if (!ValidationUtil.blankFieldValidator(etDescription, String.format(getString(R.string.error_blank), "description"))) {
            valid = false;
        }
        if (!ValidationUtil.blankFieldValidator(etAddress, String.format(getString(R.string.error_blank), "address"))) {
            valid = false;
        }
        return valid;
    }

    @Override
    @OnClick(R.id.btnSubmitLater)
    public void doSubmitForm(View v) {

        if (isValid()) {
            postData();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    private void postData() {
        Store chosenStore = (Store) this.getArguments().getParcelable(StoreDialog.STORE);
        submit.setText("sending ..");
        submit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.submit_button_tosca_disabled));
        pickup.setAddress(etAddress.getText().toString() + " // " + editAddressDetail.getText().toString());
        pickup.setLatitude(pickedPosition.latitude);
        pickup.setLongitude(pickedPosition.longitude);
        pickup.setTransId(1);
        pickup.setStoreCode(chosenStore.getCode());
        pickup.setPhone(phoneNumber.getText().toString());
        pickup.setUserId(((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth().getId());
        pickup.setPickupDate(etDate.getText().toString() + " " + etHour.getText().toString());
        pickup.setTimePickup(etHour.getText().toString());
        pickup.setNotePickup(etDescription.getText().toString());
        Log.i("kiriman", pickup.toString());
        AdapterComponent.getAdapter(BeAsliPickupAdapter.class).add(pickup).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                submit.setText(R.string.login_btn);
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
        etDate.setText("");
        etHour.setText("");
        etAddress.setText("");
        phoneNumber.setText("");
        etDescription.setText("");
    }


    @OnClick(R.id.etHourPickupLater)
    public void showTimePicker() {
        if (!etDate.getText().toString().isEmpty()) {
            etDate.setError(null);
            timePicker.show(getChildFragmentManager(), "timepicker");
        } else {
            etDate.setError("please choose your date");
        }

    }

    @OnClick(R.id.etDatePickupLater)
    public void showDatePicker() {
            etDate.setError(null);
            datePicker.show(getChildFragmentManager(), "datePicker");
    }

    @Override
    public void onTimeChoosed(int hour, int minutes) {
        final Calendar c = Calendar.getInstance();
        int hournow = c.get(Calendar.HOUR_OF_DAY);
        int minutenow = c.get(Calendar.MINUTE);
        Calendar calendarnow = Calendar.getInstance();
        Calendar calendarSelect = Calendar.getInstance();
        calendarSelect.setTime(dateSelect);
        if (calendarnow == calendarSelect) {
            if (hour < (hournow + 3)) {
                Toast.makeText(getActivity(), "please choose 3 hours later from now", Toast.LENGTH_SHORT).show();
                timePicker.dismiss();
                timePicker.show(getChildFragmentManager(), "timepicker");
            } else if (hour >= closeHourStore) {
                Toast.makeText(getActivity(), "toko sudah tutup", Toast.LENGTH_SHORT).show();
                timePicker.dismiss();
                timePicker.show(getChildFragmentManager(), "timepicker");
            } else {
                etHour.setText(hour + ":" + minutes);
            }
        } else {
            if (hour < openHourStore + 3) {
                Toast.makeText(getActivity(), "please choose 3 hours later from now", Toast.LENGTH_SHORT).show();
                timePicker.dismiss();
                timePicker.show(getChildFragmentManager(), "timepicker");
            } else if (hour >= closeHourStore) {
                Toast.makeText(getActivity(), "toko sudah tutup", Toast.LENGTH_SHORT).show();
                timePicker.dismiss();
                timePicker.show(getChildFragmentManager(), "timepicker");
            } else {
                etHour.setText(hour + ":" + minutes);
            }
        }
    }

    @Override
    public void onDateSet(String dateStr, Date realDate) {
        dateSelect = realDate;
        etDate.setText(dateStr);
    }
}
