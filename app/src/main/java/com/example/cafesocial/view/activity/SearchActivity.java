package com.example.cafesocial.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.cafesocial.R;
import com.example.cafesocial.core.activity.BaseActivity;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.StoreService;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.view.holder.SearchStoreHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    private SearchView searchView;
    private TextView btnClose, recentlyText;
    private RecyclerView listSuggest, listRecently;

    private ListAdapter<Store, SearchStoreHolder> adapterSuggest;
    private ListAdapter<Store, SearchStoreHolder> adapterRecently;

    private List<Store> storeSuggests = new ArrayList<>();
    private List<Store> storeRecentlys = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        searchView = findViewById(R.id.search);
        btnClose = findViewById(R.id.close);
        recentlyText = findViewById(R.id.recentlyText);
        listRecently = findViewById(R.id.listRecently);
        listSuggest = findViewById(R.id.listSuggest);

        adapterRecently = new ListAdapter<>(R.layout.item_search, SearchStoreHolder.class, storeRecentlys);
        LinearLayoutManager recentlyManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        listRecently.setAdapter(adapterRecently);
        listRecently.setLayoutManager(recentlyManager);

        adapterSuggest = new ListAdapter<>(R.layout.item_search, SearchStoreHolder.class, storeSuggests);
        LinearLayoutManager suggestManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        listSuggest.setAdapter(adapterSuggest);
        listSuggest.setLayoutManager(suggestManager);
    }

    @Override
    public void init() {
        // call api in here
//        if(UserService.isAuthen()) {
//            HttpService.get(StoreService.class).getListStoreViewByUser(UserService.getToken()).enqueue(new Callback<ResponseData<List<Store>>>() {
//                @Override
//                public void onResponse(Call<ResponseData<List<Store>>> call, Response<ResponseData<List<Store>>> response) {
//                    if(response.code()==200) {
//                        storeRecentlys.clear();
//                        storeRecentlys.addAll(response.body().getData());
//                        adapterRecently.notifyDataSetChanged();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseData<List<Store>>> call, Throwable t) {
//
//                }
//            });
//        }
    }

    public void getKeySearchFromIntent() {
        Intent intent = getIntent();
        String key = intent.getStringExtra("keySearch");
        if(key != null) {
            searchView.setQuery(key, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getKeySearchFromIntent();

//        if(!UserService.isAuthen()) {
//            recentlyText.setVisibility(View.INVISIBLE);
//            listRecently.setVisibility(View.INVISIBLE);
//        }
        storeRecentlys.clear();
        storeRecentlys.addAll(UserService.getStores());
        adapterRecently.notifyDataSetChanged();
    }

    @Override
    public void mapDataToView() {
        getKeySearchFromIntent();
    }

    @Override
    public void initEvent() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // call api then set new list to suggest adapter
                HttpService.get(StoreService.class).searchStoreByName(query).enqueue(new Callback<ResponseData<List<Store>>>() {
                    @Override
                    public void onResponse(Call<ResponseData<List<Store>>> call, Response<ResponseData<List<Store>>> response) {
                        if(response.code() == 200) {
                            storeSuggests.clear();
                            storeSuggests.addAll(response.body().getData());
                            adapterSuggest.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData<List<Store>>> call, Throwable t) {
                        Toastify.toastError(getApplicationContext(), null);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
    }
}