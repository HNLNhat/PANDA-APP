package com.example.panda.adapter;

import static android.content.Context.MODE_PRIVATE;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.R;
import com.example.panda.dao.ChiTietDHDAO;
import com.example.panda.dao.DonHangDAO;
import com.example.panda.database.DbHelper;
import com.example.panda.model.ChiTietDH;
import com.example.panda.model.DonHang;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHoler> {
    private Context context;
    private ArrayList<DonHang> list;
    String trangthai = "";
    DonHangDAO donHangDAO;
    String mand;
    ChiTietDHDAO chiTietDHDAO;
    ArrayList<ChiTietDH> listct;
    AlertDialog alertDialog;

    public DonHangAdapter(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_donhang,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtmadh.setText("Mã đơn hàng: "+list.get(position).getMadh());
        holder.txtTenkh.setText("Tên: "+list.get(position).getHoten());
        holder.txtSdt.setText("Số điện thoại: "+list.get(position).getSdt());
        holder.txtngay.setText("Ngày đặt hàng : "+list.get(position).getNgay());
        holder.txtsoluong.setText("Số lượng: "+ list.get(position).getTongsoluong());
        holder.txtdiachi.setText("Địa chỉ: "+list.get(position).getDiachi());
        String str = NumberFormat.getNumberInstance(Locale.US).format(list.get(position).getTien());
        holder.txtTien.setText("Thanh toán: "+str+ " VNĐ");

        SharedPreferences sharedPreferences = context.getSharedPreferences("PANDA",MODE_PRIVATE);
        mand = sharedPreferences.getString("mand", "");
        donHangDAO = new DonHangDAO(context);

        // set trạng thái
        if (list.get(holder.getAdapterPosition()).getTrangthai() == 0){
            trangthai = "Đơn hàng đang chờ xác nhận";
            holder.btnxacnhan.setVisibility(View.GONE);
            holder.btnnhanhang.setVisibility(View.GONE);
            holder.btngiaohang.setVisibility(View.GONE);
        }else if (list.get(holder.getAdapterPosition()).getTrangthai() == 1){
            trangthai = "Đơn hàng đã được xác nhận và chuẩn bị";
            holder.btnxacnhan.setVisibility(View.GONE);
            holder.btnnhanhang.setVisibility(View.GONE);
            holder.btngiaohang.setVisibility(View.GONE);
            holder.btnXoa.setVisibility(View.GONE);
        }else if (list.get(holder.getAdapterPosition()).getTrangthai() == 2){
            trangthai = "Đơn hàng đang giao tới bạn";
            holder.btnxacnhan.setVisibility(View.GONE);
            holder.btnnhanhang.setVisibility(View.VISIBLE);
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

        holder.btnnhanhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = donHangDAO.trangThai(list.get(holder.getAdapterPosition()).getMadh(),3);
                if (check){
                    reLoadData();
                    trangthai = "Giao hàng thành công";
                    holder.btnnhanhang.setVisibility(View.GONE);
                }
                holder.txttrangthai.setText("Trạng thái: "+trangthai);
            }
        });

        holder.txtchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xemChiTiet(list.get(holder.getAdapterPosition()).getMadh());
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Đơn hàng sẽ bị hủy");
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        int madh = list.get(holder.getAdapterPosition()).getMadh();
                        boolean check = donHangDAO.xoaDonHang(madh);
                        if (check){
                            reLoadData();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setMessage("Đơn hàng đã được hủy");
                            builder1.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alertDialog1 = builder1.create();
                            alertDialog1.show();
                        }
                        ChiTietDHDAO chiTietDHDAO = new ChiTietDHDAO(context);
                        boolean kt = chiTietDHDAO.xoaChitietdh(madh);
                        if (kt){
                            reLoadData();
                        }
                    }
                });

                builder.setNeutralButton("Trở về", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
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
        list = donHangDAO.getDSDonHangTheokh(mand,3);
        notifyDataSetChanged();
    }

    public void xemChiTiet(int madh){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_chitietdh,null);
        builder.setView(view);

        RecyclerView recyclerChitietdh = view.findViewById(R.id.recyclerChitietdh);

        chiTietDHDAO = new ChiTietDHDAO(context);
        listct = chiTietDHDAO.getDSChiTietDH(madh);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerChitietdh.setLayoutManager(linearLayoutManager);
        ChiTietAdapter adapter = new ChiTietAdapter(context,listct);
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
