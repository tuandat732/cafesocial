package com.example.cafesocial.core.model;

import android.util.Log;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import retrofit2.Response;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseData<T> {
    private Pageable pageable;
    private T data;
}
