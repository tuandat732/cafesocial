package com.example.cafesocial.core.adapter;

import android.view.View;

import com.example.cafesocial.core.model.Model;

public interface BaseItemListener {
    void onItemClick(int layoutId, View view, Model item, int position);
}
