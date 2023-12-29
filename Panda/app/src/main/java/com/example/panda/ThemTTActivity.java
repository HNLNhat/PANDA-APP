package com.example.panda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.panda.dao.ThongTinKHDAO;
import com.example.panda.model.ThongTinKH;

public class ThemTTActivity extends AppCompatActivity {

    EditText edtSdt, edtDiachi;
    Button btnTieptuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ttactivity);

        edtSdt = findViewById(R.id.edtSdt);
        edtDiachi = findViewById(R.id.edtDiachi);
        btnTieptuc = findViewById(R.id.btnTieptuc);

        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = edtSdt.getText().toString();
                String diachi = edtDiachi.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("PANDA",MODE_PRIVATE);
                String mand = sharedPreferences.getString("mand", "");
                String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
                if (sdt.matches(reg)){
                    ThongTinKH thongTinKH = new ThongTinKH(mand,sdt,diachi);
                    ThongTinKHDAO thongTinKHDAO = new ThongTinKHDAO(ThemTTActivity.this);
                    boolean check = thongTinKHDAO.themThongTinKH(thongTinKH);

                    if (check){
                        Intent intent = new Intent(ThemTTActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Toast.makeText(ThemTTActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ThemTTActivity.this, "Nhập không đúng định dạng", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}