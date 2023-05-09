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
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.fragment.BaseFragment;
import com.example.cafesocial.core.listeners.OnInfinityScrollListener;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.dto.request.StoreSaveRequestDto;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.model.StoreSave;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.StoreSaveService;
import com.example.cafesocial.utils.Command;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.view.activity.LoginActivity;
import com.example.cafesocial.view.holder.SaveStoreHolder;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveFragment extends BaseFragment implements BaseItemListener {

    private List<StoreSave> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListAdapter<StoreSave, SaveStoreHolder> adapter;
    private ShimmerFrameLayout shimmer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("vô save");
        return inflater.inflate(R.layout.fragment_save_store, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!UserService.isAuthen()) {
            goToActivity(LoginActivity.class);
        }

        list.clear();
        shimmer.startShimmer();
        fetchListStoreSave(0, ()->{
            shimmer.hideShimmer();
        });
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        shimmer = view.findViewById(R.id.shimmer);
        recyclerView = view.findViewById(R.id.listSaveStore);

        adapter = new ListAdapter<StoreSave, SaveStoreHolder>(R.layout.item_save_store, SaveStoreHolder.class, list, this);
        adapter.setShimmerLoading(shimmer);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void init() {

    }

    @Override
    public void initListener() {
        super.initListener();

        recyclerView.addOnScrollListener(new OnInfinityScrollListener(recyclerView) {
            @Override
            public void fetchData(int totalItem, Command command) {
                fetchListStoreSave(totalItem, command);
            }
        });
    }

    public void fetchListStoreSave(int totalItem, Command command) {
        HttpService.get(StoreSaveService.class).getList(UserService.getToken(), Const.STORE_LIMIT, totalItem).enqueue(new Callback<ResponseData<List<StoreSave>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<StoreSave>>> call, Response<ResponseData<List<StoreSave>>> response) {
                command.execute();
                if(response.code() ==200) {
                    if(response.body().getData().size() > 0) {
                        list.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseData<List<StoreSave>>> call, Throwable t) {
                System.out.println("fialll");
            }
        });
    }

    @Override
    public void onItemClick(int layoutId, View view, Model item, int position) {
        if(view.getId() == R.id.bookmark) {
            HttpService.get(StoreSaveService.class).delete(UserService.getToken(), new StoreSaveRequestDto(((StoreSave)item).getStoreId())).enqueue(new Callback<ResponseData<Boolean>>() {
                @Override
                public void onResponse(Call<ResponseData<Boolean>> call, Response<ResponseData<Boolean>> response) {
                    list.remove(item);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ResponseData<Boolean>> call, Throwable t) {
                    Toastify.toastError(getContext(), null);
                }
            });
        }
    }
}
