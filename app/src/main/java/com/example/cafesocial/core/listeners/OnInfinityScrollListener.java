package com.example.cafesocial.core.listeners;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.utils.Command;

public abstract class OnInfinityScrollListener extends RecyclerView.OnScrollListener {
    private boolean loading = true;
    private RecyclerView recyclerView;
    private View loadingView;
    private LinearLayoutManager manager;
    private ListAdapter<Model, BaseHolder<Model, BaseItemListener>> adapter;

    public OnInfinityScrollListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.adapter = (ListAdapter<Model, BaseHolder<Model, BaseItemListener>>) recyclerView.getAdapter();
    }

    public OnInfinityScrollListener(RecyclerView recyclerView, View loadingView) {
        this.recyclerView = recyclerView;
        this.manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.adapter = (ListAdapter<Model, BaseHolder<Model, BaseItemListener>>) recyclerView.getAdapter();
        this.loadingView = loadingView;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) { //check for scroll down
            int visibleItemCount = manager.getChildCount();
            int totalItemCount = manager.getItemCount();
            int pastVisiblesItems = manager.findFirstVisibleItemPosition();
            System.out.println(visibleItemCount);
            System.out.println(totalItemCount);
            System.out.println(pastVisiblesItems);
            if (loading) {
                System.out.println("vao load");
                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    System.out.println("loading api");
                    loading = false;
                    if(loadingView != null)
                        loadingView.setVisibility(View.INVISIBLE);
                    if(adapter.getShimmerLoading() != null) {
                        adapter.getShimmerLoading().startShimmer();
                    }

                    System.out.println("Last Item Wow !");
                    // Do pagination.. i.e. fetch new data

                    fetchData(adapter.getItemCount(), hideLoading());

                }
            }
        }
    }

    public Command hideLoading() {
        return new Command() {
            @Override
            public void execute() {
                loading = true;
                if(loadingView != null)
                    loadingView.setVisibility(View.VISIBLE);
                if(adapter.getShimmerLoading() != null) {
                    adapter.getShimmerLoading().hideShimmer();
                }
            }
        };
    }

    public abstract void fetchData(int totalItem, Command executeAfter);
}
