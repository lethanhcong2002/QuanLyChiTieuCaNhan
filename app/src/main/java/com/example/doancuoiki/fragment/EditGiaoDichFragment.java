package com.example.doancuoiki.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doancuoiki.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import BangDuLieu.DanhMuc;
import BangDuLieu.GiaoDich;
import BangDuLieu.KhachHang;
import BangDuLieu.Nhom;
import Custom_Adapter.AlertDialog_Nhom_DanhMuc_Adapter;
import Support.AppData;
import XuLyDuLieu.DAO_DanhMuc_Nhom;
import XuLyDuLieu.DAO_GiaoDich;

public class EditGiaoDichFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    KhachHang kh;
    GiaoDich gd;
    private int CHECK_ID_NHOM = 0;
    int ID_GIAODICH;
    Button btnChonNhom, btnXacNhan;
    EditText txtGhiChu, txtTien;
    DatePicker datePicker;
    ImageView imageView;
    private TextView txtNhom, txtHeader, txtNgay;
    private DAO_DanhMuc_Nhom dao;
    private DAO_GiaoDich dao_giaoDich;
    private AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_edit, container, false);

        kh = AppData.getInstance().getKhachHang();
        gd = AppData.getInstance().getGiaoDich();
        dao = new DAO_DanhMuc_Nhom(getContext());
        dao_giaoDich = new DAO_GiaoDich(getContext());

        txtNgay = view.findViewById(R.id.txtNgay);
        txtHeader = view.findViewById(R.id.txtHeader);
        btnChonNhom = view.findViewById(R.id.btnSelectNhom);
        txtNhom = view.findViewById(R.id.txtNhom);
        txtGhiChu = view.findViewById(R.id.txtGhiChu);
        txtTien = view.findViewById(R.id.txtTien);
        datePicker = view.findViewById(R.id.date_picker);
        imageView = view.findViewById(R.id.iv_hinhanh);
        btnXacNhan = view.findViewById(R.id.btnSummit);
        txtNhom.setVisibility(view.VISIBLE);
        txtNgay.setVisibility(view.VISIBLE);

        txtHeader.setText("Chỉnh Sửa Thông Tin");
        txtNhom.setText(dao.getTenNhom(gd.getID_Nhom()));
        txtGhiChu.setText(gd.getGhiChu());
        txtNgay.setText("Ngày ban đầu: " + gd.getNgay());
        DatePicker(gd.getNgay());
        txtTien.setText(String.format("%.0f", gd.getTien()));
        ID_GIAODICH = gd.getID();
        CHECK_ID_NHOM = gd.getID_Nhom();
        byte[] imageData = gd.getHinhAnh();

        if(imageData != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            imageView.setImageBitmap(bitmap);
        }
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
                }
                else
                {
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

                if (CHECK_ID_NHOM == 0|| tien.isEmpty()) {
                    Toast.makeText(getContext(), "Nội dung không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    GiaoDich giaoDich = new GiaoDich();
                    giaoDich.setID(gd.getID());
                    giaoDich.setID_User(kh.getID());
                    giaoDich.setID_Nhom(CHECK_ID_NHOM);
                    giaoDich.setTrangThai(true);
                    giaoDich.setNgay(ngay);
                    giaoDich.setTien(Double.parseDouble(tien));
                    giaoDich.setGhiChu(ghiChu);
                    giaoDich.setHinhAnh(imageData);
                    boolean kq = dao_giaoDich.capNhatGiaoDich(giaoDich);
                    if(kq == false)
                    {
                        Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        AppData.getInstance().setGiaoDich(giaoDich);
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                }

            }
        });
        return view;
    }

    private void DatePicker(String ngay)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date ngayDate = sdf.parse(ngay);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(ngayDate);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            datePicker.init(year, month, day, null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
