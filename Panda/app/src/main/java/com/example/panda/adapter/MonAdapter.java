package com.example.panda.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.panda.ChiTietActivity;
import com.example.panda.R;
import com.example.panda.model.MonAn;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MonAdapter extends RecyclerView.Adapter<MonAdapter.ViewHoler> {
    private Context context;
    private ArrayList<MonAn> list;

    public MonAdapter(Context context, ArrayList<MonAn> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_mon_kh,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtTenMon.setText(list.get(position).getTenmon());
 //       holder.txtGia.setText(Integer.toString(list.get(position).getGia())+ " vnđ");
        String s = list.get(position).getAnh();
        Glide.with(context).load(s).into(holder.imgMon);
        holder.itemMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mamon", list.get(position).getMamon());
                bundle.putString("tenmon", list.get(position).getTenmon());
                bundle.putInt("gia",list.get(position).getGia());
                bundle.putString("mota",list.get(position).getMota());
                bundle.putString("anh",list.get(position).getAnh());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        String str = NumberFormat.getNumberInstance(Locale.US).format(list.get(position).getGia());
        holder.txtGia.setText(str+" VNĐ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        ImageView imgMon;
        TextView txtTenMon, txtGia;
        CardView itemMonAn;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            imgMon = itemView.findViewById(R.id.imgMon);
            txtTenMon = itemView.findViewById(R.id.txtTenmon);
            txtGia = itemView.findViewById(R.id.txtGia);
            itemMonAn = itemView.findViewById(R.id.cardViewMonan);
        }
    }
}
