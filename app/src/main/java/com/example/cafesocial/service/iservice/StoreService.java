package com.example.cafesocial.service.iservice;

import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Store;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StoreService {

    @GET("/stores")
    Call<ResponseData<List<Store>>> getTopStore(@Query("totalRateCount") String totalRateCount);

    @GET("/stores")
    Call<ResponseData<List<Store>>> getList(
            @Query("name") String name,
            @Query("isOpen") Boolean isOpen,
            @Query("locations[]") ArrayList<Long> locations,
            @Query("purposes[]") ArrayList<Long> purposes,
            @Query("types[]") ArrayList<Long> types,
            @Query("benefits[]") ArrayList<Long> benefits,
            @Query("priceStart") Float priceStart,
            @Query("priceEnd") Float priceEnd,
            @Query("skip") Integer skip,
            @Query("limit") Integer limit
    );

    @GET("/stores/view")
    Call<ResponseData<List<Store>>> getListStoreViewByUser(
            @Header("Authorization") String authorization
    );

    @GET("/stores/{id}")
    Call<ResponseData<Store>> getById(@Path("id") Long id);

    @Multipart
    @POST("/stores")
    Call<ResponseData<Boolean>> createStore(
            @Header("Authorization") String authorization,
            @Part("name") RequestBody name,
            @Part("address") RequestBody address,
            @Part("present") RequestBody present,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("timeOpen") RequestBody timeOpen,
            @Part("timeClose") RequestBody timeClose,
            @Part("priceStart") RequestBody priceStart,
            @Part("priceEnd") RequestBody priceEnd,
            @Part("wifi") RequestBody wifi,
            @Part("wifiPass") RequestBody wifiPass,
            @Part("phone") RequestBody phone,
            @Part("facebook") RequestBody facebook,
            @Part("instagram") RequestBody instagram,
            @Part("website") RequestBody website,
            @Part("locationId") RequestBody locationId,
            @Part MultipartBody.Part[] images,
            @Part MultipartBody.Part[] menus,
            @Query("benefits[]") List<Long> benefits,
            @Query("purposes[]") List<Long> purposes,
            @Query("types[]") List<Long> types
            );

    @GET("/stores")
    Call<ResponseData<List<Store>>> searchStoreByName(@Query("name") String name);
}
