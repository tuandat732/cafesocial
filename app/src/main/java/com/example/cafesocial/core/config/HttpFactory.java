package com.example.cafesocial.core.config;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class HttpFactory extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
//        if (getRawType(returnType) != HttpCall.class) {
//            return null;
//        }
//
//        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
//        Class<?> rawObservableType = getRawType(observableType);
//        if (rawObservableType != ApiResponse.class) {
//            throw new IllegalArgumentException("type must be a resource");
//        }
//        if (! (observableType instanceof ParameterizedType)) {
//            throw new IllegalArgumentException("resource must be parameterized");
//        }
//        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
//        Executor executor = retrofit.callbackExecutor();
//        return new HttpAdapter<>(bodyType);
        return  new HttpAdapter<>(returnType);
    }
}
