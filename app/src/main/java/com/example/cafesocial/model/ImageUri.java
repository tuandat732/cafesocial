package com.example.cafesocial.model;

import android.net.Uri;

import com.example.cafesocial.core.model.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ImageUri extends Model {
    private Uri uri;
    private int requestCode; // request code
    private String path;
}
