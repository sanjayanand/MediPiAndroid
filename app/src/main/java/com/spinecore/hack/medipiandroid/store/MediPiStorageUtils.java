package com.spinecore.hack.medipiandroid.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class MediPiStorageUtils {
    public static final String PREFS_NAME = "MediPi";
    public static final String BP_READING = "bp";
    public static final String OX_READING = "oximeter";
    public static final String WEIGHT_READING = "weight";

    public static String getReading(Context context, String key,String defaultValue) {
        String value = null;
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null) {
            value = preferences.getString(key, defaultValue);
        }
        return value;
    }

    public static boolean setReading(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            return editor.commit();
        }
        return false;
    }

}