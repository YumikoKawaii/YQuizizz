package com.example.yquizizz.systemLink;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemData {

    public static final String RECEIVER = "codebotk1002@gmail.com";
    public static final String EMAIL = "lachuyenanh@gmail.com";
    public static final String PASSWORD = "imqpdflpeogpmofb";
    public static final String EMAIL_SUBJECT = "Feedback: " + Build.VERSION.RELEASE;

    public static final Integer dataSize = 240;
    public static final Integer day = 86400000;

    public static String getCurrentDate() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        return formater.format(date);
    }

    public static boolean checkConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        return true;

        return false;
    }
}
