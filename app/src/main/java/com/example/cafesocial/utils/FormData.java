package com.example.cafesocial.utils;

import com.example.cafesocial.model.ImageUri;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormData {
    public static RequestBody getStringPart(String text) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), text);
        return body;
    }

    public static MultipartBody.Part[] getMultipartFromArrayImage(String name, List<ImageUri> list) {
        MultipartBody.Part[] multipartTypedOutput = new MultipartBody.Part[list.size()];

        for (int index = 0; index < list.size(); index++) {
            File file2 = new File(list.get(index).getPath());
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file2);
            multipartTypedOutput[index] = MultipartBody.Part.createFormData(name, file2.getPath(), surveyBody);
        }
        return multipartTypedOutput;
    }
}
