package com.example.panda.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.R;
import com.example.panda.adapter.DonHangAdapter;
import com.example.panda.dao.DonHangDAO;
import com.example.panda.model.DonHang;

import java.util.ArrayList;

public class DonHangFrag extends Fragment {

    RecyclerView recyclerDonhang;
    ArrayList<DonHang> list;
    DonHangDAO donHangDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.frag_donhang,container,false);

       recyclerDonhang = view.findViewById(R.id.recyclerDonhang);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PANDA",MODE_PRIVATE);
        String mand = sharedPreferences.getString("mand", "");

        donHangDAO = new DonHangDAO(getActivity());
        list = donHangDAO.getDSDonHangTheokh(mand,3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,true);
        recyclerDonhang.setLayoutManager(linearLayoutManager);
        DonHangAdapter adapter = new DonHangAdapter(getActivity(),list);
        recyclerDonhang.setAdapter(adapter);
        return view;

    }
}
