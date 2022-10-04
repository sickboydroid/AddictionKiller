package com.sickboydroid.addictionkiller;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ObbManager {
    private static final String TAG = ObbManager.class.getSimpleName();
    private static final String OBB_FILE_NAME = "main.2019113035.com.dts.freefiremax.obb";
    private static final String OBB_FILE_PATH = "/sdcard/Android/obb/com.dts.freefiremax/" + OBB_FILE_NAME;
    private File lockedObbFile;
    private final File obbFile = new File(OBB_FILE_PATH);

    public ObbManager(File filesDir) {
        lockedObbFile = new File(filesDir, OBB_FILE_NAME);
        initializeLockedObbFile();
    }

    private void initializeLockedObbFile() {
        if (getLockedObbFile().exists()) {
            Log.i(TAG, "Locked obb exists!");
            return;
        }
        StatusManager.addMessage("Setting up the app, please wait...");
        if (!getObbFile().exists()) {
            StatusManager.addMessage("Cannot setup locked obb! OBB does not exist.", true);
            return;
        }
        new Thread(() -> {
            try {
                Files.copy(getObbFile().toPath(), getLockedObbFile().toPath());
                StatusManager.addMessage("Successfully set up app!", true);
            } catch (IOException e) {
                Log.wtf(TAG, "Error occurred while copying obb to locked obb file...", e);
                StatusManager.addMessage("Cannot setup locked obb! Have you set up external storage permission?", true);
            }
        }).start();
    }

    public void copyObbToInternalStorage() {
        if (getObbFile().exists())
            return;
        StatusManager.addMessage("Copying OBB...");
        new Thread(() -> {
            try {
                Files.copy(getLockedObbFile().toPath(), getObbFile().toPath());
                StatusManager.addMessage("OBB successfully copied", true);
            } catch (IOException e) {
                Log.wtf(TAG, "Error occurred while moving locked obb to internal storage", e);
                StatusManager.addMessage("Could not copy obb, something bad happened", true);
            }
        }).start();
    }

    public void removeInternalStorageObb() {
        if (getObbFile().exists()) {
            StatusManager.addMessage("Removing obb...");
            if (getObbFile().delete())
                StatusManager.addMessage("Obb file successfully removed!", true);
            else
                StatusManager.addMessage("Could not remove Obb file!", true);
        }
    }

    public File getObbFile() {
        return obbFile;
    }

    public File getLockedObbFile() {
        return lockedObbFile;
    }
}