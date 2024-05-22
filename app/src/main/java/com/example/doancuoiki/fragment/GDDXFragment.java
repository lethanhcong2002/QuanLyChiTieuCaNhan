package com.example.doancuoiki.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doancuoiki.R;

import java.util.ArrayList;
import java.util.List;

import BangDuLieu.GiaoDich;
import BangDuLieu.KhachHang;
import Custom_Adapter.GiaoDichDaXoaAdapter;
import Support.AppData;
import XuLyDuLieu.DAO_GiaoDich;

public class GDDXFragment extends Fragment {
    ListView lstGDDX;

    List<GiaoDich> list;

    GiaoDichDaXoaAdapter adapter;

    GiaoDich gd;
    KhachHang kh;
    DAO_GiaoDich dao;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gddx, container, false);
        lstGDDX = view.findViewById(R.id.lstGDDX);
        kh = AppData.getInstance().getKhachHang();
        dao = new DAO_GiaoDich(getContext());
        list = dao.getGiaoDichDaXoa(kh.getID());
        adapter = new GiaoDichDaXoaAdapter(getContext(), list);
        lstGDDX.setAdapter(adapter);

        adapter.setActionCompletedListener(new GiaoDichDaXoaAdapter.OnActionCompletedListener() {
            @Override
            public void onActionCompleted() {
                list = dao.getGiaoDichDaXoa(kh.getID());
                adapter.updateData(list);
                lstGDDX.setAdapter(adapter);
            }
        });
        return view;
    }


}

