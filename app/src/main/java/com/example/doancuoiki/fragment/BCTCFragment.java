package com.example.doancuoiki.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import BangDuLieu.KhachHang;
import BangDuLieu.ThuChiTrongThang;
import Custom_Adapter.ExpandableListBCAdapter;
import Support.AppData;
import XuLyDuLieu.DAO_GiaoDich;

public class BCTCFragment extends Fragment {
    DAO_GiaoDich dao;
    KhachHang kh;
    TextView txtTongTN, txtTongCT;
    Spinner yearSpinner;
    ExpandableListView lstThuChiThang;
    TextView txtBCTimes;
    List<ThuChiTrongThang> lstThang;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bctc, container, false);

        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showHideFAB(false);
        }

        kh = AppData.getInstance().getKhachHang();
        dao = new DAO_GiaoDich(getContext());
        txtTongTN = view.findViewById(R.id.txtTongThuNhap);
        txtTongCT = view.findViewById(R.id.txtTongChiTieu);
        lstThuChiThang = view.findViewById(R.id.lstThuChiThang);
        txtBCTimes = view.findViewById(R.id.txt_times);
        List<Double> lst = dao.getTongThuChi(kh.getID());
        txtTongTN.setText(AppData.getInstance().formatLargeNumber(lst.get(1).longValue()));
        txtTongCT.setText(AppData.getInstance().formatLargeNumber(lst.get(0).longValue()));


        yearSpinner = view.findViewById(R.id.yearSpinner);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> yearList = new ArrayList<>();
        for (int year = 2000; year <= currentYear + 1; year++) {
            yearList.add(String.valueOf(year));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, yearList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
        int defaultYearPosition = yearList.indexOf(String.valueOf(currentYear));
        if (defaultYearPosition >= 0) {
            yearSpinner.setSelection(defaultYearPosition);
        } else {
            yearSpinner.setSelection(0);
        }

        lstThang = dao.getMonthData(kh.getID(),currentYear);
        ExpandableListBCAdapter expandablelist_Thang = new ExpandableListBCAdapter(getContext(), lstThang, currentYear);
        lstThuChiThang.setAdapter(expandablelist_Thang);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int values = Integer.valueOf((String) yearSpinner.getSelectedItem());
                yearSpinner.getSelectedItem();
                lstThang = dao.getMonthData(kh.getID(), values);
                ExpandableListBCAdapter expandablelist_Thang = new ExpandableListBCAdapter(getContext(), lstThang, values);
                lstThuChiThang.setAdapter(expandablelist_Thang);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txtBCTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new BCTC_theoTimes_Fragment())
                            .addToBackStack("2")
                            .commit();
                }
            }
        });
        return view;
    }

}
