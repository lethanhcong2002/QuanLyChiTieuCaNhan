package com.example.doancuoiki.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.doancuoiki.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import BangDuLieu.ThuChiTungNhom;
import Custom_Adapter.BC_DateRange;
import Support.AppData;
import XuLyDuLieu.DAO_GiaoDich;

public class BCTC_theoTimes_Fragment extends Fragment {

    LinearLayout ll;
    DAO_GiaoDich db ;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bctheo_ktg, container, false);

        db = new DAO_GiaoDich(getContext());

        ll = view.findViewById(R.id.ll_thongke);

        EditText startDateEditText = view.findViewById(R.id.startDateEditText);
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });

        EditText endDateEditText = view.findViewById(R.id.endDateEditText);
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });

        Button btnXacNhan = view.findViewById(R.id.btnXacNhan);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!startDateEditText.getText().toString().isEmpty() && !endDateEditText.getText().toString().isEmpty())
                {
                    loadAndDisplayData(startDateEditText.getText().toString(), endDateEditText.getText().toString());
                    ll.setVisibility(View.VISIBLE);
                }
                else
                {
                    showError("Vui lòng chọn ngày bắt đầu và kết thúc");
                }
            }
        });
        return view;
    }

    private void showDatePickerDialog(final boolean isStartDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                if (isStartDate) {
                    startDate = selectedDate;
                    updateStartDateEditText();
                } else {
                    if (selectedDate.before(startDate)) {
                        showError("Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu.");
                    } else {
                        endDate = selectedDate;
                        updateEndDateEditText();
                    }
                }
            }
        };

        int year, month, day;
        if (isStartDate) {
            year = startDate.get(Calendar.YEAR);
            month = startDate.get(Calendar.MONTH);
            day = startDate.get(Calendar.DAY_OF_MONTH);
        } else {
            year = endDate.get(Calendar.YEAR);
            month = endDate.get(Calendar.MONTH);
            day = endDate.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private void updateStartDateEditText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        EditText startDateEditText = getView().findViewById(R.id.startDateEditText);
        startDateEditText.setText(dateFormat.format(startDate.getTime()));
    }

    private void updateEndDateEditText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        EditText endDateEditText = getView().findViewById(R.id.endDateEditText);
        endDateEditText.setText(dateFormat.format(endDate.getTime()));
    }
    private void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    private void loadAndDisplayData(String ngayBD, String ngayKT) {

        List<Double> thuchi = db.getGiaoDichByDateRange(AppData.getInstance().getKhachHang().getID(), ngayBD, ngayKT);
        displayDataOnRecyclerView(ngayBD, ngayKT, thuchi.get(1).longValue(), thuchi.get(0).longValue());
    }

    private void displayDataOnRecyclerView(String ngayBD, String ngayKT, double tongThuNhap, double tongChiTieu) {
        TextView txtNgayBD = getView().findViewById(R.id.txtNgayBD);
        txtNgayBD.setText("Ngày bắt đầu: " + ngayBD);

        TextView txtNgayKT = getView().findViewById(R.id.txtNgayKT);
        txtNgayKT.setText("Ngày kết thúc: " + ngayKT);

        TextView txtTongThuNhap = getView().findViewById(R.id.txtTongThuNhap);
        txtTongThuNhap.setText("Tổng thu nhập: " + tongThuNhap);

        TextView txtTongChiTieu = getView().findViewById(R.id.txtTongChiTieu);
        txtTongChiTieu.setText("Tổng chi tiêu: " + tongChiTieu);

        String danhGia = calculateDanhGia(tongThuNhap, tongChiTieu);
        TextView txtDanhGia = getView().findViewById(R.id.txtDanhGia);
        txtDanhGia.setText("Đánh Giá: " + danhGia);

        Button btnXemChiTiet = getView().findViewById(R.id.btnXemChiTiet);
        btnXemChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ThuChiTungNhom> lst = db.getNhomDataDateRange(AppData.getInstance().getKhachHang().getID(), ngayBD, ngayKT);
                showCustomAlertDialog(lst);
            }
        });
    }

    private String calculateDanhGia(double tongThuNhap, double tongChiTieu) {
        if (tongThuNhap > tongChiTieu) {
            return "Thu nhập lớn hơn chi tiêu, bạn quản lý tốt tài chính!";
        } else if (tongThuNhap < tongChiTieu) {
            return "Chi tiêu lớn hơn thu nhập, hãy cân nhắc quản lý tài chính!";
        } else {
            return "Thu nhập và chi tiêu cân bằng, bạn đang duy trì tình hình tài chính ổn định!";
        }
    }


    public void showCustomAlertDialog(List<ThuChiTungNhom> transactionList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.custom_dialog_thongke, null);

        TextView txtDialogTitle = view.findViewById(R.id.txtDialogTitle);
        ListView listView = view.findViewById(R.id.listView);
        Button btnClose = view.findViewById(R.id.btnClose);

        txtDialogTitle.setText("Các khoản thu chi");

        BC_DateRange adapter = new BC_DateRange(getContext(), transactionList);
        listView.setAdapter(adapter);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
