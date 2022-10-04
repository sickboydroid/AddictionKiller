package com.sickboydroid.addictionkiller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

@SuppressLint("StaticFieldLeak")
public class StatusManager {
    private static TextView tvStatus;
    private static Context context;
    public static void initialize(TextView tvStatus) {
        StatusManager.tvStatus = tvStatus;
    }

    public static void addMessage(String msg, boolean append) {
        if(append)
            msg = tvStatus.getText().toString() + "\n" + msg;
        String msg_temp = msg;
        tvStatus.post(() -> tvStatus.setText(msg_temp));
    }

    public static void addMessage(String message) {
        addMessage(message, false);
    }
}
