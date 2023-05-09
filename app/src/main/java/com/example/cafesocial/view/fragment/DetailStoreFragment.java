package com.example.cafesocial.view.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.fragment.BaseFragment;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.dto.request.StoreSaveRequestDto;
import com.example.cafesocial.model.Benefit;
import com.example.cafesocial.model.Post;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.BenefitService;
import com.example.cafesocial.service.iservice.PostService;
import com.example.cafesocial.service.iservice.StoreSaveService;
import com.example.cafesocial.service.iservice.StoreService;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.view.activity.AddPostActivity;
import com.example.cafesocial.view.activity.LoginActivity;
import com.example.cafesocial.view.activity.MapActivity;
import com.example.cafesocial.view.holder.BenefitHolder;
import com.example.cafesocial.view.holder.PostHolder;
import com.example.cafesocial.view.holder.TopStoreHolder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStoreFragment extends BaseFragment implements BaseItemListener, OnMapReadyCallback {
    private TextView storeName, storeLocation, btnViewMap, btnViewMenu, btnViewGo, storeRateTotal,
            storeRateString, storeRateCount, storeRateLocation, storeRateSpace, storeRateMenu,
            storeRateService, storeRatePrice, storePrice, storeTime, storePhone, storeTag,
            btnWriteRate, btnViewMore;
    private ProgressBar storeRateLocationBar, storeRateSpaceBar, storeRateMenuBar,
            storeRateServiceBar, storeRatePriceBar;
    private ImageView btnSave;
    private RecyclerView listUtils, listPosts, listStoreNears;

    private Store store = null;
    private List<Post> posts = new ArrayList<>();
    private List<Store> storesNear = new ArrayList<>();
    private List<Benefit> benefits = new ArrayList<>();

    private ListAdapter<Post, PostHolder> postAdapter;
    private ListAdapter<Benefit, BenefitHolder> benefitAdapter;
    private ListAdapter<Store, TopStoreHolder> storeNearAdapter;

    private SupportMapFragment mapFragment;
    private GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_store, container, false);
    }

    public void initView(View view) {
        storeName = view.findViewById(R.id.storeName);
        storeLocation = view.findViewById(R.id.storeLocation);
        btnViewMap = view.findViewById(R.id.btnViewMap);

        btnViewGo = view.findViewById(R.id.btnViewGo);
        btnViewMenu = view.findViewById(R.id.btnViewMenu);
        storeRateTotal = view.findViewById(R.id.storeRateTotal);
        storeRateString = view.findViewById(R.id.storeRateString);
        storeRateCount = view.findViewById(R.id.storeRateCount);
        storeRateMenu = view.findViewById(R.id.storeRateMenu);
        storeRateLocation = view.findViewById(R.id.storeRateLocation);
        storeRateSpace = view.findViewById(R.id.storeRateSpace);
        storeRateService = view.findViewById(R.id.storeRateService);
        storeRatePrice = view.findViewById(R.id.storeRatePrice);
        storeRateLocationBar = view.findViewById(R.id.storeRateLocationPBar);
        storeRateMenuBar = view.findViewById(R.id.storeRateMenuPBar);
        storeRateSpaceBar = view.findViewById(R.id.storeRateSpacePBar);
        storeRatePriceBar = view.findViewById(R.id.storeRatePricePBar);
        storeRateServiceBar = view.findViewById(R.id.storeRateServicePBar);
        storePrice = view.findViewById(R.id.storePrice);
        storeTime = view.findViewById(R.id.storeTime);
        storeTag = view.findViewById(R.id.storeTag);
        storePhone = view.findViewById(R.id.storePhone);
        btnWriteRate = view.findViewById(R.id.btnWriteRate);
        btnViewMore = view.findViewById(R.id.btnViewMore);
        btnSave = view.findViewById(R.id.btnSave);

        listUtils = view.findViewById(R.id.listUtils);
        listPosts = view.findViewById(R.id.listRate);
        listStoreNears = view.findViewById(R.id.listStoreNear);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initRecyclerView();
    }

    public void initRecyclerView() {
        postAdapter = new ListAdapter<>(R.layout.item_post, PostHolder.class, posts, this);
        LinearLayoutManager postManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        listPosts.setLayoutManager(postManager);
        listPosts.setAdapter(postAdapter);

        benefitAdapter = new ListAdapter<>(R.layout.item_benefit, BenefitHolder.class, benefits, this);
        LinearLayoutManager benefitManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        listUtils.setLayoutManager(benefitManager);
        listUtils.setAdapter(benefitAdapter);

        storeNearAdapter = new ListAdapter<>(R.layout.item_top_store, TopStoreHolder.class, storesNear, this);
        LinearLayoutManager storeNearManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        listStoreNears.setAdapter(storeNearAdapter);
        listStoreNears.setLayoutManager(storeNearManager);

    }

    public void setDataStoreToView() {
        storeName.setText(store.getName());
        storeLocation.setText(store.getLocation().getName());

        storeRateTotal.setText(store.getTotalRateCount()+"");
        storeRateString.setText(store.getRateString());
        storeRateCount.setText(store.getTotalPost()+"");

        storeRateMenu.setText(store.getTotalRateMenu()+"");
        storeRateLocation.setText(store.getTotalRateLocation()+"");
        storeRateSpace.setText(store.getTotalRateSpace()+"");
        storeRateService.setText(store.getTotalRateService()+"");
        storeRatePrice.setText(store.getTotalRatePrice()+"");
        storeRateLocationBar.setProgress(store.getProgressPercen(store.getTotalRateLocation(), 5));
        storeRateMenuBar.setProgress(store.getProgressPercen(store.getTotalRateMenu(), 5));
        storeRateSpaceBar.setProgress(store.getProgressPercen(store.getTotalRateSpace(), 5));
        storeRatePriceBar.setProgress(store.getProgressPercen(store.getTotalRatePrice(), 5));
        storeRateServiceBar.setProgress(store.getProgressPercen(store.getTotalRateService(), 5));

        storePrice.setText(store.getPriceStart() + " - "+store.getPriceEnd());
        storeTime.setText(store.getTimeOpen() + " - "+store.getTimeClose());

        storeTag.setText(store.getStoreTypeTag());
        storePhone.setText(store.getPhone());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng position = new LatLng(12.432432, 56.76767);
        if(store != null) {
            position = new LatLng(store.getLatitude(), store.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(position)
                    .title(store.getName())
                    .snippet(store.getAddress()));
        }
//        map.addMarker(new MarkerOptions()
//                .position(position)
//                .title(store.getName())
//                .snippet(store.getAddress()));
        map.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    @Override
    public void init() {
        HttpService.get(StoreService.class).getTopStore("desc").enqueue(new Callback<ResponseData<List<Store>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Store>>> call, Response<ResponseData<List<Store>>> response) {
                if(response.code() == 200) {
                    storesNear.clear();
                    storesNear.addAll(response.body().getData());
                    storeNearAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Store>>> call, Throwable t) {
                System.out.println("goi store near loi");
                System.out.println(t);
                Toastify.toastError(getContext(), null);
            }
        });

        Bundle arguments = getArguments();
        Long idStore = arguments.getLong("storeId", 1);
        HttpService.get(StoreService.class).getById(idStore).enqueue(new Callback<ResponseData<Store>>() {
            @Override
            public void onResponse(Call<ResponseData<Store>> call, Response<ResponseData<Store>> response) {
                if(response.code() == 200) {
                    store = response.body().getData();
                    UserService.addStore(store);
                    setDataStoreToView();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Store>> call, Throwable t) {
                System.out.println("get store by id loi");
                System.out.println(t);
                Toastify.toastError(getContext(), null);
            }
        });

        HttpService.get(PostService.class).findAll(5, 0).enqueue(new Callback<ResponseData<List<Post>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Post>>> call, Response<ResponseData<List<Post>>> response) {
                if(response.code() == 200) {
                    posts.clear();
                    System.out.println("tesss");
                    System.out.println(response.body());
                    posts.addAll(response.body().getData());
                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Post>>> call, Throwable t) {
                System.out.println("get post loi");
                System.out.println(t);
            }
        });

        HttpService.get(BenefitService.class).getList().enqueue(new Callback<ResponseData<List<Benefit>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Benefit>>> call, Response<ResponseData<List<Benefit>>> response) {
                if(response.code() == 200) {
                    benefits.clear();
                    benefits.addAll(response.body().getData());
                    benefitAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Benefit>>> call, Throwable t) {
                Toastify.toastError(getContext(), null);
            }
        });
    }

    @Override
    public void initListener() {
        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                intent.putExtra("store", store);
                goToActivity(MapActivity.class, intent);
            }
        });
        btnWriteRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPostActivity.class);
                intent.putExtra("store", store);
                goToActivity(AddPostActivity.class, intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UserService.isAuthen()) {
                    goToActivity(LoginActivity.class);
                    return;
                }
                System.out.println("click nut save");
                HttpService.get(StoreSaveService.class).create(UserService.getToken(), new StoreSaveRequestDto(store.getId())).enqueue(new Callback<ResponseData<Boolean>>() {
                    @Override
                    public void onResponse(Call<ResponseData<Boolean>> call, Response<ResponseData<Boolean>> response) {
                        if(response.code() == 200) {
                            System.out.println("success cal store save");
                            addFragment(R.id.frameLayout, new SaveFragment(), "saveFrag", "saveFrag", null);
                        } else {
                            System.out.println("fail call stỏe save");
                            Toastify.toastError(getContext(), null);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData<Boolean>> call, Throwable t) {
                        System.out.println("loi cal store save");
                        Toastify.toastError(getContext(), null);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        Long idStore = arguments.getLong("storeId", 1);
        HttpService.get(StoreService.class).getById(idStore).enqueue(new Callback<ResponseData<Store>>() {
            @Override
            public void onResponse(Call<ResponseData<Store>> call, Response<ResponseData<Store>> response) {
                if(response.code() == 200) {
                    store = response.body().getData();
                    UserService.addStore(store);
                    setDataStoreToView();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Store>> call, Throwable t) {
                System.out.println("get store by id loi");
                System.out.println(t);
                Toastify.toastError(getContext(), null);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onItemClick(int layoutId, View view, Model item , int position) {
        switch (layoutId) {
            case R.layout.item_top_store:
                Bundle bundle = new Bundle();
                bundle.putLong("storeId", item.getId());
                addFragment(R.id.frameLayout, new DetailStoreFragment(), "detailStoreFrag","detailStoreFrag", bundle);

                return;
        }
    }
}
