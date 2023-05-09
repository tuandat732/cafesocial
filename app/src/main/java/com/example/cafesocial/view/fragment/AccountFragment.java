package com.example.cafesocial.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.fragment.BaseFragment;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Post;
import com.example.cafesocial.model.User;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.IUserService;
import com.example.cafesocial.service.iservice.PostService;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.utils.Utils;
import com.example.cafesocial.view.activity.LoginActivity;
import com.example.cafesocial.view.holder.PostHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends BaseFragment {
    private ImageView avatar, btnFace, btnInsta, btnMenuOption;
    private TextView name, btnFollow, rateCount, commentCount, likeCount, followCount, joinDate ;
    private RecyclerView recyclerView;
    private List<Post> list = new ArrayList<>();
    private ListAdapter<Post, PostHolder> adapter;

    private User user = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        avatar = view.findViewById(R.id.avatar);
        btnFace = view.findViewById(R.id.btnFacebook);
        btnInsta = view.findViewById(R.id.btnInstagram);
        btnMenuOption = view.findViewById(R.id.btnMenuOption);
        name = view.findViewById(R.id.name);
        btnFollow = view.findViewById(R.id.btnFollow);
        rateCount = view.findViewById(R.id.rateCount);
        commentCount = view.findViewById(R.id.commentCount);
        likeCount = view.findViewById(R.id.likeCount);
        followCount = view.findViewById(R.id.followCount);
        joinDate = view.findViewById(R.id.joinDate);
        recyclerView = view.findViewById(R.id.listMyPost);
        adapter = new ListAdapter<>(R.layout.item_post, PostHolder.class, list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

    }

    @Override
    public void init() {
        if(!UserService.isAuthen()) {
            goToActivity(LoginActivity.class);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!UserService.isAuthen()) {
            goToActivity(LoginActivity.class);
        } else {
            System.out.println("user nef");
            System.out.println(UserService.getUser());
            HttpService.get(IUserService.class).getUserById(UserService.getUser().getId()).enqueue(new Callback<ResponseData<User>>() {
                @Override
                public void onResponse(Call<ResponseData<User>> call, Response<ResponseData<User>> response) {
                    user = response.body().getData();
                    if(!user.getAvatar().equals(""))
                        Utils.getBitMap(getContext(), user.getAvatar(), avatar);
                    name.setText(user.getUsername());
                    joinDate.setText(user.getCreatedAt()+"");
                    System.out.println(user);
                    rateCount.setText(user.getPosts().size()+"");
                    commentCount.setText(user.getComments().size()+"");

                }

                @Override
                public void onFailure(Call<ResponseData<User>> call, Throwable t) {
                    System.out.println("loi r");
                    System.out.println(t);
                    Toastify.toastError(getContext(), null);
                }
            });

            HttpService.get(PostService.class).findAllByUser(UserService.getUser().getId()).enqueue(new Callback<ResponseData<List<Post>>>() {
                @Override
                public void onResponse(Call<ResponseData<List<Post>>> call, Response<ResponseData<List<Post>>> response) {
                    if(response.code()==200 && response.body().getData() != null) {
                        list.clear();
                        System.out.println(response.body().getData());
                        list.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ResponseData<List<Post>>> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public void initListener() {
        super.initListener();
    }
}
