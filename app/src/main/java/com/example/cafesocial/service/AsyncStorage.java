package com.example.cafesocial.service;

import android.content.Context;
import android.content.SharedPreferences;

public class AsyncStorage {
    private static SharedPreferences sharedPreferences;

    public static AsyncStorage build(Context ctx) {
        return new AsyncStorage(ctx);
    }

    public AsyncStorage(Context ctx) {
        if(sharedPreferences == null) {
            sharedPreferences = ctx.getSharedPreferences("local", Context.MODE_PRIVATE);
        }
    }

    public void set(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String get(String key) {
        return sharedPreferences.getString(key,"");
    }
}
