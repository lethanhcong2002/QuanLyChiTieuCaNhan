package com.example.doancuoiki.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;

import java.util.Arrays;

import BangDuLieu.GiaoDich;
import Support.AppData;
import XuLyDuLieu.DAO_DanhMuc_Nhom;
import XuLyDuLieu.DAO_GiaoDich;

public class DetailFragment extends Fragment {

    GiaoDich gd;
    DAO_DanhMuc_Nhom dao;
    DAO_GiaoDich db;
    TextView txtNhom, txtGhiChu, txtNgay, txtKieuThuChi, txtTien;
    ImageButton btnMenu;
    ImageView img;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giaodich_detail, container, false);

        dao = new DAO_DanhMuc_Nhom(getContext());
        db = new DAO_GiaoDich(getContext());

        txtNhom = view.findViewById(R.id.txtNhom);
        txtGhiChu = view.findViewById(R.id.txtGhiChu);
        txtNgay = view.findViewById(R.id.txtNgay);
        txtKieuThuChi = view.findViewById(R.id.txtLoaiThuChi);
        txtTien = view.findViewById(R.id.txtTien);
        img = view.findViewById(R.id.iv_hinhanh);

        gd = AppData.getInstance().getGiaoDich();

        btnMenu = view.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });


        txtNhom.setText(dao.getTenNhom(gd.getID_Nhom()));
        txtGhiChu.setText(gd.getGhiChu());
        txtNgay.setText(gd.getNgay());
        boolean isThuNhap = dao.getClassNhom(gd.getID_Nhom());
        txtKieuThuChi.setText((isThuNhap ? "Thu Nhập" : "Chi Tiêu"));
        txtTien.setText(String.format("%.0f", gd.getTien()));
        byte[] imageData = gd.getHinhAnh();
        if (imageData != null)
        {
            Log.d("CHeck", Arrays.toString(imageData));
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

            img.setImageBitmap(bitmap);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLargeImageDialog(bitmap);
                }
            });
        }
        return view;
    }

    private void showLargeImageDialog(Bitmap imageBitmap) {
        final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_large_image);

        ImageView imageView = dialog.findViewById(R.id.iv_large_image);
        imageView.setImageBitmap(imageBitmap);
        dialog.show();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_detail, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.btnEdit) {
                    AppData.getInstance().setGiaoDich(gd);
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null) {
                        mainActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, new EditGiaoDichFragment())
                                .addToBackStack(null)
                                .commit();
                        mainActivity.showHideFAB(false);
                    }
                    return true;
                } else if (menuItem.getItemId() == R.id.btnDelete) {
                    showDeleteConfirmationDialog();
                    return true;
                } else {
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có chắc chắn muốn xóa?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean isDeleted = db.VoHieuGiaoDich(gd.getID());
                if (isDeleted) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
