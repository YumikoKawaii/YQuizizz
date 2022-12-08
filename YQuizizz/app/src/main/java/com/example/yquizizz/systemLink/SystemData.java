package com.example.yquizizz.systemLink;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemData {

    public static final Integer dataSize = 240;

    public static String getCurrentDate() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        return formater.format(date);
    }
}
