package com.akabetech.belaundryondemand.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by akbar.pambudi on 8/20/2016.
 */
public class DateUtils {
    private static final SimpleDateFormat storeDateFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

    public static String toStoreDateFormat(Date date){
        return storeDateFormat.format(date);
    }
}
