package com.example.doancuoiki.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.KhachHang;
import BangDuLieu.NganSach;
import Custom_Adapter.NganSachAdapter;
import Support.AppData;
import XuLyDuLieu.DAO_NganSach;

public class KHEndFragment extends Fragment {
    KhachHang kh;
    DAO_NganSach dao;
    List<NganSach> ns;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kh_end, container, false);

        dao = new DAO_NganSach(getContext());
        kh = AppData.getInstance().getKhachHang();
        ns = dao.LayNganSachHetHieuLuc(kh.getID());
        RecyclerView recyclerView = view.findViewById(R.id.lst_kh_end);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        NganSachAdapter adapter = new NganSachAdapter(getContext(), ns);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
