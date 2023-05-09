package com.example.cafesocial.view.holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.ImagePost;
import com.example.cafesocial.utils.Utils;

public class ImageHolder extends BaseHolder<ImagePost, BaseItemListener> {
    private ImageView img;

    public ImageHolder(@NonNull View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.imgPostItem);
    }

    @Override
    public void onBindViewHolder(ImagePost item) {
        loadImage(item.getUrl(), img);
    }

    @Override
    public void onBindEventHolder(ImagePost item, BaseItemListener itemListener) {

    }
}
