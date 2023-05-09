package com.example.cafesocial.service.iservice;

import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Sale;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SaleService {
   @Multipart
   @POST("/sales")
   Call<ResponseData<Boolean>> createSale(
           @Header("Authorization") String authorization,
           @Part("storeId") Long storeId,
           @Part("title") String title
   );

   @GET("/sales")
   Call<ResponseData<List<Sale>>> getSales();
}
