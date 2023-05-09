package com.example.cafesocial.core.config;

import java.io.IOException;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpCall<T>  implements Call<T>{
    private Call<T> call;

    public HttpCall(Call<T> call) {
        this.call = call;
    }

    public void process(HandlerCallback handlerCallback) {
        HttpCallback<T> callback = new HttpCallback<>(handlerCallback);
        call.enqueue(callback);
    }

    @Override
    public Response<T> execute() throws IOException {
        return call.execute();
    }

    @Override
    public void enqueue(Callback<T> callback) {
        call.enqueue(callback);
    }

    @Override
    public boolean isExecuted() {
        return call.isExecuted();
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public boolean isCanceled() {
        return call.isCanceled();
    }

    @Override
    public Call<T> clone() {
        return call.clone();
    }

    @Override
    public Request request() {
        return call.request();
    }

    @Override
    public Timeout timeout() {
        return call.timeout();
    }
}
