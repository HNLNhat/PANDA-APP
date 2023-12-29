package com.example.panda.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.panda.GioHangActivity;
import com.example.panda.R;
import com.example.panda.dao.GioHangDao;
import com.example.panda.model.GioHang;
import com.example.panda.model.ItemClickGioHang;
import com.example.panda.model.ItemClickGioHangXoa;
import com.example.panda.model.ItemImgClick;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHoler> {
    private Context context;
    private ArrayList<GioHang> list;
    private ItemClickGioHang itemClickGioHang;
    private ItemClickGioHangXoa itemClickGioHangXoa;
    private GioHangDao gioHangDao;
    GioHang gioHang;


    public GioHangAdapter(Context context, ArrayList<GioHang> list, ItemClickGioHang itemClickGioHang, ItemClickGioHangXoa itemClickGioHangXoa,GioHangDao gioHangDao) {
        this.context = context;
        this.list = list;
        this.itemClickGioHang = itemClickGioHang;
        this.gioHangDao = gioHangDao;
        this.itemClickGioHangXoa = itemClickGioHangXoa;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_giohang,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
       gioHang = list.get(position);

        holder.txtTenmon.setText(gioHang.getTenmon());
        holder.txtSoluong.setText(Integer.toString(list.get(position).getSoluong()));
        Glide.with(context).load(list.get(position).getHinh()).into(holder.imgAnh);
        int gia = gioHang.getGia()*gioHang.getSoluong();
        String str = NumberFormat.getNumberInstance(Locale.US).format(gia);
        holder.txtGia.setText(str+ " VNĐ");

        holder.setListenner(new ItemImgClick() {
            @Override
            public void onImgClick(View view, int pos, int giatri) {
                if (giatri == 1){
                    if (list.get(pos).getSoluong() > 1){
                        int solmoi = list.get(pos).getSoluong() - 1;
                        list.get(pos).setSoluong(solmoi);
                    }
                }else if (giatri == 2){
                    if (list.get(pos).getSoluong() < 10){
                        int solmoi = list.get(pos).getSoluong() + 1;
                        list.get(pos).setSoluong(solmoi);
                    }
                }
                holder.txtSoluong.setText(list.get(pos).getSoluong()+"");
                capNhapSL(list.get(pos).getMagh(),list.get(pos).getSoluong());
                int gia = list.get(pos).getSoluong() * list.get(pos).getGia();
                String str = NumberFormat.getNumberInstance(Locale.US).format(gia);
                holder.txtGia.setText(str+" VNĐ");
                itemClickGioHang.onClickGioHang(gioHang);

                if (list.get(pos).getSoluong() <= 1){
                    holder.imgTru.setVisibility(View.INVISIBLE);
                }else if (list.get(pos).getSoluong() >= 10){
                    holder.imgCong.setVisibility(View.INVISIBLE);
                }else {
                    holder.imgCong.setVisibility(View.VISIBLE);
                    holder.imgTru.setVisibility(View.VISIBLE);
                }


            }
        });

        itemClickGioHang.onClickGioHang(gioHang);

        holder.itemGiohang.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemClickGioHangXoa.onClickGioHang(gioHang);
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTenmon, txtGia, txtSoluong;
        ImageView imgAnh, imgTru, imgCong;
        ItemImgClick itemImgClick;
        CardView itemGiohang;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            txtTenmon = itemView.findViewById(R.id.txtTenmon);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtSoluong = itemView.findViewById(R.id.txtSoluong);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            imgCong = itemView.findViewById(R.id.imgCong);
            imgTru = itemView.findViewById(R.id.imgTru);
            itemGiohang = itemView.findViewById(R.id.itemGiohang);

            // su kiem click
            imgCong.setOnClickListener(this);
            imgTru.setOnClickListener(this);
        }

        public void  setListenner(ItemImgClick itemImgClick) {
            this.itemImgClick = itemImgClick;
        }

        @Override
        public void onClick(View v) {
            if (v == imgTru){
                itemImgClick.onImgClick(v,getAdapterPosition(),1);
                // 1tru
            }else if (v == imgCong){
                itemImgClick.onImgClick(v,getAdapterPosition(),2);
                //2 cong
            }
        }
    }

    public void reLoadData(){
        // list.clear();
        list = gioHangDao.getDSGioHangData();
        notifyDataSetChanged();
    }

    public void capNhapSL(int magh, int sl){
        boolean check = gioHangDao.capNhatSoL(magh,sl);
        if (check){

        }else {

        }
    }
}
