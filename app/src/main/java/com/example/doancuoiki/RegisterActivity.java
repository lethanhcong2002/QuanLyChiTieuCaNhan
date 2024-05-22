package com.example.doancuoiki;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import BangDuLieu.KhachHang;
import XuLyDuLieu.DAO_KhachHang;

public class RegisterActivity extends AppCompatActivity {
    DAO_KhachHang dao ;
    private EditText txtName;
    private EditText txtLoginName;
    private EditText txtPassword;
    private EditText txtRePassword;
    private Button btnRegister;
    private CheckBox cbShowpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resgister_layout);

        dao = new DAO_KhachHang(this);

        txtLoginName = findViewById(R.id.txtLoginName);
        txtPassword = findViewById(R.id.txtPassword);
        txtRePassword = findViewById(R.id.txtRePassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtName = findViewById(R.id.txtName);
        cbShowpass = findViewById(R.id.cbShowPass);

        cbShowpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    txtRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    txtRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String loginName = txtLoginName.getText().toString().trim();
                String password = txtPassword.getText().toString();
                String repassword = txtRePassword.getText().toString();
                if (loginName.isEmpty() || password.isEmpty() || repassword.isEmpty() || name.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(repassword))
                {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = dateFormat.format(currentDate);
                    KhachHang kh = new KhachHang();
                    kh.setName(name);
                    kh.setLoginName(loginName);
                    kh.setPassword(password);
                    kh.setTrangThai(true);
                    kh.setNgay(formattedDate);
                    boolean kq = dao.ThemKhachHang(kh);
                    if (kq == true) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtLoginName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String loginName = txtLoginName.getText().toString();
                    boolean isValidLogin = dao.kiemtraKH(loginName);
                    if (isValidLogin) {
                        Toast.makeText(RegisterActivity.this, "Tên đăng nhập đã tồn tại.", Toast.LENGTH_SHORT).show();
                        txtLoginName.setText("");
                    }
                }
            }
        });
    }
    public void goToLogin(View view)
    {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
