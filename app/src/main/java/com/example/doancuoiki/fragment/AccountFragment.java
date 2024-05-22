package com.example.doancuoiki.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doancuoiki.LoginActivity;
import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;

import BangDuLieu.KhachHang;
import Support.AppData;
import XuLyDuLieu.DAO_KhachHang;

public class AccountFragment extends Fragment {
    private static boolean checkshowpass = false;
    LinearLayout ll;
    EditText txtMatKhauCu, txtMatKhauMoi, txtXacNhanMatKhauMoi;
    TextView txtTenuser, txtLoginName, txtPassword;
    Button btnDoiMatKhau, btnDoi;
    ImageButton ivShowPass;
    CheckBox cbshowpass;
    KhachHang kh;
    DAO_KhachHang dao;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showHideFAB(false);
        }

        txtTenuser = (TextView) view.findViewById(R.id.txtTenUser);
        txtLoginName = (TextView) view.findViewById(R.id.txtLoginName);
        txtPassword = (TextView) view.findViewById(R.id.txtPassword);
        btnDoi = (Button) view.findViewById(R.id.btnIsChanglePassword);
        ivShowPass = (ImageButton) view.findViewById(R.id.imgshow_hidepass);
        txtMatKhauCu = (EditText) view.findViewById(R.id.txtMatKhauCu) ;
        txtMatKhauMoi = (EditText) view.findViewById(R.id.txtMatKhauMoi);
        txtXacNhanMatKhauMoi= (EditText) view.findViewById(R.id.txtXacNhanMatKhauMoi);
        btnDoiMatKhau = (Button) view.findViewById(R.id.btndoiMatKhau);
        ll = (LinearLayout) view.findViewById(R.id.linearAccount);
        cbshowpass = (CheckBox) view.findViewById(R.id.cbShowPass);

        kh = AppData.getInstance().getKhachHang();
        txtTenuser.setText(kh.getName());
        txtLoginName.setText(kh.getLoginName());
        txtPassword.setText(kh.getPassword());

        ivShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkshowpass == false)
                {
                    ivShowPass.setImageResource(R.drawable.hidepass);
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    checkshowpass = true;
                } else {
                    ivShowPass.setImageResource(R.drawable.showpass);
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    checkshowpass = false;
                }
            }
        });

        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll.setVisibility(View.VISIBLE);
                btnDoi.setVisibility(View.GONE);
            }
        });

        cbshowpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtMatKhauCu.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    txtMatKhauMoi.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    txtXacNhanMatKhauMoi.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    txtMatKhauCu.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    txtMatKhauMoi.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    txtXacNhanMatKhauMoi.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(txtMatKhauCu.getText())){
                    Toast.makeText(getContext(),"Vui lòng nhập mật khẩu cũ", Toast.LENGTH_SHORT).show();
                }
                else if (!txtMatKhauCu.getText().toString().equals(kh.getPassword())) {
                    Toast.makeText(getContext(),"Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(txtMatKhauMoi.getText())) {
                    Toast.makeText(getContext(),"Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                }
                else if (txtMatKhauCu.getText().toString().equals(txtMatKhauMoi.getText().toString())) {
                    Toast.makeText(getContext(),"Không thay đổi mật khẩu mới bằng mật khẩu cũ", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(txtXacNhanMatKhauMoi.getText())) {
                    Toast.makeText(getContext(),"Vui lòng xác nhận lại mật khẩu mới", Toast.LENGTH_SHORT).show();
                }
                else if (!txtMatKhauMoi.getText().toString().equals(txtXacNhanMatKhauMoi.getText().toString())) {
                    Toast.makeText(getContext(),"Mật khẩu xác nhận không đúng", Toast.LENGTH_SHORT).show();
                }
                else if(txtMatKhauCu.getText().toString().equals(kh.getPassword().toString()) && txtMatKhauMoi.getText().toString().equals(txtXacNhanMatKhauMoi.getText().toString()) && !txtMatKhauCu.getText().toString().equals(txtMatKhauMoi.getText().toString())){
                    dao = new DAO_KhachHang(getContext());
                    kh.setPassword(txtXacNhanMatKhauMoi.getText().toString());
                    dao.DoiMatKhau(kh);
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    AppData.getInstance().setGiaoDich(null);
                    getActivity().finish();
                    Toast.makeText(getContext(),"Đổi mật khẩu thành công, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
