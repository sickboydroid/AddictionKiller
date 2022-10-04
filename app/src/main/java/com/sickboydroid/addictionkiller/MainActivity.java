package com.sickboydroid.addictionkiller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ObbManager obbManager;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStatus = findViewById(R.id.tv_status);
        Button btnKillAddiction = findViewById(R.id.btn_show_status);
        btnKillAddiction.setOnClickListener(view -> killAddiction());
        StatusManager.initialize(tvStatus);
        StatusManager.addMessage("Hit the 'Kill Addiction' button to run app!");
    }

    private void killAddiction() {
        if (obbManager == null) obbManager = new ObbManager(getFilesDir());
        if (isGameTime()) {
            StatusManager.addMessage("It is game time ;)");
            if (obbManager.getObbFile().exists())
                StatusManager.addMessage("Obb file already exists, enjoy!", true);
            else
                obbManager.copyObbToInternalStorage();
            return;
        }
        StatusManager.addMessage("It is not game time ;(");
        obbManager.removeInternalStorageObb();
    }


    public boolean isGameTime() {
        Date currentTime = Calendar.getInstance().getTime();
        long time = currentTime.getTime();
        short hour = Short.parseShort(new SimpleDateFormat("HH", Locale.ENGLISH).format(time));
        short minute = Short.parseShort(new SimpleDateFormat("mm", Locale.ENGLISH).format(time));
        Toast.makeText(this, "min: " + minute, Toast.LENGTH_SHORT).show();
        return hour >= 15 && (hour < 18 || (hour == 18 && minute < 30));
    }
}