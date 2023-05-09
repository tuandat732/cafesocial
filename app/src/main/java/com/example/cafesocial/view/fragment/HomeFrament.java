package com.example.cafesocial.view.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.fragment.BaseFragment;
import com.example.cafesocial.core.listeners.OnInfinityScrollListener;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Location;
import com.example.cafesocial.model.Purpose;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.iservice.LocationService;
import com.example.cafesocial.service.iservice.PurposeService;
import com.example.cafesocial.service.iservice.StoreService;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.view.activity.FilterResultActivity;
import com.example.cafesocial.view.activity.SearchActivity;
import com.example.cafesocial.view.holder.TopStoreHolder;
import com.example.cafesocial.view.holder.PurposeHolder;
import com.example.cafesocial.view.holder.LocationHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFrament extends BaseFragment implements BaseItemListener {
    protected int layoutId = R.layout.fragment_home;

    private List<Purpose> purposes = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();
    private List<Store> topStores =  new ArrayList<>();

    private RecyclerView listPurpose, listTopStore, listLocation;
    private ListAdapter<Purpose, PurposeHolder> purposeAdapter;
    private ListAdapter<Store, TopStoreHolder> topStoreAdapter;
    private ListAdapter<Location, LocationHolder> locationAdapter;

    private EditText search;
    private Button btnSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutId, container, false);
    }

    @Override
    public void init() {
        // call api here
        System.out.println("call api ne");
        HttpService.get(PurposeService.class).getList().enqueue(new Callback<ResponseData<List<Purpose>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Purpose>>> call, Response<ResponseData<List<Purpose>>> response) {
                purposes.clear();
                purposes.addAll(response.body().getData());
                purposes.forEach(purpose -> {
                    System.out.println("purpoe id "+purpose.getId());
                });
                purposeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData<List<Purpose>>> call, Throwable t) {
                System.out.println("call fail");
                System.out.println(t);
                Toastify.toastError(getContext(), null);
            }
        });

        HttpService.get(LocationService.class).getList().enqueue(new Callback<ResponseData<List<Location>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Location>>> call, Response<ResponseData<List<Location>>> response) {
                locations.clear();
                locations.addAll(response.body().getData());
                locationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData<List<Location>>> call, Throwable t) {
                System.out.println("call fail");
                System.out.println(t);
                Toastify.toastError(getContext(), null);
            }
        });

        HttpService.get(StoreService.class).getTopStore("desc").enqueue(new Callback<ResponseData<List<Store>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Store>>> call, Response<ResponseData<List<Store>>> response) {
                topStores.clear();
                topStores.addAll(response.body().getData());
                topStoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData<List<Store>>> call, Throwable t) {
                Toastify.toastError(getContext(), null);
            }
        });
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        listPurpose = view.findViewById(R.id.listPurpose);
        purposeAdapter = new ListAdapter<>(R.layout.item_purpose, PurposeHolder.class, purposes, this);
        LinearLayoutManager purposeManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listPurpose.setLayoutManager(purposeManager);
        listPurpose.setAdapter(purposeAdapter);

        listLocation = view.findViewById(R.id.listLocation);
        locationAdapter = new ListAdapter<Location, LocationHolder>(R.layout.item_location, LocationHolder.class, locations, this);
        LinearLayoutManager locationManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listLocation.setLayoutManager(locationManager);
        listLocation.setAdapter(locationAdapter);

        listTopStore = view.findViewById(R.id.listTopStore);
        topStoreAdapter = new ListAdapter<Store, TopStoreHolder>(R.layout.item_top_store, TopStoreHolder.class, topStores, this);
        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
        listTopStore.setLayoutManager(gridManager);
        listTopStore.setAdapter(topStoreAdapter);

        search = view.findViewById(R.id.search);
        btnSearch = view.findViewById(R.id.btnSearch);
    }

    @Override
    public void initListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = search.getText().toString().trim();
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("keySearch", key);
                goToActivity(SearchActivity.class, intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onItemClick(int layoutId, View view, Model item , int position) {
        switch (layoutId) {
            case R.layout.item_purpose:
                Toast.makeText(getContext(), "Pupose", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), FilterResultActivity.class);
                intent.putExtra("purposeId", item.getId());
                goToActivity(FilterResultActivity.class, intent);
                return;
            case R.layout.item_top_store:
                Toast.makeText(getContext(), "top store", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putLong("storeId", item.getId());
                addFragment(R.id.frameLayout, new DetailStoreFragment(), "detailStoreFrag","detailStoreFrag", bundle);

                return;
            case R.layout.item_location:
                Toast.makeText(getContext(), "Location", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getContext(), FilterResultActivity.class);
                intent2.putExtra("locationId", item.getId());
                goToActivity(FilterResultActivity.class, intent2);
                return;
        }
    }
}
