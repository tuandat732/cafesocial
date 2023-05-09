package com.example.cafesocial.view.holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.Sale;
import com.example.cafesocial.utils.Utils;

public class SaleHolder extends BaseHolder<Sale, BaseItemListener> {
    private ImageView img;
    private TextView title, storeName, storeAddress;

    public SaleHolder(@NonNull View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.saleItemImage);
        title = itemView.findViewById(R.id.saleItemTitle);
        storeName = itemView.findViewById(R.id.saleItemStoreName);
        storeAddress = itemView.findViewById(R.id.saleItemStoreAddress);
    }

    @Override
    public void onBindViewHolder(Sale item) {
        loadImage(item.getImage(), img);
        title.setText(item.getTitle());
        storeName.setText(item.getStore().getName());
        storeAddress.setText(item.getStore().getAddress());
    }

    @Override
    public void onBindEventHolder(Sale item, BaseItemListener itemListener) {

    }
}
