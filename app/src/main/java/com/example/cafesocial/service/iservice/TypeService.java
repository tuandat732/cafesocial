package com.example.cafesocial.service.iservice;

import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Type;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TypeService {
    @GET("/types")
    Call<ResponseData<List<Type>>> getList();
}
