package com.example.panda.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.R;
import com.example.panda.model.DanhGia;

import java.util.ArrayList;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ViewHoler>{
    private Context context;
    private ArrayList<DanhGia> list;

    public DanhGiaAdapter(Context context, ArrayList<DanhGia> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_danhgia,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtten.setText(list.get(position).getHoten());
        holder.txtngay.setText(list.get(position).getNgay());
        holder.txtdanhgia.setText(list.get(position).getDanggia());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView txtten, txtngay, txtdanhgia;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            txtten = itemView.findViewById(R.id.txtten);
            txtngay = itemView.findViewById(R.id.txtngay);
            txtdanhgia = itemView.findViewById(R.id.txtdanhgia);
        }
    }
}
