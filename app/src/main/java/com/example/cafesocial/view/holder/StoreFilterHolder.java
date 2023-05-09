package com.example.cafesocial.view.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.model.StoreSave;
import com.example.cafesocial.view.activity.MainActivity;
import com.example.cafesocial.view.fragment.DetailStoreFragment;

public class StoreFilterHolder extends BaseHolder<Store, BaseItemListener> {
    private ImageView img, bookmark;
    private TextView name, address, timeOpen, rate;
    private RatingBar ratingBar;

    public StoreFilterHolder(@NonNull View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.saveStoreImg);
        name = itemView.findViewById(R.id.saveStoreName);
        address = itemView.findViewById(R.id.saveStoreAddress);
        timeOpen = itemView.findViewById(R.id.saveStoreTimeOpen);
        rate = itemView.findViewById(R.id.saveStoreRate);
        ratingBar = itemView.findViewById(R.id.saveStoreRateBar);
        bookmark = itemView.findViewById(R.id.bookmark);
    }

    @Override
    public void onBindViewHolder(Store item) {
        if(item.getImages().size()>0)
            loadImage(item.getImages().get(0).getUrl(), img);
        name.setText(item.getName());
        address.setText(item.getAddress());
        rate.setText(item.getTotalRateCount() + " đánh giá");
        timeOpen.setText(item.getTimeOpen() + " - " + item.getTimeClose());
        ratingBar.setRating(item.getTotalRateCount());
    }

    @Override
    public void onBindEventHolder(Store item, BaseItemListener itemListener) {
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onItemClick(R.layout.item_save_store, bookmark, item, getAdapterPosition());
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragment", "detailStore");
                intent.putExtra("storeId", item.getId());
                getContext().startActivity(intent);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragment", "detailStore");
                intent.putExtra("storeId", item.getId());
                getContext().startActivity(intent);
            }
        });

    }
}
