package com.example.doancuoiki.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.KhachHang;
import BangDuLieu.NganSach;
import Custom_Adapter.NganSachAdapter;
import Support.AppData;
import XuLyDuLieu.DAO_NganSach;

public class KHContinuesFragment extends Fragment {

    KhachHang kh;
    DAO_NganSach dao;
    List<NganSach> ns;
    NganSachAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kh_continues, container, false);
        dao = new DAO_NganSach(getContext());
        kh = AppData.getInstance().getKhachHang();
        ns = dao.LayNganSachConHieuLuc(kh.getID());
        RecyclerView recyclerView = view.findViewById(R.id.lst_kh_continues);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NganSachAdapter(getContext(), ns);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        return view;
    }

}
