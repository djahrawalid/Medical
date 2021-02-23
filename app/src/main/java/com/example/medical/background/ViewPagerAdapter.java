package com.example.medical.background;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public ViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments=fragments;
        this.titles=titles;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
       return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       return titles.get(position);
    }

    @Override
    public int getCount() {
        return  fragments.size();
    }
}