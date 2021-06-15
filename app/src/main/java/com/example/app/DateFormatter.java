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
}
