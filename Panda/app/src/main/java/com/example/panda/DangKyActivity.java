package com.example.panda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panda.dao.NguoiDungDAO;

public class DangKyActivity extends AppCompatActivity {

    Button btnDangKy;
    EditText edtTenDN, edtHoTen, edtPass, edtRePass;
    TextView txtDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        btnDangKy = findViewById(R.id.btnDangKy);
        edtTenDN = findViewById(R.id.edtUserName);
        edtHoTen = findViewById(R.id.edthoTen);
        edtPass = findViewById(R.id.edtPassWord);
        edtRePass = findViewById(R.id.edtRePassWord);
        txtDangNhap = findViewById(R.id.txtDangNhap);

        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(getApplicationContext());

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDN = edtTenDN.getText().toString();
                String hoTen = edtHoTen.getText().toString();
                String pass = edtPass.getText().toString();
                String repass = edtRePass.getText().toString();

                if (tenDN.equals("") || hoTen.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(DangKyActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if (!pass.equals(repass)){
                    Toast.makeText(DangKyActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }else {
                    boolean check = nguoiDungDAO.DangKy(tenDN,hoTen,pass,"kh");
                    if (check){

                        SharedPreferences sharedPreferences = getSharedPreferences("PANDA",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mand", tenDN);
                        editor.putString("hoten", hoTen);
                        editor.commit();

                        Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DangKyActivity.this, ThemTTActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Toast.makeText(DangKyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKyActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



    }
}