package com.example.cafesocial.utils;

import com.google.gson.Gson;

public class JSON {
    private static Gson gson = new Gson();

    public static <T> T parse(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    public static String stringify(Object obj) {
        return gson.toJson(obj);
    }

}
