package com.example.wx.myapplication.util;

import android.app.Activity;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

public class FingerUtil {

    private CancellationSignal signal;
    private FingerprintManagerCompat managerCompat;


    public FingerUtil(Activity activity) {
        signal = new CancellationSignal();
        managerCompat = FingerprintManagerCompat.from(activity);
    }

    public void startFingerListen(FingerprintManagerCompat.AuthenticationCallback callback) {
        managerCompat.authenticate(null, 0, signal, callback, null);
    }

    public void stopFingerListen() {
        signal.cancel();
        signal = null;
    }
}
