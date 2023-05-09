package com.example.cafesocial.view.holder;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.dto.request.CreateCommentRequestDto;
import com.example.cafesocial.model.Comment;
import com.example.cafesocial.model.ImagePost;
import com.example.cafesocial.model.Post;
import com.example.cafesocial.model.PostLike;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.PostService;
import com.example.cafesocial.utils.Utils;
import com.example.cafesocial.view.activity.LoginActivity;
import com.example.cafesocial.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostHolder extends BaseHolder<Post, BaseItemListener> {

    private final ImageView avatarPost;
    private final ImageView avatarCmt;
    private final TextView username;
    private final TextView storeName;
    private final TextView rateCount;
    private final TextView timeUpPost;
    private final TextView postContent;
    private final RecyclerView gridImg;
    private final RecyclerView listCmt;
    private final RatingBar rateStar;
    private final EditText inputCmt;
    private final Button btnLike;
    private final Button btnCmt;
    private final Button btnSendCmt;
    private final Button btnViewMoreCmt;
    private View view;
    private boolean isViewMore = true;
    private ListAdapter<Comment, CommentHolder> commentAdapter;
    private List<Comment> commentList = new ArrayList<>();


    public PostHolder(@NonNull View view) {
        super(view);
        this.view = view;
        avatarPost = view.findViewById(R.id.avatarPost);
        avatarCmt = view.findViewById(R.id.avatarCmt);
        username = view.findViewById(R.id.username);
        storeName = view.findViewById(R.id.storeName);
        rateCount = view.findViewById(R.id.rateCount);
        timeUpPost = view.findViewById(R.id.timeUpPost);
        postContent = view.findViewById(R.id.postContent);
        gridImg = view.findViewById(R.id.gridImg);
        listCmt = view.findViewById(R.id.listCmt);
        rateStar = view.findViewById(R.id.rateStar);
        inputCmt = view.findViewById(R.id.inputCmt);
        btnLike = view.findViewById(R.id.btnLike);
        btnCmt = view.findViewById(R.id.btnCmt);
        btnSendCmt = view.findViewById(R.id.btnSendCmt);
        btnViewMoreCmt = view.findViewById(R.id.btnViewMoreCmt);
    }

    @Override
    public void onBindViewHolder(Post item) {
        commentList.clear();
        if(item.getComments() != null) {
            item.getComments().forEach(comment -> {
                if (comment.getUser() == null)
                    comment.setUser(item.getUser());
            });
            commentList.addAll(item.getComments());
        }
        if(item.getUser().getAvatar() != null) {
            loadImage(item.getUser().getAvatar(), avatarPost);
            loadImage(item.getUser().getAvatar(), avatarCmt);
        }
        this.storeName.setText(item.getStore().getName());
        this.postContent.setText(item.getRateContent());
        this.username.setText(item.getUser().getUsername());
        this.rateCount.setText(item.getTotalRate()+"");
        this.rateStar.setRating(item.getTotalRate());
        this.timeUpPost.setText(item.getCreatedAt());
        this.btnLike.setText("Thích ("+ item.getPostLikes().size() +")");
        this.btnCmt.setText("Bình luận ("+ item.getComments().size() +")");
        if(!item.getUser().getAvatar().equals("")) {
            loadImage(item.getUser().getAvatar(), avatarPost);
            loadImage(item.getUser().getAvatar(), avatarCmt);
        }

        ListAdapter<ImagePost, ImageHolder> imageAdapter = new ListAdapter<>(R.layout.item_image, ImageHolder.class, item.getImages().subList(0, item.getImages().size() > 4 ? 4 :item.getImages().size()));
        LinearLayoutManager manager = new LinearLayoutManager(this.view.getContext(),LinearLayoutManager.HORIZONTAL, false);
        this.gridImg.setLayoutManager(manager);
        this.gridImg.setAdapter(imageAdapter);

        System.out.println(item.getComments());
        commentAdapter = new ListAdapter<>(R.layout.item_comment, CommentHolder.class, commentList);
        LinearLayoutManager managerList = new LinearLayoutManager(this.view.getContext(), RecyclerView.VERTICAL, false);
        this.listCmt.setLayoutManager(managerList);
        this.listCmt.setAdapter(commentAdapter);
        
        listCmt.setVisibility(View.GONE);
        btnViewMoreCmt.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBindEventHolder(Post item, BaseItemListener itemListener) {
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragment","profile");
                intent.putExtra("userId", item.getUserId());
                getContext().startActivity(intent);
            }
        });
        avatarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragment","profile");
                intent.putExtra("userId", item.getUserId());
                getContext().startActivity(intent);
            }
        });
        storeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragment","detailStore");
                intent.putExtra("storeId", item.getStoreId());
                getContext().startActivity(intent);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        
        btnViewMoreCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isViewMore) {
                    btnViewMoreCmt.setText("Ẩn");
                    listCmt.setVisibility(View.VISIBLE);
                    isViewMore = false;
                } else {
                    isViewMore = true;
                    btnViewMoreCmt.setText("Hiển thị");
                    listCmt.setVisibility(View.GONE);
                }
            }
        });
        
        btnSendCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UserService.isAuthen()) {
                    getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                String cmtText = inputCmt.getText().toString().trim();
                if(!cmtText.equals("")) {
                    HttpService.get(PostService.class).createComment(
                            UserService.getToken(),
                            item.getId(),
                            new CreateCommentRequestDto(cmtText)
                    ).enqueue(new Callback<ResponseData<Comment>>() {
                        @Override
                        public void onResponse(Call<ResponseData<Comment>> call, Response<ResponseData<Comment>> response) {
                            if(response.code() == 200) {
                                inputCmt.setText("");
                                Comment cmt = response.body().getData();
                                cmt.setUser(UserService.getUser());
                                commentList.add(cmt);
                                btnCmt.setText("BÌNH LUẬN ("+commentList.size()+")");
                                commentAdapter.notifyDataSetChanged();
                            } else {
                                System.out.println(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData<Comment>> call, Throwable t) {
                            System.out.println("looi tao cmt ");
                        }
                    });
                }
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UserService.isAuthen()) {
                    getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                HttpService.get(PostService.class).likePost(UserService.getToken(), item.getId()).enqueue(new Callback<ResponseData<Boolean>>() {
                    @Override
                    public void onResponse(Call<ResponseData<Boolean>> call, Response<ResponseData<Boolean>> response) {
                        if(response.code() == 200) {
                            if (response.body().getData()) {
                                item.getPostLikes().add(new PostLike(UserService.getUser().getId(), item.getId()));
                                btnLike.setText("THÍCH ("+item.getPostLikes().size()+")");
                                btnLike.setTextColor(getContext().getResources().getColor(R.color.primary));
                            } else {
                                btnLike.setTextColor(getContext().getResources().getColor(R.color.black));
                            }
                        } else {
                            Toast.makeText(getContext(), "Co loi xay ra", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData<Boolean>> call, Throwable t) {

                    }
                });
            }
        });

        btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCmt.setFocusable(true);
            }
        });
    }
}
