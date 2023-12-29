package com.example.panda.Admin.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.panda.R;
import com.example.panda.dao.DonHangDAO;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class DoanhThuFrag extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_doanhthu_frag,container,false);
        EditText edtStart = view.findViewById(R.id.edtStart);
        EditText edtEnd = view.findViewById(R.id.edtEnd);
        Button btnDoanhThu = view.findViewById(R.id.btnDoanhThu);
        TextView txtDoanhThu = view.findViewById(R.id.txtDoanhThu);

        Calendar calendar = Calendar.getInstance();

        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";

                        if (dayOfMonth<10){
                            ngay = "0"+ dayOfMonth;
                        }else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if (month+1 < 10){
                            thang = "0"+ (month+1);
                        }else {
                            thang = String.valueOf(month+1);
                        }
                        edtStart.setText(ngay+"/"+thang+"/"+year);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });

        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";

                        if (dayOfMonth<10){
                            ngay = "0"+ dayOfMonth;
                        }else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if (month+1 < 10){
                            thang = "0"+ (month+1);
                        }else {
                            thang = String.valueOf(month+1);
                        }
                        edtEnd.setText(ngay+"/"+thang+"/"+year);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHangDAO donHangDAO = new DonHangDAO(getContext());
                String ngaybd = edtStart.getText().toString();
                String ngaykt = edtEnd.getText().toString();
                int doanhthu = donHangDAO.getDoanhThu(ngaybd,ngaykt,3);
                String str = NumberFormat.getNumberInstance(Locale.US).format(doanhthu);
                txtDoanhThu.setText("Doanh thu: "+str+" VNĐ");
            }
        });
        return view;
    }
}
