package com.example.esempioapplication.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.esempioapplication.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

public class BluetoothFragment extends Fragment {

    private BluetoothViewModel mViewModel;

    public static BluetoothFragment newInstance() {
        return new BluetoothFragment();
    }

    @SuppressLint("PrivateApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Context MyContext = Objects.requireNonNull(getContext()).getApplicationContext();
        mBluetoothAdapter = getBTAdapter();
        try {
            classBluetoothPan = Class.forName("android.bluetooth.BluetoothPan");
            mIsBTTetheringOn = classBluetoothPan.getDeclaredMethod("isTetheringOn", noparams);
            BTPanCtor = classBluetoothPan.getDeclaredConstructor(Context.class, BluetoothProfile.ServiceListener.class);
            BTPanCtor.setAccessible(true);
            BTSrvInstance = BTPanCtor.newInstance(MyContext, new BTPanServiceListener(MyContext));
        } catch (Exception e) {
            Log.e(TAG,"Error",e);
        }

        return inflater.inflate(R.layout.bluetooth_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BluetoothViewModel.class);
        // TODO: Use the ViewModel
    }

    private BluetoothAdapter getBTAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    // Check whether Bluetooth tethering is enabled.
    private boolean IsBluetoothTetherEnabled() {
        try {
            if (mBluetoothAdapter != null) {
                return (Boolean) mIsBTTetheringOn.invoke(BTSrvInstance, (Object[]) noparams);
            }
        } catch (Exception e) {
            Log.e(TAG,"Error",e);
        }
        return false;
    }

    class BTPanServiceListener implements BluetoothProfile.ServiceListener {
        private final Context mContext;

        BTPanServiceListener(final Context context) {
            this.mContext = context;
        }

        @Override
        public void onServiceConnected(final int profile,
                                       final BluetoothProfile proxy) {
            //Some code must be here or the compiler will optimize away this callback.
            Log.i("MyApp", "BTPan proxy connected");
        }

        @Override
        public void onServiceDisconnected(final int profile) {
        }
    }


    private BluetoothAdapter mBluetoothAdapter = null;
    private Class<?> classBluetoothPan = null;
    private Constructor<?> BTPanCtor = null;
    private Object BTSrvInstance = null;
    private final Class<?>[] noparams = {};
    private Method mIsBTTetheringOn;

    private static final String TAG = BluetoothFragment.class.getSimpleName();
}
