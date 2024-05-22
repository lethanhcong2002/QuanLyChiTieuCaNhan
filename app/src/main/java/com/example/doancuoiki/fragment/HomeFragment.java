package com.example.doancuoiki.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import BangDuLieu.GiaoDich;
import BangDuLieu.KhachHang;
import BangDuLieu.ThuChiTungNhom;
import Custom_Adapter.GD_RecylerView_Adapter;
import Custom_Adapter.GiaoDichAdapter;
import Custom_Adapter.SliderAdapter;
import Support.AppData;
import XuLyDuLieu.DAO_DanhMuc_Nhom;
import XuLyDuLieu.DAO_GiaoDich;

public class HomeFragment extends Fragment {

    DAO_DanhMuc_Nhom dao;
    DAO_GiaoDich db;
    List<ThuChiTungNhom> tn,ct;
    KhachHang kh;
    List<GiaoDich> lst;
    LinearLayoutManager layoutManagerThuNhap, layoutManagerChiTieu;
    int itemsCountThuNhap, itemsCountChiTieu;
    Handler handlerThuNhap, handlerChiTieu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        kh = AppData.getInstance().getKhachHang();
        db = new DAO_GiaoDich(getContext());
        dao = new DAO_DanhMuc_Nhom(getContext());
        Date currDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        List<Double> tongtien = db.getTongTienNgay(kh.getID(), dateFormat.format(currDate));
        lst = db.getAllGiaoDich(kh.getID());
        tn = dao.getTop5NhomByTotalTien(kh.getID(), 1);
        ct = dao.getTop5NhomByTotalTien(kh.getID(), 0);
        itemsCountThuNhap = tn.size();
        itemsCountChiTieu = ct.size();
        handlerThuNhap = new Handler();
        handlerChiTieu = new Handler();

        TextView TongThuNhap = view.findViewById(R.id.txtTongThuNhap);
        TextView TongChiTieu = view.findViewById(R.id.txtTongChiTieu);
        TongThuNhap.setText(AppData.getInstance().formatLargeNumber(tongtien.get(1).longValue()));
        TongChiTieu.setText(AppData.getInstance().formatLargeNumber(tongtien.get(0).longValue()));

        RecyclerView rv = view.findViewById((R.id.lst_qltc));
        GD_RecylerView_Adapter gd = new GD_RecylerView_Adapter(lst, getContext());
        gd.setClickListener(new GD_RecylerView_Adapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                AppData.getInstance().setGiaoDich(lst.get(position));
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new DetailFragment())
                            .addToBackStack("0")
                            .commit();
                }
            }
        });
        rv.setAdapter(gd);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        layoutManagerThuNhap = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerChiTieu = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView rvIncome = view.findViewById(R.id.rvInCome);
        rvIncome.setLayoutManager(layoutManagerThuNhap);
        if (tn == null || tn.isEmpty()) {
            tn = new ArrayList<>();
            tn.add(new ThuChiTungNhom("Default", 0.0));
        }
        SliderAdapter thunhap = new SliderAdapter(tn);
        rvIncome.setAdapter(thunhap);
        SlideMuotThuNhap();

        RecyclerView rvExpense = view.findViewById(R.id.rvExpense);
        rvExpense.setLayoutManager(layoutManagerChiTieu);
        if (ct == null || ct.isEmpty()) {
            ct = new ArrayList<>();
            ct.add(new ThuChiTungNhom("Default", 0.0));
        }
        SliderAdapter chitieu = new SliderAdapter(ct);
        rvExpense.setAdapter(chitieu);
        SlideMuotChiTieu();

        return view;
    }

    public void SlideMuotThuNhap()
    {
        final int visibleItemsCount = 2;

        final Handler handler = handlerThuNhap;

        final LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getHorizontalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 0.5f;
            }
        };

        final Runnable autoScrollRunnable = new Runnable() {
            int currentFirstVisibleItem = 0;

            @Override
            public void run() {
                if (currentFirstVisibleItem + visibleItemsCount < itemsCountThuNhap) {
                    currentFirstVisibleItem++;
                    smoothScroller.setTargetPosition(currentFirstVisibleItem);
                    layoutManagerThuNhap.startSmoothScroll(smoothScroller);
                } else {
                    currentFirstVisibleItem = 0;
                    smoothScroller.setTargetPosition(currentFirstVisibleItem);
                    layoutManagerThuNhap.startSmoothScroll(smoothScroller);
                }

                handler.postDelayed(this, 4000);
            }
        };
        handler.post(autoScrollRunnable);
    }

    public void SlideMuotChiTieu()
    {
        final int visibleItemsCount = 2;

        final Handler handler = handlerChiTieu;

        final LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getHorizontalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 0.5f;
            }
        };

        final Runnable autoScrollRunnable = new Runnable() {
            int currentFirstVisibleItem = 0;

            @Override
            public void run() {
                if (currentFirstVisibleItem + visibleItemsCount < itemsCountChiTieu) {
                    currentFirstVisibleItem++;
                    smoothScroller.setTargetPosition(currentFirstVisibleItem);
                    layoutManagerChiTieu.startSmoothScroll(smoothScroller);
                } else {
                    currentFirstVisibleItem = 0;
                    smoothScroller.setTargetPosition(currentFirstVisibleItem);
                    layoutManagerChiTieu.startSmoothScroll(smoothScroller);
                }

                handler.postDelayed(this, 4000);
            }
        };
        handler.post(autoScrollRunnable);
    }

}