package com.example.esempioapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.mStartScan = this.findViewById(R.id.scan_button);
        this.mStartScan.setOnClickListener(new StartScanListener());
        this.mWifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        this.mWifiReceiver = new WifiReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        this.registerReceiver(this.mWifiReceiver, intentFilter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Log.e(TAG,"Permission is not granted");
        }
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(this.mWifiReceiver);
        super.onDestroy();
    }

    private class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
            Log.d(TAG, "WifiReceiver.onReceive result " + success);
            if (success) {
                scanSuccess();
            } else {
                // scan failure handling
                scanFailure();
            }
        }
    }

    private void scanFailure() {
        List<ScanResult> oldResults = mWifiManager.getScanResults();
        Log.d(TAG, "number of WiFi founded: " + oldResults.size());
        for (ScanResult res : oldResults) {
            Log.d(TAG, "Found: " + res);
        }
    }

    private void scanSuccess() {
        List<ScanResult> results = mWifiManager.getScanResults();
        Log.d(TAG, "number of WiFi founded: " + results.size());
        for (ScanResult res : results) {
            Log.d(TAG, "Found: " + res);
        }
    }

    private class StartScanListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            boolean success = mWifiManager.startScan();
            Log.d(TAG, "startScan result " + success);
            if (!success) {
                // scan failure handling
                scanFailure();
            }
        }
    }

    private BroadcastReceiver mWifiReceiver;
    private Button mStartScan;
    private WifiManager mWifiManager;

    private static final String TAG = MainActivity.class.getSimpleName();
}
