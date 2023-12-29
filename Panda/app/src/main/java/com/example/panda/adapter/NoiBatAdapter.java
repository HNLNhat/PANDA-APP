package com.example.panda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.panda.MainActivity;
import com.example.panda.R;
import com.example.panda.model.MonAn;

import java.util.ArrayList;

public class NoiBatAdapter extends RecyclerView.Adapter<NoiBatAdapter.ViewHolder>{

    private ArrayList<MonAn> list;
    private Context context;

    public NoiBatAdapter(ArrayList<MonAn> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg()).into(holder.imgBanner);
        holder.txtTenmon.setText(list.get(position).getTenmon());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgBanner;
        TextView txtTenmon;
        public ViewHolder(View itemView) {
            super(itemView);

            imgBanner = itemView.findViewById(R.id.imgBanner);
            txtTenmon = itemView.findViewById(R.id.txtTenmon);
        }
    }
}
