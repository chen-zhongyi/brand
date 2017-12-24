package com.chen.brand.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    private static Calendar calendar = Calendar.getInstance();

    public static Integer getYear(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
