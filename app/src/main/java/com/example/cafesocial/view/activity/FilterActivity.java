package com.example.cafesocial.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.cafesocial.R;
import com.example.cafesocial.core.activity.BaseActivity;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Benefit;
import com.example.cafesocial.model.Location;
import com.example.cafesocial.model.Purpose;
import com.example.cafesocial.model.Type;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.iservice.BenefitService;
import com.example.cafesocial.service.iservice.LocationService;
import com.example.cafesocial.service.iservice.PurposeService;
import com.example.cafesocial.service.iservice.TypeService;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.view.holder.FilterSelectHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends BaseActivity implements BaseItemListener {
    private TextView filterTime, filterPurpose, filterBenefit, filterType, filterLocation;
    private ImageView btnClose;
    private Button btnApply, btnClear;
    private RadioGroup radioGroup;
    private RadioButton radioAll, radioIsOpen;

    private List<Purpose> purposes = new ArrayList<>();
    private List<Type> types = new ArrayList<>();
    private List<Benefit> benefits = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();

    private ListAdapter<Purpose, FilterSelectHolder> purposeAdapter;
    private ListAdapter<Location, FilterSelectHolder> locationAdapter;
    private ListAdapter<Benefit, FilterSelectHolder> benefitAdapter;
    private ListAdapter<Type, FilterSelectHolder> typeAdapter;

    private RecyclerView reLocation, rePurpose, reType, reBenefit;

    private boolean isOpenFilterTime = true
            , isOpenFilterPurpose=true
            , isOpenFilterBenefit=true
            , isOpenFilterType=true
            , isOpenFilterLocation=true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_filter;
    }

    @Override
    public void initView() {
        filterTime = findViewById(R.id.filterTime);
        filterLocation = findViewById(R.id.filterLocation);
        filterPurpose = findViewById(R.id.filterPurpose);
        filterBenefit = findViewById(R.id.filterBenefit);
        filterType = findViewById(R.id.filterType);
        btnClose = findViewById(R.id.btnCloseFilter);
        btnApply = findViewById(R.id.btnApply);
        btnClear = findViewById(R.id.btnClear);
        radioGroup = findViewById(R.id.radioGroup);
        radioAll = findViewById(R.id.radioAll);
        radioIsOpen = findViewById(R.id.radioOpen);

        reLocation = findViewById(R.id.reLocation);
        reBenefit = findViewById(R.id.reBenefit);
        rePurpose = findViewById(R.id.rePurpose);
        reType = findViewById(R.id.reType);

        initRecyclerView();
    }

    public void initRecyclerView() {
        GridLayoutManager locationManager = new GridLayoutManager(getApplicationContext(), 2);
        locationAdapter = new ListAdapter<Location, FilterSelectHolder>(R.layout.item_filter_select, FilterSelectHolder.class, locations, this);
        reLocation.setAdapter(locationAdapter);
        reLocation.setLayoutManager(locationManager);

        GridLayoutManager typeManager = new GridLayoutManager(getApplicationContext(), 2);
        typeAdapter = new ListAdapter<Type, FilterSelectHolder>(R.layout.item_filter_select, FilterSelectHolder.class, types, this);
        reType.setAdapter(typeAdapter);
        reType.setLayoutManager(typeManager);

        GridLayoutManager benefitManager = new GridLayoutManager(getApplicationContext(), 2);
        benefitAdapter = new ListAdapter<Benefit, FilterSelectHolder>(R.layout.item_filter_select, FilterSelectHolder.class, benefits, this);
        reBenefit.setAdapter(benefitAdapter);
        reBenefit.setLayoutManager(benefitManager);

        GridLayoutManager purposeManager = new GridLayoutManager(getApplicationContext(), 2);
        purposeAdapter = new ListAdapter<Purpose, FilterSelectHolder>(R.layout.item_filter_select, FilterSelectHolder.class, purposes, this);
        rePurpose.setAdapter(purposeAdapter);
        rePurpose.setLayoutManager(purposeManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        HttpService.get(LocationService.class).getList().enqueue(new Callback<ResponseData<List<Location>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Location>>> call, Response<ResponseData<List<Location>>> response) {
                if(response.code() == 200) {
                    locations.clear();;
                    locations.addAll(response.body().getData());
                    locations.forEach(item -> {
                        item.setIsSelect(false);
                    });
                    Long locationId = intent.getLongExtra("locationId", -1);
                    if(locationId != -1) {
                        locations.forEach(location -> {
                            if(location.getId() == locationId)
                                location.setIsSelect(true);
                        });
                    }
                    locationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Location>>> call, Throwable t) {
                System.out.println("fallll");
            }
        });

        HttpService.get(PurposeService.class).getList().enqueue(new Callback<ResponseData<List<Purpose>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Purpose>>> call, Response<ResponseData<List<Purpose>>> response) {
                if(response.code() == 200){
                    purposes.clear();
                    purposes.addAll(response.body().getData());
                    purposes.forEach(item -> {
                        item.setIsSelect(false);
                    });
                    Long purposeId = intent.getLongExtra("purposeId", -1);
                    if(purposeId != -1) {
                        purposes.forEach(purpose -> {
                            if(purpose.getId() == purposeId) purpose.setIsSelect(true);
                        });
                    }
                    purposeAdapter.notifyDataSetChanged();
                } else {
                    Toastify.toastError(getApplicationContext(), null);
                }

            }

            @Override
            public void onFailure(Call<ResponseData<List<Purpose>>> call, Throwable t) {
                System.out.println("fallll");
                Toastify.toastError(getApplicationContext(), null);
            }
        });

        HttpService.get(BenefitService.class).getList().enqueue(new Callback<ResponseData<List<Benefit>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Benefit>>> call, Response<ResponseData<List<Benefit>>> response) {
                if(response.code() ==200) {
                    benefits.clear();
                    benefits.addAll(response.body().getData());
                    benefits.forEach(item -> {
                        item.setIsSelect(false);
                    });
                    benefitAdapter.notifyDataSetChanged();
                } else {
                    Toastify.toastError(getApplicationContext(), null);
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Benefit>>> call, Throwable t) {
                System.out.println("calll bene fall");
                Toastify.toastError(getApplicationContext(), null);
            }
        });

        HttpService.get(TypeService.class).getList().enqueue(new Callback<ResponseData<List<Type>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Type>>> call, Response<ResponseData<List<Type>>> response) {
                if(response.code()==200) {
                    types.clear();
                    types.addAll(response.body().getData());
                    types.forEach(item -> {
                        item.setIsSelect(false);
                    });
                    typeAdapter.notifyDataSetChanged();
                } else {
                    Toastify.toastError(getApplicationContext(), null);
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Type>>> call, Throwable t) {
                Toastify.toastError(getApplicationContext(), null);
            }
        });

    }

    @Override
    public void initEvent() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        filterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpenFilterTime) {
                    isOpenFilterTime = false;
                    radioGroup.setVisibility(View.GONE);
                } else {
                    isOpenFilterTime =true;
                    radioGroup.setVisibility(View.VISIBLE);
                }
            }
        });

        filterLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpenFilterLocation) {
                    isOpenFilterLocation= false;
                    reLocation.setVisibility(View.GONE);
                } else {
                    isOpenFilterLocation= true;
                    reLocation.setVisibility(View.VISIBLE);
                }
            }
        });

        filterType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpenFilterType) {
                    isOpenFilterType= false;
                    reType.setVisibility(View.GONE);
                } else {
                    isOpenFilterType= true;
                    reType.setVisibility(View.VISIBLE);
                }
            }
        });

        filterBenefit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpenFilterBenefit) {
                    isOpenFilterBenefit= false;
                    reBenefit.setVisibility(View.GONE);
                } else {
                    isOpenFilterBenefit= true;
                    reBenefit.setVisibility(View.VISIBLE);
                }
            }
        });

        filterPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpenFilterPurpose) {
                    isOpenFilterPurpose= false;
                    rePurpose.setVisibility(View.GONE);
                } else {
                    isOpenFilterPurpose= true;
                    rePurpose.setVisibility(View.VISIBLE);
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purposes.forEach(item -> item.setIsSelect(false));
                locations.forEach(item -> item.setIsSelect(false));
                benefits.forEach(item -> item.setIsSelect(false));
                types.forEach(item -> item.setIsSelect(false));
                typeAdapter.notifyDataSetChanged();
                locationAdapter.notifyDataSetChanged();
                benefitAdapter.notifyDataSetChanged();
                purposeAdapter.notifyDataSetChanged();

            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Long> listIdLocation = new ArrayList<>();
                ArrayList<Long> listIdType = new ArrayList<>();
                ArrayList<Long> listIdBenefit = new ArrayList<>();
                ArrayList<Long> listIdPurpose = new ArrayList<>();
                boolean isGetAll = false;
                if(radioAll.isChecked()) isGetAll = true;
                else if(radioIsOpen.isChecked()) isGetAll = false;

                purposes.forEach(item -> {
                    if(item.getIsSelect()) listIdPurpose.add(item.getId());
                });
                locations.forEach(item -> {
                    if(item.getIsSelect()) listIdLocation.add(item.getId());
                });
                types.forEach(item -> {
                    if(item.getIsSelect()) listIdType.add(item.getId());
                });
                benefits.forEach(item -> {
                    if(item.getIsSelect()) listIdBenefit.add(item.getId());
                });

                Intent intent = new Intent();
                intent.putExtra("purposeIds", listIdPurpose);
                intent.putExtra("locationIds", listIdLocation);
                intent.putExtra("typeIds", listIdType);
                intent.putExtra("benefitIds", listIdBenefit);
                intent.putExtra("isGetAll", isGetAll);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(int layoutId, View view, Model item, int position) {
        if(item instanceof Type) {
            types.get(position).setIsSelect(!types.get(position).getIsSelect());
            typeAdapter.notifyDataSetChanged();
        } else if(item instanceof Benefit) {
            benefits.get(position).setIsSelect(!benefits.get(position).getIsSelect());
            benefitAdapter.notifyDataSetChanged();
        } else if(item instanceof Location){
            locations.get(position).setIsSelect(!locations.get(position).getIsSelect());
            locationAdapter.notifyDataSetChanged();
        } else if(item instanceof Purpose) {
            purposes.get(position).setIsSelect(!purposes.get(position).getIsSelect());
            purposeAdapter.notifyDataSetChanged();
        }
    }
}