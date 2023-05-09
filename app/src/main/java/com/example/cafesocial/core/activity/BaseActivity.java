package com.example.cafesocial.core.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.List;
import java.util.Map;

public abstract class BaseActivity extends AppCompatActivity {
    protected AwesomeValidation validation;
    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        validation = new AwesomeValidation(ValidationStyle.BASIC);
        manager = getSupportFragmentManager();

        init();
        initView();
        initValidation();

        mapDataToView();
        initEvent();
    }

    public void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void goToActivity(Class activity, Intent intent) {
        startActivity(intent);
    }

    public abstract int getLayoutId();

    public abstract void initView();
    public abstract void initEvent();
    public void init() {

    }

    public void initValidation() {

    }

    public void mapDataToView() {

    }

    public void addFragment(int layoutFrame, Fragment fg, String tag, String name, Bundle bundle) {
        FragmentTransaction transaction = manager.beginTransaction();
        if(bundle != null) {
            fg.setArguments(bundle);
        }
        transaction.add(layoutFrame, fg, tag); // thêm tag để sau pop dc, còn name để sử dụng trong backstack
        transaction.addToBackStack(name);
        transaction.commit();
    }
}
