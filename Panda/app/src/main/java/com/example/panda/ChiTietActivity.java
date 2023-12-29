package com.example.panda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.panda.adapter.DanhGiaAdapter;
import com.example.panda.adapter.MonAdapter;
import com.example.panda.dao.DanhGiaDAO;
import com.example.panda.dao.GioHangDao;
import com.example.panda.dao.MonAnDAO;
import com.example.panda.model.DanhGia;
import com.example.panda.model.GioHang;
import com.example.panda.model.MonAn;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtGia, txtMota, txtSoluong;
    RecyclerView recyclerViewDanhGia;
    ImageView imgAnh, imgTru, imgCong;
    Button btnThemgiohang;
    CollapsingToolbarLayout collapsingToolbarLayout;
    int sol = 1, giamoi;
    GioHangDao gioHangDao;
    ArrayList<DanhGia> list;
    DanhGiaDAO danhGiaDAO;
    int mamon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        toolbar = findViewById(R.id.main_toolbar);
        txtGia = findViewById(R.id.txtGia);
        txtMota = findViewById(R.id.txtMota);
        imgAnh = findViewById(R.id.main_backdrop);
        collapsingToolbarLayout = findViewById(R.id.main_collapsing);
        txtSoluong = findViewById(R.id.txtSoluong);
        imgCong = findViewById(R.id.imgCong);
        imgTru = findViewById(R.id.imgTru);
        btnThemgiohang = findViewById(R.id.btnThemGioHang);
        recyclerViewDanhGia = findViewById(R.id.recyclerDanhGia);

        gioHangDao = new GioHangDao(ChiTietActivity.this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mamon = bundle.getInt("mamon",0);
        String tenmon = bundle.getString("tenmon");
        int gia = bundle.getInt("gia",0);
        String mota = bundle.getString("mota");
        String anh = bundle.getString("anh");

        SharedPreferences sharedPreferences = getSharedPreferences("PANDA",MODE_PRIVATE);
        String mand = sharedPreferences.getString("mand", "");

        collapsingToolbarLayout.setTitle(tenmon);
        String str = NumberFormat.getNumberInstance(Locale.US).format(gia);
        txtGia.setText(str + " VNĐ");
        txtMota.setText(mota);
        Glide.with(this).load(anh).into(imgAnh);

        // dánh giá
        danhGia();

        // cộng trừ số lượng
        txtSoluong.setText(Integer.toString(sol));
        checkSoL();

        imgCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sol = sol + 1;
                txtSoluong.setText(Integer.toString(sol));
                checkSoL();
                giamoi = sol * gia;
            }
        });

        imgTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sol = sol - 1;
                txtSoluong.setText(Integer.toString(sol));
                checkSoL();
                giamoi = sol * gia;
            }
        });

        btnThemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean kt = gioHangDao.kiemTraSLtontai(mamon);
                int solht = sol;
                if (kt){
                    int magh = sharedPreferences.getInt("magh", 0);
                    int soluong = sharedPreferences.getInt("soluong", 0);

                    int slm = solht+soluong;

                    if (slm > 10){
                        Toast.makeText(ChiTietActivity.this, "Số lượng vượt giới hạn trong giỏ hàng", Toast.LENGTH_SHORT).show();
                    }else {
                       gioHangDao.capNhatSoL(magh, slm);
                    }
                }else {
                    GioHang gioHang = new GioHang(mamon,mand,solht);
                    gioHangDao.themGioHang(gioHang);
                    finish();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkSoL(){
        if (sol == 1){
            imgTru.setVisibility(View.INVISIBLE);
            imgCong.setVisibility(View.VISIBLE);
        }else if (1 < sol && sol < 10){
            imgCong.setVisibility(View.VISIBLE);
            imgTru.setVisibility(View.VISIBLE);
        }else if (sol == 10){
            imgCong.setVisibility(View.INVISIBLE);
            imgTru.setVisibility(View.VISIBLE);
        }
    }

    private void danhGia(){
        danhGiaDAO = new DanhGiaDAO(this);
        list = danhGiaDAO.getDSDanhGia(mamon);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewDanhGia.setLayoutManager(linearLayoutManager);
        DanhGiaAdapter adapter = new DanhGiaAdapter(this,list);
        recyclerViewDanhGia.setAdapter(adapter);
    }
}