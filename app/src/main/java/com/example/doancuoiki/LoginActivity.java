package com.example.doancuoiki;

import androidx.appcompat.app.AppCompatActivity;

import BangDuLieu.KhachHang;
import Support.AppData;
import XuLyDuLieu.DAO_KhachHang;

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

public class LoginActivity extends AppCompatActivity {

    DAO_KhachHang dao ;
    private EditText txtLoginName;
    private EditText txtPassword;
    private Button btnLogin;
    CheckBox cbShowpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        dao = new DAO_KhachHang(this);

        txtLoginName = findViewById(R.id.txtLoginName);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        cbShowpass = findViewById(R.id.cbShowPass);

        cbShowpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginName = txtLoginName.getText().toString();
                String password = txtPassword.getText().toString();
                KhachHang khachHang = dao.getKhachHang(loginName, password);
                if (khachHang != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    AppData.getInstance().setKhachHang(khachHang);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Thông tin đăng nhập không chính xác.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goToRegister(View view)
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}