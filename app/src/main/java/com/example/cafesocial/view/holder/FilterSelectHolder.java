package com.example.cafesocial.view.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.model.Benefit;
import com.example.cafesocial.model.Location;
import com.example.cafesocial.model.Purpose;
import com.example.cafesocial.model.Type;

public class FilterSelectHolder extends BaseHolder<Model, BaseItemListener> {
    private TextView name;

    public FilterSelectHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
    }

    @Override
    public void onBindViewHolder(Model item) {

        if(item.getIsSelect() == null) item.setIsSelect(false);
        if(item != null) {
            if(item instanceof Type) name.setText(((Type) item).getName());
            if(item instanceof Benefit) name.setText(((Benefit) item).getName());
            if(item instanceof Location) name.setText(((Location) item).getName());
            if(item instanceof Purpose) name.setText(((Purpose) item).getName());
            if (item.getIsSelect()) {
                name.setBackground(getContext().getResources().getDrawable(R.drawable.item_filter_primary));
                name.setTextColor(getContext().getResources().getColor(R.color.primary));
            } else {
                name.setBackground(getContext().getResources().getDrawable(R.drawable.item_filter_gray));
                name.setTextColor(getContext().getResources().getColor(R.color.black));
            }
        }
    }

    @Override
    public void onBindEventHolder(Model item, BaseItemListener itemListener) {
        onBindViewLayoutItemHolder(item, itemListener);
    }
}
