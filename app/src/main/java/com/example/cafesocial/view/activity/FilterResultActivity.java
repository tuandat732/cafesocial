package com.example.cafesocial.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cafesocial.R;
import com.example.cafesocial.consts.Const;
import com.example.cafesocial.core.activity.BaseActivity;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.listeners.OnInfinityScrollListener;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.dto.request.StoreSaveRequestDto;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.model.StoreSave;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.StoreSaveService;
import com.example.cafesocial.service.iservice.StoreService;
import com.example.cafesocial.utils.Command;
import com.example.cafesocial.view.holder.StoreFilterHolder;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterResultActivity extends BaseActivity implements BaseItemListener {
    private final int REQUEST_FILTER_CODE = 1;
    private ShimmerFrameLayout shimmer;
    private TextView storeCount;
    private RecyclerView reStores;
    private Button btnOpenMap, btnOpenFilter;

    private ArrayList<Long> purposeIds = new ArrayList<>();
    private ArrayList<Long> typeIds = new ArrayList<>();
    private ArrayList<Long> locationIds = new ArrayList<>();
    private ArrayList<Long> benefitIds = new ArrayList<>();
    private boolean isGetAll = true;

    private List<Store> stores = new ArrayList<>();
    private ListAdapter<Store, StoreFilterHolder> storeAdapter;
    private Boolean isBackFromFilter =false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_filter_result;
    }

    @Override
    public void initView() {
        storeCount = findViewById(R.id.storeCount);
        reStores = findViewById(R.id.reStores);
        btnOpenFilter = findViewById(R.id.btnOpenFilter);
        btnOpenMap = findViewById(R.id.btnOpenMap);
        shimmer = findViewById(R.id.shimmer);

        storeAdapter = new ListAdapter<Store, StoreFilterHolder>(R.layout.item_save_store, StoreFilterHolder.class, stores, this);
        storeAdapter.setShimmerLoading(shimmer);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        reStores.setAdapter(storeAdapter);
        reStores.setLayoutManager(manager);
    }

    @Override
    public void initEvent() {
        reStores.addOnScrollListener(new OnInfinityScrollListener(reStores) {
            @Override
            public void fetchData(int totalItem, Command command) {
                callApiFilter(totalItem, command);
            }
        });

        btnOpenFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFrom = getIntent();
                Long locationId = intentFrom.getLongExtra("locationId", -1);
                Long purposeId = intentFrom.getLongExtra("purposeId", -1);
                Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
                intent.putExtra("locationId", locationId);
                intent.putExtra("purposeId", purposeId);
                startActivityForResult(intent, REQUEST_FILTER_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isBackFromFilter) {
            isGetAll = true;
            Intent intent = getIntent();
            Long locationId = intent.getLongExtra("locationId", -1);
            if (locationId != -1) {
                locationIds.clear();
                locationIds.add(locationId);
            }

            Long purposeId = intent.getLongExtra("purposeId", -1);
            if (purposeId != -1) {
                purposeIds.clear();
                purposeIds.add(purposeId);
            }

            stores.clear();
            shimmer.startShimmer();
            callApiFilter(0, () -> {
                shimmer.hideShimmer();
            });
        } else {
            isBackFromFilter = false;
        }
    }

    public void callApiFilter(int skip, Command command) {
        System.out.println(locationIds);
        HttpService.get(StoreService.class).getList(
                null,
                !isGetAll,
                locationIds,
                purposeIds,
                typeIds,
                benefitIds,
                null,
                null,
                skip,
                Const.STORE_LIMIT
        ).enqueue(new Callback<ResponseData<List<Store>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Store>>> call, Response<ResponseData<List<Store>>> response) {
                command.execute();
                if(response.code() ==200) {
                    storeCount.setText(response.body().getData().size()+"");
                    stores.clear();
                    stores.addAll(response.body().getData());
                    storeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Store>>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("hehehehe");
        System.out.println(requestCode);
        System.out.println(requestCode);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_FILTER_CODE && data!=null) {
            isBackFromFilter = true;
            System.out.println("vào dc trong ne");
            purposeIds.clear();
            purposeIds.addAll((ArrayList<Long>) data.getSerializableExtra("purposeIds"));
            locationIds.clear();
            locationIds.addAll((ArrayList<Long>) data.getSerializableExtra("locationIds"));
            typeIds.clear();
            typeIds.addAll((ArrayList<Long>) data.getSerializableExtra("typeIds"));
            benefitIds.clear();
            benefitIds.addAll((ArrayList<Long>) data.getSerializableExtra("benefitIds"));
            isGetAll = data.getBooleanExtra("isGetAll", true);
            stores.clear();
            shimmer.startShimmer();
            callApiFilter(0, ()->{
                shimmer.hideShimmer();
            });
        }
    }

    @Override
    public void onItemClick(int layoutId, View view, Model item, int position) {
        if(view.getId() == R.id.bookmark) {
            if(!UserService.isAuthen()) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                return;
            }
            HttpService.get(StoreSaveService.class).create(UserService.getToken(), new StoreSaveRequestDto(((Store)item).getId())).enqueue(new Callback<ResponseData<Boolean>>() {
                @Override
                public void onResponse(Call<ResponseData<Boolean>> call, Response<ResponseData<Boolean>> response) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("fragment", "saveStore");
                    goToActivity(MainActivity.class, intent);
                }

                @Override
                public void onFailure(Call<ResponseData<Boolean>> call, Throwable t) {

                }
            });
        }
    }
}