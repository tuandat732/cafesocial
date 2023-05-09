package com.example.cafesocial.service.iservice;

import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IUserService {
    @GET("/users/{id}")
    Call<ResponseData<User>> getUserById(@Path("id") Long id);
}
