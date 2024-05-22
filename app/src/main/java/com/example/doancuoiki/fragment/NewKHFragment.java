package com.example.doancuoiki.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import BangDuLieu.DanhMuc;
import BangDuLieu.KhachHang;
import BangDuLieu.NganSach;
import BangDuLieu.Nhom;
import Custom_Adapter.AlertDialog_Nhom_DanhMuc_Adapter;
import Support.AppData;
import XuLyDuLieu.DAO_DanhMuc_Nhom;
import XuLyDuLieu.DAO_NganSach;

public class NewKHFragment extends Fragment {


    KhachHang kh;
    private int CHECK_ID_NHOM = 0;
    Button btnChonNhom, btnXacNhan;
    EditText txtTien;
    private TextView txtNhom;
    private DAO_DanhMuc_Nhom dao;
    Spinner spThang, spNam;
    private DAO_NganSach dao_nganSach;
    private AlertDialog alertDialog;

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_edit_kh, container, false);

        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showHideFAB(false);
        }

        kh = AppData.getInstance().getKhachHang();
        dao = new DAO_DanhMuc_Nhom(getContext());
        dao_nganSach = new DAO_NganSach(getContext());

        txtTien = view.findViewById(R.id.txtTien);
        txtNhom = view.findViewById(R.id.txtNhom);
        btnChonNhom = view.findViewById(R.id.btnSelectNhom);
        btnXacNhan = view.findViewById(R.id.btnSummit);
        spThang = view.findViewById(R.id.spThang);
        spNam = view.findViewById(R.id.spNam);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayAdapter<String> thangAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        thangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThang.setAdapter(thangAdapter);
        ArrayAdapter<String> namAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        namAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNam.setAdapter(namAdapter);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        for (int i = 1; i <= 12; i++) {
            thangAdapter.add(String.format("%02d", i));
        }

        for (int i = currentYear - 4; i <= currentYear; i++) {
            namAdapter.add(String.valueOf(i));
        }
        namAdapter.add(String.valueOf(currentYear + 1));
        spThang.setSelection(currentMonth - 1);
        int currentYearPosition = namAdapter.getPosition(String.valueOf(currentYear));
        spNam.setSelection(currentYearPosition);

        btnChonNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Chọn Loại Thu Chi:");

                List<Object> items = new ArrayList<>();
                List<DanhMuc> danhMucList = dao.getAllDanhMuc();
                for (DanhMuc danhMuc : danhMucList) {
                    items.add(danhMuc);
                    List<Nhom> nhomListForDanhMuc = dao.getNhomListByDanhMucId(danhMuc.getID());
                    items.addAll(nhomListForDanhMuc);
                }

                AlertDialog_Nhom_DanhMuc_Adapter customAdapter = new AlertDialog_Nhom_DanhMuc_Adapter(requireContext(), items);

                builder.setAdapter(customAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Object selectedItem = items.get(which);
                        if (selectedItem instanceof Nhom) {
                            Nhom selectedNhom = (Nhom) selectedItem;
                            txtNhom.setVisibility(view.VISIBLE);
                            txtNhom.setText(selectedNhom.getName());
                            CHECK_ID_NHOM = selectedNhom.getID();
                            dialog.dismiss();
                        }
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = spThang.getSelectedItem().toString() + "/" + spNam.getSelectedItem().toString() + dao.getTenNhom(CHECK_ID_NHOM);
                String tien = txtTien.getText().toString();

                if(CHECK_ID_NHOM == 0 || tien.isEmpty())
                {
                    Toast.makeText(getContext(), "Nội dung không được bỏ trống", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    NganSach ns = new NganSach();
                    ns.setID_User(kh.getID());
                    ns.setName(name);
                    ns.setID_Nhom(CHECK_ID_NHOM);
                    ns.setNgay(spThang.getSelectedItem().toString() + "/" + spNam.getSelectedItem().toString());
                    Log.d("check:", spThang.getSelectedItem().toString() + "/" + spNam.getSelectedItem().toString());
                    ns.setTien(Double.valueOf(tien));

                    boolean kq = dao_nganSach.ThemNganSach(ns);

                    if(kq == false)
                    {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        requireActivity().onBackPressed();
                    }
                }
            }
        });

        return view;
    }
}
