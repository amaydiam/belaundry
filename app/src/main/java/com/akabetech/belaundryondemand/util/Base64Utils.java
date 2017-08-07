package com.akabetech.belaundryondemand.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by akbar.pambudi on 8/4/2016.
 */
public class Base64Utils {
    public static String encode(String text) throws UnsupportedEncodingException{
        return Base64.encodeToString(text.getBytes("UTF-8"),Base64.NO_WRAP);
    }

    public static String decode(String encodedText)throws UnsupportedEncodingException{
        return Base64.encodeToString(encodedText.getBytes("UTF-8"),Base64.NO_WRAP);
    }
}
