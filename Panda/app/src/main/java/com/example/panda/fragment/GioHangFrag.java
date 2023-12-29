package com.example.panda.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.DonHangActivity;
import com.example.panda.GioHangActivity;
import com.example.panda.R;
import com.example.panda.adapter.GioHangAdapter;
import com.example.panda.dao.ChiTietDHDAO;
import com.example.panda.dao.DonHangDAO;
import com.example.panda.dao.GioHangDao;
import com.example.panda.dao.NguoiDungDAO;
import com.example.panda.dao.ThongTinKHDAO;
import com.example.panda.model.ChiTietDH;
import com.example.panda.model.DonHang;
import com.example.panda.model.GioHang;
import com.example.panda.model.ItemClickGioHang;
import com.example.panda.model.ItemClickGioHangXoa;
import com.example.panda.model.ThongTinKH;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GioHangFrag extends Fragment {

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
    ThongTinKHDAO thongTinKHDAO;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<ThongTinKH> listTTKH;
    int i, index = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_giohang,null);

        gioHangDao = new GioHangDao(getContext());

        txtTien = view.findViewById(R.id.txtTien);
        txtTrong = view.findViewById(R.id.txtTrong);
        recyclerGiohang = view.findViewById(R.id.recyclerGiohang);
        btnDathang = view.findViewById(R.id.btnDatHang);
        linearLayoutTien = view.findViewById(R.id.linearTien);

        // check giỏ hàng
        sharedPreferences = getContext().getSharedPreferences("PANDA",MODE_PRIVATE);
        mand = sharedPreferences.getString("mand", "");

        checkGiohang(mand);

        // hiển thị giỏ hàng
        loadGioHang(mand);

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTTKH();
            }
        });

        return view;
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
        gioHangDao = new GioHangDao(getContext());
        list = gioHangDao.getDSGioHang(ma);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerGiohang.setLayoutManager(linearLayoutManager);
        GioHangAdapter adapter = new GioHangAdapter(getContext(), list, new ItemClickGioHang() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa");
        builder.setMessage("Bạn có chắc muốn xóa");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = gioHangDao.xoaGioHang(ma);

                if (check){
                    loadGioHang(mand);
                    checkGiohang(mand);
                }else {
                    Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
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
        DonHangDAO donHangDAO = new DonHangDAO(getContext());
        ChiTietDHDAO chiTietDHDAO = new ChiTietDHDAO(getContext());
        int tongsol = gioHangDao.tongSoLuong(mand);
        // lấy ngày hiện tại
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);
        DonHang donHang = new DonHang(mand,tongsol,tongtien,ngay,0);
        boolean check = donHangDAO.themDonnHang(donHang);
        if (check){
            startActivity(new Intent(getContext(), DonHangActivity.class));
            donHangs = donHangDAO.getDSDonHangTheokh(mand,3);
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

    public void showDialogTTKH(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_thongtindathang,null);
        builder.setView(view);

        TextView txthoten = view.findViewById(R.id.txthoten);
        TextView txtsdt = view.findViewById(R.id.txtsdt);
        TextView txtdiachi = view.findViewById(R.id.txtdiachi);
        thongTinKHDAO = new ThongTinKHDAO(getContext());
        listTTKH = thongTinKHDAO.getThongTinKH();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PANDA", Context.MODE_PRIVATE);
        String mand = sharedPreferences.getString("mand","");

        for (i = 0; i < listTTKH.size(); i++){
            String ma = listTTKH.get(i).getMadn();
            if (ma.equals(mand)){
                txthoten.setText("Họ tên: "+ listTTKH.get(i).getHoten());
                txtsdt.setText("Số điện thoại: "+ listTTKH.get(i).getSdt());
                txtdiachi.setText("Địa chỉ: "+ listTTKH.get(i).getDiachi());
                index = i + 1;
                break;
            }
        }

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                datHang();
            }
        });

        builder.setNeutralButton("Chỉnh sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                capNhatThongTin();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void capNhatThongTin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setPositiveButton("Lưu", null).setNegativeButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_capnhat_ttkh,null);
        builder.setView(view);

        EditText edtsdt = view.findViewById(R.id.edtsdt);
        EditText edtdiachi = view.findViewById(R.id.edtdiachi);

        thongTinKHDAO = new ThongTinKHDAO(getContext());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PANDA", Context.MODE_PRIVATE);
        String mand = sharedPreferences.getString("mand","");

        edtsdt.setText(listTTKH.get(i).getSdt());
        edtdiachi.setText(listTTKH.get(i).getDiachi());

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = edtsdt.getText().toString();
                String diachi = edtdiachi.getText().toString();
                String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
                if (sdt.matches(reg)){
                    ThongTinKH thongTinKH = new ThongTinKH(index,mand,sdt,diachi);
                    boolean check = thongTinKHDAO.capNhatThongThinKH(thongTinKH);
                    if (check){
                        alertDialog.dismiss();
                        datHang();
                    }else {
                        Toast.makeText(getContext(), "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Số điện thoại chưa đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
