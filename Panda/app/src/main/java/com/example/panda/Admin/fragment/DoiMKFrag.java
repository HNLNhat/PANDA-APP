package com.example.panda.Admin.fragment;

import android.content.Context;
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
import androidx.fragment.app.Fragment;

import com.example.panda.Admin.AdminActivity;
import com.example.panda.LoginActivity;
import com.example.panda.R;
import com.example.panda.dao.NguoiDungDAO;

public class DoiMKFrag extends Fragment {

    NguoiDungDAO nguoiDungDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_doimk_frag,container,false);

        EditText edtPass = view.findViewById(R.id.edtPassword);
        EditText edtNewPass = view.findViewById(R.id.edtNewPassword);
        EditText edtRePass = view.findViewById(R.id.edtRePassword);
        TextView txtten = view.findViewById(R.id.txtTen);
        Button btnLuu = view.findViewById(R.id.btnluu);
        Button btnHuy = view.findViewById(R.id.btnhuy);
        nguoiDungDAO = new NguoiDungDAO(getContext());

        SharedPreferences sharedPreferences = (getContext()).getSharedPreferences("PANDA",getContext().MODE_PRIVATE);
        String tenKh = sharedPreferences.getString("hoten", "");
        String mand = sharedPreferences.getString("mand","");
        txtten.setText(tenKh);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = edtPass.getText().toString();
                String newpass = edtNewPass.getText().toString();
                String repass = edtRePass.getText().toString();

                if (oldpass.equals("" ) || newpass.equals("") || repass.equals("")){
                    Toast.makeText(getContext(), "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (newpass.equals(repass)){
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
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

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AdminActivity.class));
            }
        });


        return view;
    }
}
