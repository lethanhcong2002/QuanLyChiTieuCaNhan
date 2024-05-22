package com.example.doancuoiki.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import BangDuLieu.DanhMuc;
import BangDuLieu.GiaoDich;
import BangDuLieu.KhachHang;
import BangDuLieu.Nhom;
import Custom_Adapter.AlertDialog_Nhom_DanhMuc_Adapter;
import Support.AppData;
import XuLyDuLieu.DAO_DanhMuc_Nhom;
import XuLyDuLieu.DAO_GiaoDich;

public class NewGiaoDichFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    KhachHang kh;
    private int CHECK_ID_NHOM = 0;
    Button btnChonNhom, btnXacNhan;
    EditText txtGhiChu, txtTien;
    DatePicker datePicker;
    ImageView imageView;
    private TextView txtNhom, txtHeader;
    private DAO_DanhMuc_Nhom dao;
    private DAO_GiaoDich dao_giaoDich;
    private AlertDialog alertDialog;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_edit, container, false);

        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showHideFAB(false);
        }

        kh = AppData.getInstance().getKhachHang();
        dao = new DAO_DanhMuc_Nhom(getContext());
        dao_giaoDich = new DAO_GiaoDich(getContext());

        txtHeader = view.findViewById(R.id.txtHeader);
        txtHeader.setText("Thêm Mới Thu Chi");
        btnChonNhom = view.findViewById(R.id.btnSelectNhom);
        txtNhom = view.findViewById(R.id.txtNhom);
        txtGhiChu = view.findViewById(R.id.txtGhiChu);
        txtTien = view.findViewById(R.id.txtTien);
        datePicker = view.findViewById(R.id.date_picker);
        imageView = view.findViewById(R.id.iv_hinhanh);
        btnXacNhan = view.findViewById(R.id.btnSummit);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
                pickImageIntent.setType("image/*");
                startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST);
            }
        });


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
            public void onClick(View v) {
                byte[] imageData;
                Drawable drawables = imageView.getDrawable();
                boolean isBaselineMood = drawables != null && drawables.getConstantState() != null && drawables.getConstantState().equals(getResources().getDrawable(R.drawable.baseline_mood_24).getConstantState());
                if(isBaselineMood == true)
                {
                    imageData = null;
                } else {
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                    Bitmap imageBitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageData = stream.toByteArray();
                }
                String ghiChu = txtGhiChu.getText().toString();
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int dayOfMonth = datePicker.getDayOfMonth();
                String formattedMonth = String.format("%02d", month);
                String formattedDayOfMonth = String.format("%02d", dayOfMonth);
                String ngay = formattedDayOfMonth + "/" + formattedMonth + "/" + year;

                String tien = txtTien.getText().toString();

                if (CHECK_ID_NHOM == 0 || tien.isEmpty()) {
                    Toast.makeText(getContext(), "Nội dung không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    GiaoDich gd = new GiaoDich();
                    gd.setID_User(kh.getID());
                    gd.setID_Nhom(CHECK_ID_NHOM);
                    gd.setTrangThai(true);
                    gd.setNgay(ngay);
                    gd.setTien(Double.parseDouble(tien));
                    gd.setGhiChu(ghiChu);
                    gd.setHinhAnh(imageData);
                    boolean kq = dao_giaoDich.ThemGiaoDich(gd);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
