package com.example.panda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.R;
import com.example.panda.adapter.LoaiAdapter;
import com.example.panda.adapter.MonAdapter;
import com.example.panda.dao.LoaiDAO;
import com.example.panda.dao.MonAnDAO;
import com.example.panda.model.ItemClick;
import com.example.panda.model.LoaiMon;
import com.example.panda.model.MonAn;

import java.util.ArrayList;

public class OderFrag extends Fragment {

    Spinner spnloai;
    RecyclerView recyclerViewLoai, recyclerViewMon;
    LoaiDAO loaiDAO;
    ArrayList<LoaiMon> list;
    MonAnDAO monAnDAO;
    ArrayList<MonAn> monan;
    int maloai;
    CardView tatca;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_oder,null);

        recyclerViewLoai = view.findViewById(R.id.recyclerLoai);
        recyclerViewMon = view.findViewById(R.id.recyclerMonAn);
        tatca = view.findViewById(R.id.tatca);

        loaiMon();
        monAn();

        tatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monAn();
            }
        });
        return view;
    }

    public void loaiMon(){
        loaiDAO = new LoaiDAO(getContext());
        list = loaiDAO.getDSLoai();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerViewLoai.setLayoutManager(linearLayoutManager);
        LoaiAdapter adapter = new LoaiAdapter(list, getContext(), new ItemClick() {
            @Override
            public void onClickLoai(LoaiMon loaiMon) {
                maloai =loaiMon.getMaloai();
                monAnTheoLoai(maloai);
            }
        });
        recyclerViewLoai.setAdapter(adapter);
    }

    public void monAn(){
        monAnDAO = new MonAnDAO(getContext());
        monan = monAnDAO.getDSMonAn();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewMon.setLayoutManager(gridLayoutManager);
        MonAdapter adapter = new MonAdapter(getContext(),monan);
        recyclerViewMon.setAdapter(adapter);
    }

    public void monAnTheoLoai(int loai){
        monAnDAO = new MonAnDAO(getContext());
        monan = monAnDAO.getDSTheoLoaiMonAn(loai);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewMon.setLayoutManager(gridLayoutManager);
        MonAdapter adapter = new MonAdapter(getContext(),monan);
        recyclerViewMon.setAdapter(adapter);
    }
}
