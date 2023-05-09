package com.example.cafesocial.core.config;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpCallback<T> implements Callback<T>{
    private HandlerCallback handlerCallback;

    public HttpCallback(HandlerCallback handlerCallback) {
        this.handlerCallback = handlerCallback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        handlerCallback.handle(response, null);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        handlerCallback.handle(null, t);
    }

    private void handleResponse(Response<T> response) {
        if(response.code() == 400) {

        }
    }
}
