package com.example.panda.Admin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.Admin.adapter.DonHangAdapter_Admin;
import com.example.panda.R;
import com.example.panda.dao.DonHangDAO;
import com.example.panda.model.DonHang;

import java.util.ArrayList;

public class DonHangFrag extends Fragment {

    ArrayList<DonHang> list;
    DonHangDAO donHangDAO;
    RecyclerView recyclerdonhangAdmin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_donhang_frag,container,false);

        recyclerdonhangAdmin = view.findViewById(R.id.recyclerdonhangAdmin);

        donHangDAO = new DonHangDAO(getContext());
        list = donHangDAO.getDSDonHang();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,true);
        recyclerdonhangAdmin.setLayoutManager(linearLayoutManager);
        DonHangAdapter_Admin adapter_admin = new DonHangAdapter_Admin(getContext(),list);
        recyclerdonhangAdmin.setAdapter(adapter_admin);

        return view;
    }
}
