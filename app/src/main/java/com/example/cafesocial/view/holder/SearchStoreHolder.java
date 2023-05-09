package com.example.cafesocial.view.holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.utils.Utils;

public class SearchStoreHolder extends BaseHolder<Store, BaseItemListener> {
    private ImageView img;
    private TextView name, address;

    public SearchStoreHolder(@NonNull View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.storeImg);
        name = itemView.findViewById(R.id.storeName);
        address = itemView.findViewById(R.id.storeAddress);
    }

    @Override
    public void onBindViewHolder(Store item) {
        if(item.getImages().size() > 0)
            loadImage(item.getImages().get(0).getUrl(), img);
        name.setText(item.getName());
        address.setText(item.getAddress());
    }

    @Override
    public void onBindEventHolder(Store item, BaseItemListener itemListener) {

    }
}
