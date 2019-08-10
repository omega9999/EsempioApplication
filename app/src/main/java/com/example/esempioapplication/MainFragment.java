package com.example.esempioapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;


public class MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        this.mStartScan = root.findViewById(R.id.scan_button);
        this.mPlay = root.findViewById(R.id.play);
        this.mPause = root.findViewById(R.id.pause);
        this.mGoToRandom = root.findViewById(R.id.goto_random);

        this.mStartScan.setOnClickListener(new StartScanListener());

        this.mPlay.setOnClickListener(v -> mMediaPlayer.start());
        this.mPause.setOnClickListener(v -> mMediaPlayer.pause());
        this.mGoToRandom.setOnClickListener(v -> {
            final long duration = mMediaPlayer.getDuration();
            final long position = (long) (Math.random() * duration);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mMediaPlayer.seekTo(position, MediaPlayer.SEEK_PREVIOUS_SYNC);
            } else {
                mMediaPlayer.seekTo((int) position);
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mWifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        this.mWifiReceiver = new WifiReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getActivity().registerReceiver(this.mWifiReceiver, intentFilter);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Log.e(TAG, "Permission is not granted");
        }


        this.mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.inno_nazionale_italia);
        this.mMediaPlayer.setOnCompletionListener(mp -> {
            Toast.makeText(getActivity(), "Finish play", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(this.mWifiReceiver);
        this.mMediaPlayer.release();
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
    private MediaPlayer mMediaPlayer;

    private Button mPlay;
    private Button mPause;
    private Button mGoToRandom;

    private static final String TAG = MainFragment.class.getSimpleName();
}