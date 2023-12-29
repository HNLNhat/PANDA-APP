package com.example.panda.Admin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.R;
import com.example.panda.adapter.ChiTietAdapter;
import com.example.panda.adapter.DonHangAdapter;
import com.example.panda.dao.ChiTietDHDAO;
import com.example.panda.dao.DonHangDAO;
import com.example.panda.model.ChiTietDH;
import com.example.panda.model.DonHang;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DonHangAdapter_Admin extends RecyclerView.Adapter<DonHangAdapter_Admin.ViewHoler> {
    private Context context;
    private ArrayList<DonHang> list;
    String trangthai = "";
    DonHangDAO donHangDAO;
    ChiTietDHDAO chiTietDHDAO;
    ArrayList<ChiTietDH> listchitiet;

    public DonHangAdapter_Admin(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DonHangAdapter_Admin.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_donhang,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter_Admin.ViewHoler holder, int position) {
        holder.txtmadh.setText("Mã đơn hàng: "+list.get(position).getMadh());
        holder.txtTenkh.setText("Tên: "+list.get(position).getHoten());
        holder.txtSdt.setText("Số điện thoại: "+list.get(position).getSdt());
        holder.txtngay.setText("Ngày đặt hàng: "+list.get(position).getNgay());
        holder.txtsoluong.setText("Số lượng: "+ list.get(position).getTongsoluong());
        holder.txtdiachi.setText("Địa chỉ: "+list.get(position).getDiachi());
        String str = NumberFormat.getNumberInstance(Locale.US).format(list.get(position).getTien());
        holder.txtTien.setText("Thanh toán: "+str + " VNĐ");
        chiTietDHDAO = new ChiTietDHDAO(context);

        if (list.get(holder.getAdapterPosition()).getTrangthai() == 0){
            holder.txttrangthai.setVisibility(View.GONE);
            holder.btnxacnhan.setVisibility(View.VISIBLE);
            holder.btnnhanhang.setVisibility(View.GONE);
            holder.btngiaohang.setVisibility(View.GONE);
            holder.btnXoa.setVisibility(View.GONE);
        }else if (list.get(holder.getAdapterPosition()).getTrangthai() == 1){
            holder.txttrangthai.setVisibility(View.GONE);
            holder.btnxacnhan.setVisibility(View.GONE);
            holder.btnnhanhang.setVisibility(View.GONE);
            holder.btngiaohang.setVisibility(View.VISIBLE);
            holder.btnXoa.setVisibility(View.GONE);
        }else if (list.get(holder.getAdapterPosition()).getTrangthai() == 2){
            trangthai = "Đang giao hàng";
            holder.btnxacnhan.setVisibility(View.GONE);
            holder.btnnhanhang.setVisibility(View.GONE);
            holder.btngiaohang.setVisibility(View.GONE);
            holder.btnXoa.setVisibility(View.GONE);
        }else {
            trangthai = "Giao hàng thành công";
            holder.btnxacnhan.setVisibility(View.GONE);
            holder.btnnhanhang.setVisibility(View.GONE);
            holder.btngiaohang.setVisibility(View.GONE);
            holder.btnXoa.setVisibility(View.GONE);
            holder.txttrangthai.setTextColor(Color.parseColor("#00C897"));
        }
        holder.txttrangthai.setText("Trạng thái: "+trangthai);

        holder.btngiaohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donHangDAO = new DonHangDAO(context);
                boolean check = donHangDAO.trangThai(list.get(holder.getAdapterPosition()).getMadh(),2);
                if (check){
                    reLoadData();
                    trangthai = "Đang giao hàng";
                    holder.txttrangthai.setVisibility(View.VISIBLE);
                    holder.btngiaohang.setVisibility(View.GONE);
                }
                holder.txttrangthai.setText("Trạng thái: "+trangthai);
            }
        });

        holder.btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donHangDAO = new DonHangDAO(context);
                boolean check = donHangDAO.trangThai(list.get(holder.getAdapterPosition()).getMadh(),1);
                if (check){
                    reLoadData();
                    holder.btnxacnhan.setVisibility(View.GONE);
                    holder.btngiaohang.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.txtchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xemChiTiet();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView txtmadh, txtTenkh, txtSdt, txtngay, txtsoluong, txtchitiet, txtdiachi, txttrangthai, txtTien;
        Button btnxacnhan, btngiaohang, btnnhanhang, btnXoa;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            txtmadh = itemView.findViewById(R.id.txtmadh);
            txtTenkh = itemView.findViewById(R.id.txtTenkh);
            txtSdt = itemView.findViewById(R.id.txtSdt);
            txtngay = itemView.findViewById(R.id.txtngay);
            txtsoluong = itemView.findViewById(R.id.txtsoluong);
            txtchitiet = itemView.findViewById(R.id.txtchitiet);
            txtdiachi = itemView.findViewById(R.id.txtdiachi);
            txttrangthai = itemView.findViewById(R.id.txttrangthai);
            txtTien = itemView.findViewById(R.id.txtTien);
            btnxacnhan = itemView.findViewById(R.id.btnxacnhan);
            btngiaohang = itemView.findViewById(R.id.btngiaohang);
            btnnhanhang = itemView.findViewById(R.id.btnnhanhang);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }

    public void reLoadData(){
        list.clear();
        list = donHangDAO.getDSDonHang();
        notifyDataSetChanged();
    }

    public void xemChiTiet(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_chitietdh,null);
        builder.setView(view);

        RecyclerView recyclerChitietdh = view.findViewById(R.id.recyclerChitietdh);
        SharedPreferences sharedPreferences = context.getSharedPreferences("PANDA",Context.MODE_PRIVATE);
        int madh = sharedPreferences.getInt("madh", 0);

        listchitiet = chiTietDHDAO.getDSChiTietDH(madh);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerChitietdh.setLayoutManager(linearLayoutManager);
        ChiTietAdapter adapter = new ChiTietAdapter(context,listchitiet);
        recyclerChitietdh.setAdapter(adapter);

        builder.setTitle("Chi tiết đơn hàng");

        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
