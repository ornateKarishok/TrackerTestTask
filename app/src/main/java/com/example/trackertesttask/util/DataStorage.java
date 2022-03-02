package com.example.trackertesttask.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.trackertesttask.model.WorkSession;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class DataStorage {
    public static final String DEFAULT_VALUE = " ";
    public static final String APP_PREFERENCES = "App data";
    public static final String WORK_SESSION_ARRAY = "Work session array";
    public static final String DEFAULT_THEME = "pink";
    public static final String SHARED_PREFERENCES_ID = "Theme";

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

    public static <T> void setListToStorage(Context context, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        setDataToStorage(context, json, WORK_SESSION_ARRAY);
    }

    public static String getThemeColor(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(SHARED_PREFERENCES_ID, DEFAULT_THEME);
    }

    public static List<WorkSession> getListFromStorage(Context context) {
        List<WorkSession> arrayItems = new LinkedList<>();
        String serializedObject = getDataFromStorage(context, WORK_SESSION_ARRAY);
        if (!serializedObject.equals(DEFAULT_VALUE)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<WorkSession>>() {
            }.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }
}
