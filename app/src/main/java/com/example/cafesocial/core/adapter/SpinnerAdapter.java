package com.example.cafesocial.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private int[] imgs;
    private int layoutId;

    public SpinnerAdapter(Context context, int[] imgs, int layoutId) {
        this.imgs = imgs;
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return imgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ImageView img = item.findViewById(layoutId);
        img.setImageResource(imgs[position]);

        return item;
    }

    public int getIndexImage(int img) {
        int p =0;
        for (int i=0;i<imgs.length;i++) {
            if(imgs[i] == img) {
                p = i;
                break;
            }
        }
        return p;
    }
}
