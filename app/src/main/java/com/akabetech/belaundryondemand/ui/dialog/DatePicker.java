package com.akabetech.belaundryondemand.ui.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by akbar.pambudi on 8/19/2016.
 */
public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerListener datePickerListener;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    private int openhorStore;
    private int closehourStore;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog dialog =new DatePickerDialog(getContext(),this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        if(hour+3>=closehourStore)
        {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow=calendar.getTime();
            dialog.getDatePicker().setMinDate(tomorrow.getTime());
        }
        else {
            calendar.add(Calendar.DAY_OF_YEAR,0);
            Date now = calendar.getTime();
            dialog.getDatePicker().setMinDate(now.getTime());
        }

        return dialog;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        Date realDate = calendar.getTime();
        String dateStr = simpleDateFormat.format(realDate);

        datePickerListener.onDateSet(dateStr,realDate);
    }

    public void setDatePickerListener(DatePickerListener datePickerListener, int openHourStore, int closeHourStore) {
        this.openhorStore=openHourStore;
        this.closehourStore=closeHourStore;
        this.datePickerListener = datePickerListener;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public interface DatePickerListener{
        void onDateSet(String dateStr,Date realDate);
    }
}
