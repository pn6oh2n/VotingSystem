package com.example;

import java.sql.Date;
import java.util.Calendar;

public class Util {

    public static final String API_V1 = "/api/v1";

    public static Date getTodaySQLDate(){
        return new Date(System.currentTimeMillis());
    }

    public static Integer getTodayHourOfDay(){
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }
}
