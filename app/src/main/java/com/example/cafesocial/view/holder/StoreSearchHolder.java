package com.example.cafesocial.view.holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.utils.Utils;

public class StoreSearchHolder extends BaseHolder<Store, BaseItemListener> {
    private TextView storeName;
    private ImageView storeImg;
    private TextView storeAddress;


    public StoreSearchHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(Store item) {
        storeName.setText(item.getName());
        storeAddress.setText(item.getAddress());
        if(item.getImages().size() > 0)
            loadImage(item.getImages().get(0).getUrl(), storeImg);
    }

    @Override
    public void onBindEventHolder(Store item, BaseItemListener itemListener) {

    }
}
