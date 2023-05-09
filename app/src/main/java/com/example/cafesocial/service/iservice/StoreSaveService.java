package com.example.cafesocial.service.iservice;

import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.dto.request.StoreSaveRequestDto;
import com.example.cafesocial.model.StoreSave;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StoreSaveService {
    @POST("/store-saves")
    Call<ResponseData<Boolean>> create(
            @Header("Authorization") String authorization,
            @Body StoreSaveRequestDto storeId
    );

    @POST("/store-saves/delete")
    Call<ResponseData<Boolean>> delete(
            @Header("Authorization") String authorization,
            @Body StoreSaveRequestDto storeId
    );

    @GET("/store-saves")
    Call<ResponseData<List<StoreSave>>> getList(
            @Header("Authorization") String authorization,
            @Query("limit") Integer limit,
            @Query("skip") Integer skip
    );
}
