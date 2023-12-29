package com.example.panda.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.panda.DonHangActivity;
import com.example.panda.LoginActivity;
import com.example.panda.R;
import com.example.panda.dao.NguoiDungDAO;
import com.example.panda.dao.ThongTinKHDAO;
import com.example.panda.model.ThongTinKH;

import java.util.ArrayList;

public class ProfileFrag extends Fragment {

    TextView txtHoSo, txtTen, txtDoiMk, txtdonhang;
    Button btnDangXuat;
    ThongTinKHDAO thongTinKHDAO;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<ThongTinKH> list;
    int i, index = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_profile,null);

        txtHoSo = view.findViewById(R.id.txtHoSo);
        txtTen = view.findViewById(R.id.txtTen);
        txtDoiMk = view.findViewById(R.id.txtDMK);
        txtdonhang = view.findViewById(R.id.txtDonhang);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);

        SharedPreferences sharedPreferences = (getContext()).getSharedPreferences("PANDA",getContext().MODE_PRIVATE);
        String tenKh = sharedPreferences.getString("hoten", "");
        txtTen.setText(tenKh);

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        txtHoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        txtDoiMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doiMatKhau();
            }
        });

        txtdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DonHangActivity.class));
            }
        });

        return view;
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_thongtinkh,null);
        builder.setView(view);

        TextView txthoten = view.findViewById(R.id.txthoten);
        TextView txtsdt = view.findViewById(R.id.txtsdt);
        TextView txtdiachi = view.findViewById(R.id.txtdiachi);
        thongTinKHDAO = new ThongTinKHDAO(getContext());
        list = thongTinKHDAO.getThongTinKH();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PANDA", Context.MODE_PRIVATE);
        String mand = sharedPreferences.getString("mand","");

        for (i = 0; i < list.size(); i++){
            String ma = list.get(i).getMadn();
           if (ma.equals(mand)){
                txthoten.setText("Họ tên: "+ list.get(i).getHoten());
                txtsdt.setText("Số điện thoại: "+ list.get(i).getSdt());
                txtdiachi.setText("Địa chỉ: "+ list.get(i).getDiachi());
                index = i + 1;
                break;
           }
        }

        builder.setPositiveButton("Chỉnh sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                capNhatThongTin();

            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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

        edtsdt.setText(list.get(i).getSdt());
        edtdiachi.setText(list.get(i).getDiachi());

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
                        Toast.makeText(getContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Số điện thoại chưa đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void doiMatKhau(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setPositiveButton("Lưu", null).setNegativeButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau,null);
        builder.setView(view);

        EditText edtPass = view.findViewById(R.id.edtPassword);
        EditText edtNewPass = view.findViewById(R.id.edtNewPassword);
        EditText edtRePass = view.findViewById(R.id.edtRePassword);
        TextView txtten = view.findViewById(R.id.txtTen);

        SharedPreferences sharedPreferences = (getContext()).getSharedPreferences("PANDA",getContext().MODE_PRIVATE);
        String tenKh = sharedPreferences.getString("hoten", "");
        String mand = sharedPreferences.getString("mand","");
        txtten.setText(tenKh);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = edtPass.getText().toString();
                String newpass = edtNewPass.getText().toString();
                String repass = edtRePass.getText().toString();

                if (oldpass.equals("" ) || newpass.equals("") || repass.equals("")){
                    Toast.makeText(getContext(), "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (newpass.equals(repass)){
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN",Context.MODE_PRIVATE);
                        String matt = sharedPreferences.getString("matt","");
                        nguoiDungDAO = new NguoiDungDAO(getContext());
                        int check = nguoiDungDAO.capNhatMK(mand,oldpass,newpass);
                        if (check == 1){
                            Toast.makeText(getContext(), "Cập nhập mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else  if (check == 0){
                            Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(), "Nhập mật khẩu không trùng với nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
