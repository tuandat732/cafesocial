package com.example.cafesocial.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cafesocial.R;
import com.example.cafesocial.core.activity.BaseActivity;
import com.example.cafesocial.model.Store;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {
    private TextView name;
    private ImageView btnClose;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Store store = null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void initView() {
        name = findViewById(R.id.name);
        btnClose = findViewById(R.id.btnClose);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void initEvent() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra("store");
        if(store == null) {
            finish();
        }else {
            name.setText(store.getName());
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng position = new LatLng(store.getLatitude(), store.getLongitude());
        map.addMarker(new MarkerOptions()
                .position(position)
                .title(store.getName())
                .snippet(store.getAddress()));
        map.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}