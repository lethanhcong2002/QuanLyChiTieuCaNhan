package com.example.doancuoiki.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.doancuoiki.R;
import com.google.android.material.tabs.TabLayout;

import Custom_Adapter.QLTCPagerAdapter;

public class QLTCFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qltc, container, false);

        // Ánh xạ TabLayout và ViewPager từ layout
        tabLayout = view.findViewById(R.id.tabLayoutQLTC);
        viewPager = view.findViewById(R.id.viewPagerQLTC);

        // Tạo và thiết lập adapter cho ViewPager
        QLTCPagerAdapter adapter = new QLTCPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        // Kết nối TabLayout với ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // Đặt tiêu đề cho từng tab
        tabLayout.getTabAt(0).setText("Thu Nhập");
        tabLayout.getTabAt(1).setText("Chi Tiêu");

        return view;
    }
}

