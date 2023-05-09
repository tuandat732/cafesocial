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
import com.example.cafesocial.model.Purpose;
import com.example.cafesocial.utils.Utils;

public class PurposeHolder extends BaseHolder<Purpose, BaseItemListener> {
    private final ImageView imgItem;
    private final TextView txtItem;

    public PurposeHolder(@NonNull View itemView) {
        super(itemView);

        imgItem = itemView.findViewById(R.id.imgItem);
        txtItem = itemView.findViewById(R.id.txtItem);
    }

    @Override
    public void onBindViewHolder(Purpose item) {
        loadImage(item.getImage(), imgItem);
        txtItem.setText(item.getName());
    }

    @Override
    public void onBindEventHolder(Purpose item, BaseItemListener itemListener) {
        onBindViewLayoutItemHolder(item, itemListener);
    }
}
