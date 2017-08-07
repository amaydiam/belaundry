package com.akabetech.belaundryondemand.util;

import android.widget.EditText;

/**
 * Created by akbar.pambudi on 8/17/2016.
 */
public class ValidationUtil {
    public static boolean blankFieldValidator(EditText editText,String msg){
        if(editText.getText().toString().trim().length()<=0){
            editText.setError(msg);
            return false;
        }
        return true;
    }

    public static boolean mailFormatValidator(EditText editText,String msg){
        if(!editText.getText().toString().contains("@")) {
            editText.setError(msg);
            return false;
        }
        return true;

    }

    public static boolean compareValidator(EditText et1,EditText et2,String msg){
        if(!et1.getText().toString().equals(et2.getText().toString())){
            et1.setError(msg);
            et2.setError(msg);
            return false;
        }
        return true;
    }
}
