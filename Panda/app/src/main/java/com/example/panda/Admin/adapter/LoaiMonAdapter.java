package com.example.panda.Admin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.R;
import com.example.panda.dao.LoaiDAO;
import com.example.panda.model.ItemClick;
import com.example.panda.model.LoaiMon;

import java.util.ArrayList;

public class LoaiMonAdapter extends RecyclerView.Adapter<LoaiMonAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiMon> list;
    private ItemClick itemClick;

    public LoaiMonAdapter(Context context, ArrayList<LoaiMon> list, ItemClick itemClick){
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai_admin,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaLoai.setText(String.valueOf(list.get(position).getMaloai()));
        holder.txtTenLoai.setText(list.get(position).getTenloai());
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiDAO loaiDAO = new LoaiDAO(context);
                int check = loaiDAO.xoaLoaiMon(list.get(holder.getAdapterPosition()).getMaloai());
                if (check == 1){
                    Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list = loaiDAO.getDSLoai();
                    notifyDataSetChanged();
                }else  if (check == -1){
                    Toast.makeText(context, "Món ăn đang tồn tại", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Không thể xóa", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMaLoai, txtTenLoai;
        ImageView imgSua, imgXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtLoaiMon);
            txtTenLoai = itemView.findViewById(R.id.txtTenloai);
            imgSua = itemView.findViewById(R.id.imgSua);
            imgXoa = itemView.findViewById(R.id.imgXoa);
        }
    }
}
