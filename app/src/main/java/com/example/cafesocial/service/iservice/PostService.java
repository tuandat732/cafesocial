package com.example.cafesocial.service.iservice;

import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.dto.request.CreateCommentRequestDto;
import com.example.cafesocial.dto.response.PostResponseDto;
import com.example.cafesocial.model.Comment;
import com.example.cafesocial.model.Post;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostService {
    @GET("/posts")
    Call<ResponseData<List<Post>>> findAll(
            @Query("limit") Integer limit,
            @Query("skip") Integer skip
    );

    @GET("/posts/users/{id}")
    Call<ResponseData<List<Post>>> findAllByUser(
            @Path("id") Long id
    );

    @Multipart
    @POST("/posts")
    Call<ResponseData<Boolean>> createPost(
            @Header("Authorization") String authorization,
            @Part("rateLocation") RequestBody rateLocation,
            @Part("rateSpace") RequestBody rateSpace,
            @Part("rateMenu") RequestBody rateMenu,
            @Part("rateService") RequestBody rateService,
            @Part("ratePrice") RequestBody ratePrice,
            @Part("rateContent") RequestBody rateContent,
            @Part("storeId") RequestBody storeId,
            @Part MultipartBody.Part[] images
    );

    @GET("/posts/{id}")
    Call<ResponseData<Post>> getById(
            @Path("id") Long id
    );

    @PATCH("/posts/{id}/like")
    Call<ResponseData<Boolean>> likePost(
            @Header("Authorization") String authorization,
            @Path("id") Long id
    );

    @GET("/posts/{id}/comments")
    Call<ResponseData<List<Comment>>> getListCmtByPost(
            @Path("id") Long id
    );

    @POST("/posts/{id}/comments")
    Call<ResponseData<Comment>> createComment(
            @Header("Authorization") String authorization,
            @Path("id") Long id,
            @Body CreateCommentRequestDto dto
            );

    @PATCH("/posts/comments/{id}")
    Call<ResponseData<Boolean>> likeComment(
            @Header("Authorization") String authorization,
            @Path("id") Integer id
    );

}
