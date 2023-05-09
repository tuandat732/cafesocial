package com.example.cafesocial.view.holder;

import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.ImageUri;

public class ImageUriHolder extends BaseHolder<ImageUri, BaseItemListener> {
    private ImageView image;

    public ImageUriHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.uriImage);
    }

    @Override
    public void onBindViewHolder(ImageUri item) {
        System.out.println(item.getUri());
        image.setImageURI(item.getUri());
    }

    @Override
    public void onBindEventHolder(ImageUri item, BaseItemListener itemListener) {
        onBindViewLayoutItemHolder(item, itemListener);
    }
}
