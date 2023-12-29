package com.example.panda.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.R;
import com.example.panda.model.ItemClick;
import com.example.panda.model.LoaiMon;

import java.util.ArrayList;

public class LoaiAdapter extends RecyclerView.Adapter<LoaiAdapter.ViewHoler>{
    private ArrayList<LoaiMon> list;
    private ItemClick itemClick;
    private Context context;

    public LoaiAdapter(ArrayList<LoaiMon> list, Context context, ItemClick itemClick) {
        this.list = list;
        this.itemClick = itemClick;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai_kh,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtLoai.setText(list.get(position).getTenloai());
        holder.itemLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiMon loaiMon = list.get(holder.getAdapterPosition());
                itemClick.onClickLoai(loaiMon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView txtLoai;
        CardView itemLoai;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            txtLoai = itemView.findViewById(R.id.txtloai);
            itemLoai = itemView.findViewById(R.id.itemLoai);
        }
    }
}
