package com.example.doancuoiki.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.GiaoDich;
import BangDuLieu.KhachHang;
import Custom_Adapter.GD_RecylerView_Adapter;
import Custom_Adapter.GiaoDichAdapter;
import Support.AppData;
import XuLyDuLieu.DAO_GiaoDich;

public class IncomeFragment extends Fragment {
    KhachHang kh;
    RecyclerView lst;
    DAO_GiaoDich dao;
    List<GiaoDich> gd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        dao = new DAO_GiaoDich(getContext());
        kh = AppData.getInstance().getKhachHang();
        gd = dao.getGiaoDichByTrangThai(kh.getID(), 1);
        lst = view.findViewById(R.id.lst_ThuNhap);
        GD_RecylerView_Adapter gdAdapter = new GD_RecylerView_Adapter(gd, getContext());
        gdAdapter.setClickListener(new GD_RecylerView_Adapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                AppData.getInstance().setGiaoDich(gd.get(position));
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new DetailFragment())
                            .addToBackStack("0")
                            .commit();
                }
            }
        });
        lst.setAdapter(gdAdapter);
        lst.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
