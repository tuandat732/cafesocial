package com.example.cafesocial.utils;

import java.util.Calendar;

public class Carbon {
    private static Calendar calendar = Calendar.getInstance();

    public static int getCurrentHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentMinute() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public static int getCurrentYear() {
        return calendar.get(Calendar.YEAR);
    }
}
