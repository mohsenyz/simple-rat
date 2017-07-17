package com.phoenix.rat.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mphj on 7/11/17.
 */
public class TimeHelper {
    public static String getCurrentTime(){
        Date date = Calendar.getInstance().getTime();
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
    }
}
