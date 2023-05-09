package com.example.cafesocial.view.holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.Benefit;

public class BenefitHolder extends BaseHolder<Benefit, BaseItemListener> {
    private ImageView icon;
    private TextView name;

    public BenefitHolder(@NonNull View itemView) {
        super(itemView);

        icon = itemView.findViewById(R.id.img);
        name = itemView.findViewById(R.id.name);
    }

    @Override
    public void onBindViewHolder(Benefit item) {
        loadImage(item.getImage(), icon);
        name.setText(item.getName());
    }

    @Override
    public void onBindEventHolder(Benefit item, BaseItemListener itemListener) {
        onBindViewLayoutItemHolder(item, itemListener);
    }
}
