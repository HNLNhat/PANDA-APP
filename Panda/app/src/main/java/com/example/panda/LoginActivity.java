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

import com.example.panda.Admin.AdminActivity;
import com.example.panda.dao.NguoiDungDAO;

public class LoginActivity extends AppCompatActivity {
EditText edtUserName , edtPassWord;
Button btnLogin;
TextView txtDangKy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         btnLogin = findViewById(R.id.btnLogin);
         edtUserName = findViewById(R.id.edtUserName);
         edtPassWord = findViewById(R.id.edtPassWord);
         txtDangKy = findViewById(R.id.txtDangKi);

        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);
         btnLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
             String user = edtUserName.getText().toString();
             String pass = edtPassWord.getText().toString();
            if (nguoiDungDAO.checkDangNhap(user,pass)){
                SharedPreferences sharedPreferences = getSharedPreferences("PANDA",MODE_PRIVATE);
                String loaiTK = sharedPreferences.getString("loai", "");
                if(loaiTK.equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                }
            }else{
                Toast.makeText(LoginActivity.this, "Nhập sai tên hoặc mật khẩu ", Toast.LENGTH_SHORT).show();
            }
             }
         });

        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DangKyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

}