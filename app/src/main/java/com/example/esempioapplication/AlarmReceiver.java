package com.example.esempioapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    private static long startTime = new Date().getTime();
    public AlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long endTime =  new Date().getTime();
        Log.w(TAG,"Receive intent action " + intent.getAction() + " with delta " + (endTime-startTime));
        startTime = endTime;
    }

    private static final String TAG = MyTimer.class.getSimpleName();
}
