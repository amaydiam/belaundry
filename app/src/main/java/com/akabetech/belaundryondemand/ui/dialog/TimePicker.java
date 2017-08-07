package com.akabetech.belaundryondemand.ui.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by akbar.pambudi on 8/18/2016.
 */
public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private TimePickerListener timePickerListener;
    private int minHour = -1;
    private int minMinute = -1;

    private int maxHour = 25;
    private int maxMinute = 25;

    private int currentHour = 0;
    private int currentMinute = 0;
    private int openhorStore=0;
    private int closehourStore=0;


    public void setTimePickerListener(TimePickerListener timePickerListener, int openHourStore,int closeHourStore) {
        this.openhorStore=openHourStore;
        this.closehourStore=closeHourStore;
        this.timePickerListener = timePickerListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        Log.e("hour",hour+"");
        if((hour+3>closehourStore)||hour<openhorStore)
        {
            hour=openhorStore+3;
        }
        else
        {
            hour=hour+3;
        }
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, hour, minute, false);
        return dialog;
    }
    public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {


    }
    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            timePickerListener.onTimeChoosed(hourOfDay,minute);
    }

    public interface TimePickerListener{
        void onTimeChoosed(int hour,int minutes);
    }
    public void setMin(int hour, int minute) {
        minHour = hour;
        minMinute = minute;
    }

    public void setMax(int hour, int minute) {
        maxHour = hour;
        maxMinute = minute;
    }

}
