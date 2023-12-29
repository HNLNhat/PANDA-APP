package com.example.panda.Admin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.R;
import com.example.panda.Admin.adapter.LoaiMonAdapter;
import com.example.panda.dao.LoaiDAO;
import com.example.panda.model.ItemClick;
import com.example.panda.model.LoaiMon;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoaiFrag extends Fragment {

    RecyclerView recyclerViewLoai;
    LoaiDAO loaiDAO;
    ArrayList<LoaiMon> list;
    TextInputEditText edtThemLoai;
    int maloai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_loai_frag,container,false);

        recyclerViewLoai = view.findViewById(R.id.recyclerLoai);
        edtThemLoai = view.findViewById(R.id.edtThemLoai);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnSua = view.findViewById(R.id.btnSua);

        loadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtThemLoai.getText().toString();
                if(loaiDAO.themLoai(tenloai)){
                    loadData();
                    edtThemLoai.setText("");
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtThemLoai.getText().toString();
                LoaiMon loai = new LoaiMon(maloai, tenloai);
                if (loaiDAO.capNhatLoai(loai)){
                    loadData();
                    edtThemLoai.setText("");
                }else {
                    Toast.makeText(getContext(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void loadData(){
        loaiDAO = new LoaiDAO(getContext());
        list = loaiDAO.getDSLoai();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewLoai.setLayoutManager(linearLayoutManager);
        LoaiMonAdapter adapter = new LoaiMonAdapter(getContext(), list, new ItemClick() {
            @Override
            public void onClickLoai(LoaiMon loai) {
                edtThemLoai.setText(loai.getTenloai());
                maloai = loai.getMaloai();
            }
        });
        recyclerViewLoai.setAdapter(adapter);
    }
}
