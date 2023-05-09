package com.example.cafesocial.service;

import com.example.cafesocial.consts.Const;
import com.example.cafesocial.core.config.HttpFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService {
    private static Retrofit httpClient = null;

    public static Retrofit getHttpClient() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        httpClient = new Retrofit.Builder()
                .baseUrl(Const.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return httpClient;
    }

    public static <T> T get(Class<T> classType) {
        if(httpClient == null) getHttpClient();
        return httpClient.create(classType);
    }
}
