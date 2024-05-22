package com.example.doancuoiki.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;
import com.google.android.material.tabs.TabLayout;

import Custom_Adapter.KHPagerAdapter;

public class LKHFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button btnNewKH;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           View view = inflater.inflate(R.layout.fragment_lkh, container, false);

        tabLayout = view.findViewById(R.id.tabLayoutLKH);
        viewPager = view.findViewById(R.id.viewPagerLKH);
        btnNewKH = view.findViewById(R.id.btnNewKH);

        btnNewKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new NewKHFragment())
                            .addToBackStack("4")
                            .commit();
                }
            }
        });

        KHPagerAdapter adapter = new KHPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Đang Áp Dụng");
        tabLayout.getTabAt(1).setText("Đã Kết Thúc");
        return view;
    }
}
