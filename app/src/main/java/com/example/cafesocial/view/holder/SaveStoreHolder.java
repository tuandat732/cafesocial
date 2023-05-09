package com.example.cafesocial.view.holder;

import android.content.Intent;
import android.net.Uri;
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
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.utils.Utils;
import com.example.cafesocial.view.activity.LoginActivity;
import com.example.cafesocial.view.activity.MainActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

public class SaveStoreHolder extends BaseHolder<StoreSave, BaseItemListener> {
    private ImageView img, bookmark;
    private TextView name, address, timeOpen, rate;
    private RatingBar ratingBar;

    public SaveStoreHolder(@NonNull View itemView) {
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
    public void onBindViewHolder(StoreSave item) {
        Store store = item.getStore();
        if(store.getImages().size()>0)
            loadImage(store.getImages().get(0).getUrl(), img);
        name.setText(store.getName());
        address.setText(store.getAddress());
        rate.setText(store.getTotalRateCount() + " đánh giá");
        timeOpen.setText(store.getTimeOpen() + " - " + store.getTimeClose());
        ratingBar.setRating(store.getTotalRateCount());
    }

    @Override
    public void onBindEventHolder(StoreSave item, BaseItemListener itemListener) {
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
