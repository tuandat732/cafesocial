package com.example.cafesocial.view.holder;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.model.Comment;
import com.example.cafesocial.utils.Utils;
import com.example.cafesocial.view.activity.MainActivity;

public class CommentHolder extends BaseHolder<Comment, BaseItemListener> {
    private ImageView cmtAvatar;
    private TextView cmtUsername, cmtTime, cmtContent;

    public CommentHolder(@NonNull View itemView) {
        super(itemView);

        cmtAvatar = itemView.findViewById(R.id.cmtAvatar);
        cmtUsername = itemView.findViewById(R.id.cmtUsername);
        cmtContent = itemView.findViewById(R.id.cmtContent);
        cmtTime = itemView.findViewById(R.id.cmtTime);
    }

    @Override
    public void onBindViewHolder(Comment item) {
        System.out.println("item cmt");
        System.out.println(item);
        if(!item.getUser().getAvatar().equals(""))
            loadImage(item.getUser().getAvatar(), this.cmtAvatar);
        this.cmtUsername.setText(item.getUser().getUsername());
        this.cmtTime.setText(item.getCreatedAt());
        this.cmtContent.setText(item.getContent());
    }

    @Override
    public void onBindEventHolder(Comment item, BaseItemListener itemListener) {
        cmtUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragment","profile");
                intent.putExtra("userId", item.getUserId());
                getContext().startActivity(intent);
            }
        });
        cmtAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragment","profile");
                intent.putExtra("userId", item.getUserId());
                getContext().startActivity(intent);
            }
        });
    }
}
