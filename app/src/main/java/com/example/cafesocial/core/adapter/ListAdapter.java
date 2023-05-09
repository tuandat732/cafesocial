package com.example.cafesocial.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafesocial.core.holder.BaseHolder;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.view.activity.FilterActivity;
import com.example.cafesocial.view.holder.FilterSelectHolder;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter<M extends Model, VH extends BaseHolder> extends RecyclerView.Adapter<VH>{
    protected List<M> list;
    private BaseItemListener itemListener;
    private int itemLayoutId;
    private Class<VH> HolderClass;
    private ShimmerFrameLayout shimmer;

    public ListAdapter() {
        this.list = new ArrayList<>();
    }

    public ListAdapter(int itemLayoutId, Class<VH> holderClass, BaseItemListener itemListener) {
        list = new ArrayList<>();
        this.itemListener = itemListener;
        this.itemLayoutId = itemLayoutId;
        this.HolderClass = holderClass;
    }

    public ListAdapter(int itemLayoutId, Class<VH> holderClass, List<M> list) {
        if(list == null) list = new ArrayList<>();
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        this.HolderClass = holderClass;
    }

    public ListAdapter(int itemLayoutId, Class<VH> holderClass, List<M> list, BaseItemListener itemListener) {
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        this.HolderClass = holderClass;
        this.itemListener = itemListener;
    }


    public void setShimmerLoading(ShimmerFrameLayout shimmer) {
        this.shimmer = shimmer;
    }

    public ShimmerFrameLayout getShimmerLoading() {
        return shimmer;
    }

    public void setList(List<M> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    protected View getViewInflater(@LayoutRes int resource, @NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
        try {
            VH holder = HolderClass.getConstructor(View.class).newInstance(view);
            holder.setContext(parent.getContext());
            holder.setLayoutId(itemLayoutId);
            return holder;
        } catch (Exception e) {
            System.out.println("Looix naaaa");
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        M item = list.get(holder.getAdapterPosition());
        holder.onBindViewHolder(item);

        holder.onBindEventHolder(item, itemListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
