package com.example.cafesocial.view.holder;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.Location;
import com.example.cafesocial.utils.Utils;

public class LocationHolder extends BaseHolder<Location, BaseItemListener> {
    private final ImageView imgItem;
    private final TextView txtItem;
    private final TextView txtTotal;

    public LocationHolder(@NonNull View itemView) {
        super(itemView);

        imgItem = itemView.findViewById(R.id.imgItem);
        txtItem = itemView.findViewById(R.id.txtItem);
        txtTotal = itemView.findViewById(R.id.txtTotalStore);
    }

    @Override
    public void onBindViewHolder(Location item) {
        loadImage(item.getImage(), imgItem);
        txtItem.setText(item.getName());
        txtTotal.setText(item.getStores().size() + " quán cafe");
    }

    @Override
    public void onBindEventHolder(Location item, BaseItemListener itemListener) {
        onBindViewLayoutItemHolder(item, itemListener);
    }
}
