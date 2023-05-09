package com.example.cafesocial.service.iservice;

import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Benefit;
import com.example.cafesocial.model.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BenefitService {
    @GET("/benefits")
    Call<ResponseData<List<Benefit>>> getList();
}
