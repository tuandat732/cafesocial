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
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.fragment.BaseFragment;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Sale;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.iservice.SaleService;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.view.holder.SaleHolder;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleFragment extends BaseFragment {

    private List<Sale> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListAdapter<Sale, SaleHolder> adapter;
    private ShimmerFrameLayout shimmer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("Vô sale ");
        return inflater.inflate(R.layout.fragment_sale, container, false);
    }

    @Override
    public void initView(View view) {
        shimmer = view.findViewById(R.id.shimmer);
        recyclerView = view.findViewById(R.id.listSale);

        adapter = new ListAdapter<>(R.layout.item_sale, SaleHolder.class, list);
        adapter.setShimmerLoading(shimmer);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void init() {

    }

    @Override
    public void onResume() {
        super.onResume();
        shimmer.startShimmer();
        HttpService.get(SaleService.class).getSales().enqueue(new Callback<ResponseData<List<Sale>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Sale>>> call, Response<ResponseData<List<Sale>>> response) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shimmer.hideShimmer();
                list.clear();
                list.addAll(response.body().getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData<List<Sale>>> call, Throwable t) {
                System.out.println("call fail sale nè");
                System.out.println(t);
                Toastify.toastError(getContext(), null);
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();


    }
}
