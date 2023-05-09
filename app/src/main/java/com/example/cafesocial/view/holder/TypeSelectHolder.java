package com.example.cafesocial.view.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.Type;

public class TypeSelectHolder extends BaseHolder<Type, BaseItemListener> {
    private TextView name;
    private CheckBox checkBox;

    public TypeSelectHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        checkBox = itemView.findViewById(R.id.checkbox);
    }

    @Override
    public void onBindViewHolder(Type item) {
        if(item.getIsSelect() == null) {
            item.setIsSelect(false);
        }
        name.setText(item.getName());
        checkBox.setChecked(item.getIsSelect());
    }

    @Override
    public void onBindEventHolder(Type item, BaseItemListener itemListener) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                itemListener.onItemClick(getLayoutId(), checkBox, item, getAdapterPosition());
            }
        });
    }
}
