package com.example.cafesocial.view.holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.Location;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.utils.Utils;

public class TopStoreHolder extends BaseHolder<Store, BaseItemListener> {

        private final ImageView imgItem;
        private final TextView txtItem;
        private final TextView txtAddress;

        public TopStoreHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItem);
            txtItem = itemView.findViewById(R.id.txtItem);
            txtAddress = itemView.findViewById(R.id.txtAddress);
        }

    @Override
    public void onBindViewHolder(Store item) {
        if(item.getImages().size()>0)
            loadImage(item.getImages().get(0).getUrl(), imgItem);
        this.txtAddress.setText(item.getAddress());
        this.txtItem.setText(item.getName());
    }

    @Override
    public void onBindEventHolder(Store item, BaseItemListener itemListener) {
        onBindViewLayoutItemHolder(item, itemListener);
    }
}
