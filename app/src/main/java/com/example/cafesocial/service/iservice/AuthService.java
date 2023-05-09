package com.example.cafesocial.service.iservice;

import com.example.cafesocial.core.config.ApiResponse;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.dto.request.LoginRequestDto;
import com.example.cafesocial.dto.request.RegisterRequestDto;
import com.example.cafesocial.dto.response.AuthLoginResponseDto;
import com.example.cafesocial.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/auth/login")
    Call<ResponseData<AuthLoginResponseDto>> login(@Body LoginRequestDto dto);

    @POST("/auth/register")
    Call<ResponseData<Boolean>> register(@Body RegisterRequestDto dto);

    @GET("/auth/me")
    Call<ResponseData<User>> getMe(@Header("Authorization") String authorization);
}
