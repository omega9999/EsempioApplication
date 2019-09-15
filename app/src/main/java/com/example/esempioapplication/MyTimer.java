package com.example.esempioapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;

public class MyTimer implements Closeable {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    // TODO https://androidclarified.com/android-example-alarm-manager-complete-working/
    // TODO https://www.simplifiedcoding.net/android-scheduled-task-example/
    // TODO https://codelabs.developers.google.com/codelabs/android-training-alarm-manager/index.html?index=..%2F..android-training#4
    // TODO https://github.com/google-developer-training/android-fundamentals-apps-v2/tree/master/StandUp
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    MyTimer(@NonNull final Context context) {
        AlarmReceiver receiver = new AlarmReceiver();
        Log.w(TAG,"create timer");
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        long triggerTime = SystemClock.elapsedRealtime();
        alarmIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, 10 * 1000, alarmIntent);
        //alarmMgr.setAndAllowWhileIdle();

        AlarmManager.AlarmClockInfo info = alarmMgr.getNextAlarmClock();
        Log.w(TAG,"info = " + new Date(info.getTriggerTime()));


    }

    @Override
    public void close() throws IOException {
        alarmMgr.cancel(alarmIntent);
    }




    private static final int REQUEST_CODE = 13588;

    private static final String TAG = MyTimer.class.getSimpleName();
}
