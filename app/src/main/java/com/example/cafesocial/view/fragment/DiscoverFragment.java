package com.example.cafesocial.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafesocial.R;
import com.example.cafesocial.consts.Const;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.fragment.BaseFragment;
import com.example.cafesocial.core.listeners.OnInfinityScrollListener;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Post;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.iservice.PostService;
import com.example.cafesocial.utils.Command;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.view.holder.PostHolder;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverFragment extends BaseFragment {
    private ShimmerFrameLayout shimmer;
    private List<Post> posts = new ArrayList<>();
    private RecyclerView recyclerPosts;
    ListAdapter<Post, PostHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void init() {

    }

    @Override
    public void onResume() {
        super.onResume();
        posts.clear();
        shimmer.startShimmer();
        fetchDataApi(0, ()->{
            System.out.println("stop shim");
            shimmer.hideShimmer();
        });
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        recyclerPosts = view.findViewById(R.id.listPost);
        shimmer = view.findViewById(R.id.shimmerPost);

        adapter = new ListAdapter<>(R.layout.item_post, PostHolder.class, posts);
        adapter.setShimmerLoading(shimmer);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerPosts.setAdapter(adapter);
        recyclerPosts.setLayoutManager(manager);
    }

    @Override
    public void initListener() {
        super.initListener();

        recyclerPosts.addOnScrollListener(new OnInfinityScrollListener(recyclerPosts) {
            @Override
            public void fetchData(int totalItem, Command command) {

                System.out.println("scroll nef");
                fetchDataApi(totalItem, command);
            }
        });
    }

    public void fetchDataApi(int skip, Command command) {
        HttpService.get(PostService.class).findAll(Const.POST_LIMIT, skip).enqueue(new Callback<ResponseData<List<Post>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Post>>> call, Response<ResponseData<List<Post>>> response) {
                command.execute();
                if(response.body().getData().size() > 0) {
                    posts.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Post>>> call, Throwable t) {
                System.out.println("loi cal post");
                System.out.println(t);
                Toastify.toastError(getContext(), null);
            }
        });
    }
}
