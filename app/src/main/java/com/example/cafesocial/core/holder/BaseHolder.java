package com.example.cafesocial.core.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.utils.Utils;

public abstract class BaseHolder<T, L extends BaseItemListener> extends RecyclerView.ViewHolder {
    private Context context;
    public View view;
    private int layoutId;

    public BaseHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public abstract void onBindViewHolder(T item);

    public void onBindViewLayoutItemHolder(T item, L itemListener) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onItemClick(layoutId, v, (Model) item, BaseHolder.this.getAdapterPosition());
            }
        });
    }

    public abstract void onBindEventHolder(T item, L itemListener);

    public void loadImage(String url, ImageView imageView)  {
        if(!url.equals(""))
            Utils.getBitMap(getContext(), url, imageView);
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }
}
