package com.example.cafesocial.core.config;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

public class HttpAdapter<T> implements CallAdapter<T, HttpCall<T>> {
    private Type responseType;

    public HttpAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public HttpCall<T> adapt(Call<T> call) {
        return new HttpCall<>(call);
    }
}
