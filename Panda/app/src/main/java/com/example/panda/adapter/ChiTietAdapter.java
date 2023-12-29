package com.example.panda.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.panda.R;
import com.example.panda.dao.DanhGiaDAO;
import com.example.panda.model.ChiTietDH;
import com.example.panda.model.DanhGia;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.ViewHoler>{
    private Context context;
    private ArrayList<ChiTietDH> list;
    String mand;
    DanhGiaDAO danhGiaDAO;

    public ChiTietAdapter(Context context, ArrayList<ChiTietDH> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_chitietdh,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtTenmon.setText(list.get(position).getTenmon());
        holder.txtSoluong.setText("Số lượng: "+list.get(position).getSoluong());
        Glide.with(context).load(list.get(position).getAnh()).into(holder.imgAnh);
        String str = NumberFormat.getNumberInstance(Locale.US).format(list.get(position).getTien());
        holder.txtGia.setText("Giá: "+str+ " VNĐ");

        SharedPreferences sharedPreferences = context.getSharedPreferences("PANDA",Context.MODE_PRIVATE);
        mand = sharedPreferences.getString("mand","");

        danhGiaDAO = new DanhGiaDAO(context);
        boolean check = danhGiaDAO.checkDanhgia(mand,list.get(position).getMamon());
        if (check){
            holder.btnxoaDG.setVisibility(View.VISIBLE);
        }else if (list.get(position).getTrangthai() == 3){
            holder.btnDanhgia.setVisibility(View.VISIBLE);
        }

        holder.btnDanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themDanhGia(list.get(holder.getAdapterPosition()).getMamon());
                holder.btnDanhgia.setVisibility(View.GONE);
                holder.btnxoaDG.setVisibility(View.VISIBLE);
            }
        });

        holder.btnxoaDG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = danhGiaDAO.xoaDanhGia(mand,list.get(holder.getAdapterPosition()).getMamon());
                if (check){
                    Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                    holder.btnDanhgia.setVisibility(View.VISIBLE);
                    holder.btnxoaDG.setVisibility(View.GONE);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView txtTenmon, txtGia, txtSoluong;
        ImageView imgAnh;
        Button btnDanhgia, btnxoaDG;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            txtTenmon = itemView.findViewById(R.id.txtTenmon);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtSoluong = itemView.findViewById(R.id.txtSoluong);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            btnDanhgia = itemView.findViewById(R.id.btnDanhgia);
            btnxoaDG = itemView.findViewById(R.id.btnxoaDG);
        }
    }

    private void themDanhGia(int mamon){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_danhgia,null);
        builder.setView(view);
        builder.setTitle("Đánh giá");

        EditText edtDanhgia = view.findViewById(R.id.edtDanhgia);

        builder.setPositiveButton("Đánh giá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DanhGiaDAO danhGiaDAO = new DanhGiaDAO(context);
                // lấy ngày hiện tại
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String ngay = simpleDateFormat.format(currentTime);
                String danhgia = edtDanhgia.getText().toString();

                DanhGia danhGia = new DanhGia(mand,ngay,mamon,danhgia);
                danhGiaDAO.themDanhGia(danhGia);

            }
        });



        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
