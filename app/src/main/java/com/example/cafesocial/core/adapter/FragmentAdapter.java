package com.example.cafesocial.core.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.cafesocial.view.fragment.AccountFragment;
import com.example.cafesocial.view.fragment.DiscoverFragment;
import com.example.cafesocial.view.fragment.HomeFrament;
import com.example.cafesocial.view.fragment.SaleFragment;
import com.example.cafesocial.view.fragment.SaveFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles;

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public FragmentAdapter(@NonNull FragmentManager fm,int behavior ,List<Fragment> fragmentList, String[] titles) {
        super(fm, behavior);
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        System.out.println("po "+position);
//        switch (position) {
//            case 0:
//                return new HomeFrament();
//            case 1:
//                return new DiscoverFragment();
//            case 2:
//                return new SaleFragment();
//            case 3:
//                return new SaveFragment();
//            case 4:
//                return new AccountFragment();
//        }
//        return null;

        for (int i=0; i < fragmentList.size(); i++) {
            if(i == position) {
                System.out.println(fragmentList.get(i));
                return fragmentList.get(i);
            }
        }
        return fragmentList.get(0);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
