package com.example.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class DateFormatter {
    public static Date getDateFromString(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return new Date(2020, 01, 01);
        }
    }

    public static String formattedDate(Date date) {
        String dateToString = date.toString();
        return dateToString.substring(0, dateToString.lastIndexOf('G') - 4);
    }
}
