package com.example.panda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panda.adapter.GioHangAdapter;
import com.example.panda.dao.ChiTietDHDAO;
import com.example.panda.dao.DonHangDAO;
import com.example.panda.dao.GioHangDao;
import com.example.panda.model.ChiTietDH;
import com.example.panda.model.DonHang;
import com.example.panda.model.GioHang;
import com.example.panda.model.ItemClickGioHang;
import com.example.panda.model.ItemClickGioHangXoa;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GioHangActivity extends AppCompatActivity {

    Toolbar toolbar;
    GioHangDao gioHangDao;
    TextView txtTrong, txtTien;
    RecyclerView recyclerGiohang;
    Button btnDathang;
    LinearLayout linearLayoutTien;
    ArrayList<GioHang> list;
    String mand;
    int tongtien = 0;
    int madh;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        gioHangDao = new GioHangDao(GioHangActivity.this);

        txtTien = findViewById(R.id.txtTien);
        txtTrong = findViewById(R.id.txtTrong);
        recyclerGiohang = findViewById(R.id.recyclerGiohang);
        btnDathang = findViewById(R.id.btnDatHang);
        linearLayoutTien = findViewById(R.id.linearTien);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
       // toolbar.setTitle("Giỏ hàng");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setTitle("Giỏ hàng");

        // check giỏ hàng
        sharedPreferences = getSharedPreferences("PANDA",MODE_PRIVATE);
        mand = sharedPreferences.getString("mand", "");

        checkGiohang(mand);

        // hiển thị giỏ hàng
        loadGioHang(mand);

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datHang();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            startActivity(new Intent(GioHangActivity.this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkGiohang(String ma){
        boolean check = gioHangDao.checkGiohang(ma);
        if (check){
            txtTrong.setVisibility(View.GONE);
            recyclerGiohang.setVisibility(View.VISIBLE);
        }else {
            txtTrong.setVisibility(View.VISIBLE);
            recyclerGiohang.setVisibility(View.GONE);
            linearLayoutTien.setVisibility(View.GONE);
            btnDathang.setVisibility(View.GONE);
        }
    }


    private void loadGioHang( String ma){
        gioHangDao = new GioHangDao(GioHangActivity.this);
        list = gioHangDao.getDSGioHang(ma);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GioHangActivity.this);
        recyclerGiohang.setLayoutManager(linearLayoutManager);
        GioHangAdapter adapter = new GioHangAdapter(GioHangActivity.this, list, new ItemClickGioHang() {
            @Override
            public void onClickGioHang(GioHang gioHang) {
                int tien = 0;
                for (int i = 0; i < list.size(); i++) {
                    tien = tien + (list.get(i).getGia() * list.get(i).getSoluong());
                }

                String str = NumberFormat.getNumberInstance(Locale.US).format(tien);
                txtTien.setText(str + " VNĐ");
                tongtien = tien;


            }
        }, new ItemClickGioHangXoa() {
            @Override
            public void onClickGioHang(GioHang gioHang) {
               showDialogg(gioHang.getMagh());
            }
        }, gioHangDao);
        recyclerGiohang.setAdapter(adapter);
    }

    private void showDialogg(int ma){
        AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
        builder.setTitle("Xóa");
        builder.setMessage("Bạn có chắc muốn xóa");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = gioHangDao.xoaGioHang(ma);

                if (check){
                    Toast.makeText(GioHangActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                    loadGioHang(mand);
                    checkGiohang(mand);
                }else {
                    Toast.makeText(GioHangActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void datHang(){
        ArrayList<DonHang> donHangs = null;
        DonHangDAO donHangDAO = new DonHangDAO(this);
        ChiTietDHDAO chiTietDHDAO = new ChiTietDHDAO(this);
        int tongsol = gioHangDao.tongSoLuong(mand);
        // lấy ngày hiện tại
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);
        DonHang donHang = new DonHang(mand,tongsol,tongtien,ngay,0);
        boolean check = donHangDAO.themDonnHang(donHang);
        if (check){
            donHangs = donHangDAO.getDSDonHangTheokh(mand,3);
            Toast.makeText(this, "Đạt hàng thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(GioHangActivity.this,DonHangActivity.class));
            finish();
        }
        madh = sharedPreferences.getInt("madh",0);
        //lấy chi tiết sp
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getMand().equals(mand)){
                int mamon = list.get(i).getMamon();
                int sl = list.get(i).getSoluong();
                int tien = list.get(i).getGia()*list.get(i).getSoluong();
                ChiTietDH chiTietDH = new ChiTietDH(madh,mamon,sl,tien);
                chiTietDHDAO.themCHiTietDH(chiTietDH);
            }
        }

        boolean xoa = gioHangDao.xoaGioHangTheomand(mand);
        if (xoa){
            loadGioHang(mand);
            checkGiohang(mand);
        }
    }
}