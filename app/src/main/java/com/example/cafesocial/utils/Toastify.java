package com.example.cafesocial.utils;

import android.content.Context;
import android.graphics.Color;

import com.example.cafesocial.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class Toastify {
    public static void toastError(Context context, String text) {
        if(text == null) {
            text = "Có lỗi xảy ra";
        }
        new StyleableToast
                .Builder(context)
                .text(text)
                .textColor(Color.WHITE)
                .backgroundColor(context.getResources().getColor(R.color.primary))
                .show();
    }

    public static void toastSuccess(Context context, String text) {
        if(text == null) {
            text = "Thành công";
        }
        new StyleableToast
                .Builder(context)
                .text(text)
                .textColor(Color.WHITE)
                .backgroundColor(context.getResources().getColor(R.color.green))
                .show();
    }
}
