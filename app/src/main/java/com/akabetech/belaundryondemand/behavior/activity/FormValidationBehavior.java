package com.akabetech.belaundryondemand.behavior.activity;

import android.view.View;

/**
 * Created by akbar.pambudi on 8/17/2016.
 */
public interface FormValidationBehavior {
    boolean isValid();
    void doSubmitForm(View v);
    void reset();
}
