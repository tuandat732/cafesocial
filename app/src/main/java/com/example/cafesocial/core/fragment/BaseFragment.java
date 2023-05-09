package com.example.cafesocial.core.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class BaseFragment extends Fragment {
    protected AwesomeValidation validation ;
    private FragmentManager manager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        validation = new AwesomeValidation(ValidationStyle.BASIC);
        manager = getActivity().getSupportFragmentManager();

        init();
        initView(view);
        mapDataToView();
        initListener();
    }

    public void init() {

    }

    public void initView(View view) {

    }

    public void initListener() {

    }

    public void initValidation() {

    }

    public void goToActivity(Class activity) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
    }

    public void goToActivity(Class activity, Intent intent) {
        startActivity(intent);
    }

    public void mapDataToView() {

    }

    public void addFragment(int layoutId, Fragment fg, String tag, String name, Bundle bundle) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction();
        if(bundle != null)
            fg.setArguments(bundle);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(layoutId, fg, tag); // thêm tag để sau pop dc, còn name để sử dụng trong backstack
        transaction.addToBackStack(name);
        transaction.commit();
    }
}
