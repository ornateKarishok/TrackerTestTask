package com.example.trackertesttask;

import android.content.Context;
import android.content.SharedPreferences;

public class DataStorage {
    public static final String DEFAULT_VALUE = " ";
    public static final String APP_PREFERENCES = "app_data";

    public static void setDataToStorage(Context context, String data, String id) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(id, data);
        editor.apply();
    }

    public static String getDataFromStorage(Context context, String id) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(id, DEFAULT_VALUE);
    }
}
