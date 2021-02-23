package com.example.medical.background;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    /**
     *
     * toDate y3ni ki te9ra men database
     *
     */
    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    /**
     * hedi ki tji tsauvgardih fel database
     */

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}