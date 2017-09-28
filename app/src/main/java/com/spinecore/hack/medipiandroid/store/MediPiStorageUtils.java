package com.spinecore.hack.medipiandroid.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class MediPiStorageUtils {
    public static final String PREFS_NAME = "MediPi";
    public static String getStringPreference(Context context, String key,String defaultValue) {
        String value = null;
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null) {
            value = preferences.getString(key, defaultValue);
        }
        return value;
    }

    public static boolean setStringPreference(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            return editor.commit();
        }
        return false;
    }

    public static float getFloatPreference(Context context, String key, float defaultValue) {
        float value = defaultValue;
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null) {
            value = preferences.getFloat(key, defaultValue);
        }
        return value;
    }

    public static boolean setFloatPreference(Context context, String key, float value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat(key, value);
            return editor.commit();
        }
        return false;
    }
    public static long getLongPreference(Context context, String key, long defaultValue) {
        long value = defaultValue;
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null) {
            value = preferences.getLong(key, defaultValue);
        }
        return value;
    }
    public static boolean setLongPreference(Context context, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(key, value);
            return editor.commit();
        }
        return false;
    }
    public static int getIntegerPreference(Context context, String key, int defaultValue) {
        int value = defaultValue;
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null) {
            value = preferences.getInt(key, defaultValue);
        }
        return value;
    }
    public static boolean setIntegerPreference(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            return editor.commit();
        }
        return false;
    }

    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
        boolean value = defaultValue;
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null) {
            value = preferences.getBoolean(key, defaultValue);
        }
        return value;
    }
   public static boolean setBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
        return false;
    }
}