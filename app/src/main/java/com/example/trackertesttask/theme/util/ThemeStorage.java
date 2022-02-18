package com.example.trackertesttask.theme.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeStorage {
    public static final String DEFAULT_THEME = "pink";
    public static final String SHARED_PREFERENCES_ID = "theme";
    public static final String APP_PREFERENCES = "theme_data";

    public static void setThemeColor(Context context, String themeColor) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SHARED_PREFERENCES_ID, themeColor);
        editor.apply();
    }

    public static String getThemeColor(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(SHARED_PREFERENCES_ID, DEFAULT_THEME);
    }
}
